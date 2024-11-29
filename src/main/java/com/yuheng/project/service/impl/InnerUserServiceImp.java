package com.yuheng.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuheng.project.common.ErrorCode;
import com.yuheng.project.exception.BusinessException;
import com.yuheng.project.mapper.UserMapper;
import com.yuheng.yuhengapicommon.model.entity.User;
import com.yuheng.yuhengapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImp implements InnerUserService {


    @Resource
    private UserMapper userMapper;


    @Override
    public User getInvokeUser(String accessKey) {

        if (StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
