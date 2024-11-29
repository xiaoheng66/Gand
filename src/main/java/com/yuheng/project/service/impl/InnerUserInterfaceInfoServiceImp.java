package com.yuheng.project.service.impl;

import com.yuheng.project.service.UserInterfaceInfoService;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;
import com.yuheng.yuhengapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImp implements InnerUserInterfaceInfoService {


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId,userId);
    }
}
