package com.yuheng.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {



    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);


    /**
     * 调用接口统计
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
