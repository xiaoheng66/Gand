package com.yuheng.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuheng.yuhengapicommon.model.entity.InterfaceInfo;


/**
* @author ZhangYuheng
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-07-31 10:49:20
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}
