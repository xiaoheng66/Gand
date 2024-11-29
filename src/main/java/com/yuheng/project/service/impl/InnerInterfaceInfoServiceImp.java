package com.yuheng.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuheng.project.common.ErrorCode;
import com.yuheng.project.exception.BusinessException;
import com.yuheng.project.mapper.InterfaceInfoMapper;
import com.yuheng.yuhengapicommon.model.entity.InterfaceInfo;
import com.yuheng.yuhengapicommon.model.entity.User;
import com.yuheng.yuhengapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImp implements InnerInterfaceInfoService {


    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaveInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(queryWrapper);

    }
    }

