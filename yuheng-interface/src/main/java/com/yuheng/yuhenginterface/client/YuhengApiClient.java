/*
package com.yuheng.yuhenginterface.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yuheng.yuhenginterface.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.yuheng.yuhenginterface.utils.SignUtils.getSign;

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

        String result = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);

        return result;
    }

    public String getNameByPost(@RequestParam String name)
    {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;

    }

    public String getUsernameByPost(@RequestBody User user)
    {
        String json = JSONUtil.toJsonStr(user);

        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user/")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());

        String result = httpResponse.body();

        System.out.println(result);

        return result;


    }
}
*/
