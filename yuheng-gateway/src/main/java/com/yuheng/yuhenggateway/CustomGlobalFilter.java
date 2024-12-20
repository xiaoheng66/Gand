package com.yuheng.yuhenggateway;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.yuheng.yuhengapiclientsdk.utils.SignUtils;
import com.yuheng.yuhengapicommon.model.entity.InterfaceInfo;
import com.yuheng.yuhengapicommon.model.entity.User;
import com.yuheng.yuhengapicommon.service.InnerInterfaceInfoService;
import com.yuheng.yuhengapicommon.service.InnerUserInterfaceInfoService;
import com.yuheng.yuhengapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *   消费者  具体的业务逻辑写在这里了
 *
 * @author YuhengZhang
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;


    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;


    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8123/api";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        log.info("拦截到了1");



        //请求日志
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();


        log.info("请求唯一标识 : " + request.getId());
        log.info("请求唯一方法 : " + request.getMethod());
        log.info("请求路径 : " + request.getPath().value());
        log.info("请求参数 : " + request.getQueryParams());
        log.info("请求来源地址 : " + request.getRemoteAddress());
        log.info("请求来源地址 : " + request.getLocalAddress().getHostString());

        String sourceAddress = request.getLocalAddress().getHostString();




        log.info("拦截到了2");

        //访问控制 - 黑白名单
        /*if (!IP_WHITE_LIST.contains(sourceAddress)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }*/

        // 鉴权  判断ak sk 是否合法

        HttpHeaders headers = request.getHeaders();

        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");

        log.info("KEY is {}",accessKey);

        log.info("拦截到了3");

        // 去数据库中查是否已经分配用户  远程调用

        User invokeUser = null;
        try {

            invokeUser = innerUserService.getInvokeUser(accessKey);

        }catch (Exception e){

            log.error("getInvokeUser error : ", e);
        }
        if (invokeUser == null){
            return handleNoAuth(response);
        }

       /* if (!"yuheng".equals(accessKey)){
            return handleNoAuth(response);
        }*/

        //检查时间
        if (Long.parseLong(nonce) > 10000L){
            return handleNoAuth(response);
        }
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINUTES = 60* 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES){
            return handleNoAuth(response);
        }

        // 从数据库中把 secretKey 取出来  加密一次后和用户传进来的比对
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body,secretKey);

        if (sign == null || !sign.equals(serverSign))
        {
            return handleNoAuth(response);
        }


        // 预处理路径，去掉多余的 `/api` 部分
        String processedPath = path.replaceAll("/api/api", "/api");

        //判断请求的模拟接口是否存在 从数据库中查询 并且判断请求方式是否相符 远程调用
        InterfaceInfo interfaveInfo = null;
        try {

            interfaveInfo = innerInterfaceInfoService.getInterfaveInfo(processedPath, method);

        }catch (Exception e){

            log.error("getInterfaceInfo error : ", e);
        }
        if (interfaveInfo == null){
            return handleNoAuth(response);
        }



        //请求转发， 调用模拟接口， 响应日志
        return handleResponse(exchange,chain,interfaveInfo.getId(), invokeUser.getId());

    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            // 获取原始响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {

                                // 远程调用   调用成功了  调用次数 + 1
                                try {
                                    innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                } catch (Exception e) {
                                    log.error("invokeCount error : " , e);
                                }

                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                               log.info("响应结果 : " + data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();

    }
    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();

    }

    //用来指定过滤器的顺序 不过这里只有一个过滤器
    @Override
    public int getOrder() {
        return -1;
    }
}