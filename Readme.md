# NingFOJ在线判题系统

## 项目方案

#### 项目简介

​	NingFOJ（Online Judge）项目为用户提供选择题目，在线做题，编写代码并提交的功能，系统对用户提交的答案进行用例测试，如果一致则通过，否则会返回各种错误类型。

​	该项目的核心模块为代码判题模块，判题机会作为一个单独的项目文件来完成，对外提供接口，可以让普通用户进行API调用。
#### 核心业务流程

##### 业务流程图

![业务流程图](D:\ningfOJ\ningfoj-backend-microservice\images\img.png)

##### 序列图

![序列图](D:\ningfOJ\ningfoj-backend-microservice\images\img_1.png)

##### 软件结构图

![软件结构图](D:\ningfOJ\ningfoj-backend-microservice\images\img_2.png)

##### 数据库设计

![UML图](D:\ningfOJ\ningfoj-backend-microservice\images\img_3.png)

##### 技术选型
##### 框架

- Spring Boot 2.7.x
- Spring MVC
- MyBatis + MyBatis Plus
- Spring Boot 调试工具和项目处理器
- Spring AOP 切面编程
- Spring Scheduler 定时任务
- Spring 事务注解
- Spring Cloud Alibaba

##### 数据存储

- MySQL
- Redis
- RabbitMQ

##### 工具类

- Hutool工具库
- Gson解析库
- Lombok注解