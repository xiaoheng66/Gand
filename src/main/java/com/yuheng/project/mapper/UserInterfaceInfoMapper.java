package com.yuheng.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuheng.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* @author YuhengZhang
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-08-01 14:50:03
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




