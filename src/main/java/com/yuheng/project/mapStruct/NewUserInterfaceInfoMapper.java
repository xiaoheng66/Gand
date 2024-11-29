package com.yuheng.project.mapStruct;

import com.yuheng.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewUserInterfaceInfoMapper {

    // 创建映射实例
    NewUserInterfaceInfoMapper INSTANCE = Mappers.getMapper(NewUserInterfaceInfoMapper.class);

    // 映射方法，将 UserInterfaceInfoAddRequest 转换为 UserInterfaceInfo
    UserInterfaceInfo toUserInterfaceInfo(UserInterfaceInfoAddRequest userInterfaceInfoAddRequest);
}
