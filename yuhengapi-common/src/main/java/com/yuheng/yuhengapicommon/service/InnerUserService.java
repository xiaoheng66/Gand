package com.yuheng.yuhengapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.yuheng.yuhengapicommon.model.entity.User;



/**
 * 用户服务
 *
 * @author YuhengZhang
 */
public interface InnerUserService{


    //  查询是否已经分配给用户密钥
    User getInvokeUser(String accessKey);
}
