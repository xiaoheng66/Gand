package com.yuheng.yuhengapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;

/**
* @author ZhangYuheng
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-08-01 14:50:03
*/
public interface InnerUserInterfaceInfoService{

    /**
     * 调用接口统计
     */
    boolean invokeCount(long interfaceInfoId,long userId);





}
