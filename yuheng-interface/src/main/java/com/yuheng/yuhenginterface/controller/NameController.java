package com.yuheng.yuhenginterface.controller;

import cn.hutool.http.HttpUtil;
import com.yuheng.yuhengapiclientsdk.model.User;

import com.yuheng.yuhengapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *    API接口
 *
 * @author YuhengZhang
 **/
@RestController
@RequestMapping("/name")
public class NameController {


    @GetMapping("/get")
    public String getNameByGet(String name)
    {
        return "Get 你的名字是 : " + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name)
    {
        return "POST 你的名字是 : " + name;
    }


    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request)
    {
        String result = "POST 用户名字是 : " + user.getUsername();
        return result;
    }


    //  新增接口

    @GetMapping("/fuji")
    public String getFuji(){

        String result = HttpUtil.get("https://papi.oxoll.cn/API/ranpic/fj.php");

        return result;

    }


    @GetMapping("/huilv")
    public String getHuiLv(){

        String result = HttpUtil.get("https://api.songzixian.com/api/exchange-rate?dataSource=open_exchange_rates");

        return result;

    }

    @GetMapping("/shuaige")
    public String getShuaiGe(){

        String result = HttpUtil.get("https://papi.oxoll.cn/API/sgsp/");

        return result;

    }

    @GetMapping("/tiangou")
    public String TianGou(){

        String result = HttpUtil.get("https://v.api.aa1.cn/api/tiangou/index.php");

        return result;

    }

    @GetMapping("/beijingyoujia")
    public String youjiaBJ(){

        String result = HttpUtil.get("https://api.mhimg.cn/api/youjia_cx/?youjia=%E5%8C%97%E4%BA%AC");

        return result;

    }




    @GetMapping("/guangdongyoujia")
    public String youjiaGD(){

        String result = HttpUtil.get("https://api.mhimg.cn/api/youjia_cx/?youjia=%E5%B9%BF%E4%B8%9C");

        return result;

    }

    @GetMapping("/wangming")
    public String WangMing(){

        String result = HttpUtil.get("https://cn.apihz.cn/api/zici/sjwm.php?id=88888888&key=88888888");

        return result;

    }

    @GetMapping("/lishi")
    public String lishiTD(){

        String result = HttpUtil.get("https://api.mhimg.cn/api/lishi/?format=json");

        return result;

    }

    @GetMapping("pyq")
    public String PYQ(){

        String result = HttpUtil.get("https://v.api.aa1.cn/api/pyq/index.php?aa1=json");

        return result;

    }

    @GetMapping("/xingqisi")
    public String xingqisiWA(){

        String result = HttpUtil.get("https://api.ahfi.cn/api/kfcv50?type=json");

        return result;

    }



    @GetMapping("/gaoxiao")
    public String GaoXiao(){

        String result = HttpUtil.get("https://v.api.aa1.cn/api/api-wenan-gaoxiao/index.php?aa1=json");

        return result;

    }







}
