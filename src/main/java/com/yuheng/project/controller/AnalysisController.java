package com.yuheng.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yuheng.project.annotation.AuthCheck;
import com.yuheng.project.common.BaseResponse;
import com.yuheng.project.common.ErrorCode;
import com.yuheng.project.common.ResultUtils;
import com.yuheng.project.exception.BusinessException;
import com.yuheng.project.mapper.UserInterfaceInfoMapper;
import com.yuheng.project.model.vo.InterfaceInfoVO;
import com.yuheng.project.service.InterfaceInfoService;
import com.yuheng.yuhengapicommon.model.entity.InterfaceInfo;
import com.yuheng.yuhengapicommon.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 调用人数分析接口
 *
 * @author YuhengZhang
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;


    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){


        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());

        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);

        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);

            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();


            interfaceInfoVO.setTotalNum(totalNum);

            return interfaceInfoVO;
        }).collect(Collectors.toList());


        return ResultUtils.success(interfaceInfoVOList);

    }

}
