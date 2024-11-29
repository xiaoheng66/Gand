package com.yuheng.project.model.dto.interfaceinfo;


import lombok.Data;

import java.io.Serializable;

/**
 * 接口调用请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {


    private Long id;

    private String userRequestParams;


    private static final long serialVersionUID = 1L;

}