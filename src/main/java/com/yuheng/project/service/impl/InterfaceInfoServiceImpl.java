package com.yuheng.project.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuheng.project.common.ErrorCode;
import com.yuheng.project.exception.BusinessException;
import com.yuheng.yuhengapicommon.model.entity.InterfaceInfo;
import com.yuheng.project.service.InterfaceInfoService;
import com.yuheng.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author ZhangYuheng
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-07-31 10:49:20
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {

     String name = interfaceInfo.getName();

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");

        }
    }

}




