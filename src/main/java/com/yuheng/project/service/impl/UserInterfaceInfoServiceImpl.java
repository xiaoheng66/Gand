package com.yuheng.project.service.impl;



import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuheng.project.common.ErrorCode;
import com.yuheng.project.exception.BusinessException;
import com.yuheng.project.mapper.UserInterfaceInfoMapper;
import com.yuheng.project.service.UserInterfaceInfoService;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author ZhangYuheng
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-08-01 14:50:03
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {



        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }



        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0){

                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或者用户不存在");

            }

     }
        if (userInterfaceInfo.getLeftNum() < 0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");

        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {

        //校验
        if (interfaceInfoId <= 0 || userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //通过这两个传入的参数  找到需要自增的那条数据库的数据

        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");

        return this.update(updateWrapper);
    }



}




