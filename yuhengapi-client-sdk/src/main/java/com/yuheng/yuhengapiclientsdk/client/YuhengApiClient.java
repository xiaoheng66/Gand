package com.yuheng.yuhengapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yuheng.yuhengapiclientsdk.model.User;
import java.util.HashMap;
import java.util.Map;

import static com.yuheng.yuhengapiclientsdk.utils.SignUtils.getSign;


public class YuhengApiClient {

    private String accessKey;

    private String secretKey;

    public YuhengApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    private Map<String,String> getHeaderMap(String body){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        //hashMap.put("secretKey",secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign",getSign(body, secretKey));
        hashMap.put("body",body);

        return hashMap;
    }



    public String getNameByGet(String name)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get("http://localhost:8090/api/name/", paramMap);
        System.out.println(result);

        return result;
    }

    public String getNameByPost(String name)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post("http://localhost:8090/api/name/", paramMap);
        System.out.println(result);
        return result;

    }

    public String getUsernameByPost(User user)
    {
        String json = JSONUtil.toJsonStr(user);

        HttpResponse httpResponse = HttpRequest.post("http://localhost:8090/api/name/user/")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());

        String result = httpResponse.body();

        System.out.println(result);

        return result;
    }
    public String getFuji()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/fuji/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }


    public String getHuiLv()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/huilv/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String getShuaiGe()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/shuaige/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String TianGou()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/tiangou/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String youjiaBJ()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/beijingyoujia/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String youjiaGD()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/guangdongyoujia/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String WangMing()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/wangming/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String lishiTD()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/lishi/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String PYQ()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/pyq/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String xingqisiWA()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/xingqisi/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }

    public String GaoXiao()
    {
        HttpResponse httpResponse = HttpRequest.get("http://localhost:8090/api/name/gaoxiao/")
                .addHeaders(getHeaderMap(""))
                .body("")
                .execute();
        String result = httpResponse.body();
        return result;
    }





}
