# 🚀 API Management Platform

## 项目介绍

该平台是一个基于 **SpringBoot**、**Mybatis**、**Dubbo**、**SpringCloud Gateway**、**Nacos**、**Hutool** 和 **MapStruct** 的接口管理系统。管理员可以发布、下线接口，并可视化查看接口的调用频率统计。当前平台管理着 20+ 个实用接口，累计调用次数已达到万余次。用户可以随时在线测试并调用接口。

This platform is an API management system built using **SpringBoot**, **Mybatis**, **Dubbo**, **SpringCloud Gateway**, **Nacos**, **Hutool**, and **MapStruct**. Administrators can publish and withdraw APIs, and visualize API call frequency statistics. The platform currently manages 20+ practical APIs with cumulative usage reaching tens of thousands. Users can test and call APIs online at any time.

---

## 🛠️ 模块调用流程介绍

1. **前端请求 `proj` 模块**  
2. `proj` 模块通过 **SDK** 与 **反射技术** 调用接口。  
3. SDK 在接口调用逻辑中，向 **GateWay** 模块发送请求。  
4. **GateWay** 模块进行鉴权后，将请求转发到真正提供接口服务的 **Interface** 模块。

### Module Calling Flow

1. The frontend sends a request to the `proj` module.  
2. The `proj` module invokes the interface through the **SDK** and **reflection technology**.  
3. In the SDK’s interface calling logic, a request is sent to the **GateWay** module.  
4. The **GateWay** module performs authentication and forwards the request to the actual **Interface** module providing the service.

---

## ⚠️ 注意事项

- 本SDK尚未上传至 **Maven 仓库**，因此在本地运行项目之前，请先自行打包SDK模块至本地仓库。

### Notes

- The SDK has not been uploaded to the **Maven repository**, so you need to manually package the SDK module to your local repository before running the project.
