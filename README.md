四象物联云平台
===============

> 四象物联云平台简介

四象物联网云平台是一款专注于工业物联的通用物联网平台，可以快速将通过四象工业网关/软网关接入的工业传感器设备、任何智能设备使用MQTT协议接入到系统中， 支持对设备的运行数据进行实时处理、下发控制、数据存储、高性能并发处理、大屏显示、云组态、数据分析、动态归类计算等功能，支持私有化部署。

> 功能特性

1.支持大规模分布式部署，海量连接，性能强悍;
2.物联设备一站式接入集成，可视化配置，毫秒级数据处理;
3.支持万亿级数据的存储、查询和聚合分析;
4.内置高速规则引擎，快速应用集成;
5.跨平台可伸缩，适配各种类型和规模的基础设施;
6.支持可视化大屏设计、可视化云组态设计、可视化表格报表设计;
7.内置能源管理、节能控制、抄表系统、农业大棚控制等多行业应用模块;

四象物联云平台演示地址：
https://cloud.sxlinks.com/
演示帐号：admin
演示密码：Test#1234

四象物联云平台技术架构：https://sxlinks.com/book/cloud/technology/struct.html

四象物联提供工业物联网云平台、智慧管理系统、各种智能系统等软硬件定制业务，拥有自主研发软件和硬件制造能力，为客户落地现场超过500+个，官网：https://www.sxlinks.com

**商业授权微信：**15392751825	QQ：3328350766	QQ群号：773458153

> 功能预览

**产品中心：**产品管理、设备管理、分组管理、监控管理、网络广播管理、设备地图、固件管理、项目管理

**数据中心：**设备实时数据、设备历史数据、告警实时数据、告警历史数据、联动实时数据、联动历史数据

**规则引擎：**预警设置、条件联动、数据转发、数据归类

**数据分析：**统计概况、地理分布、总体趋势、指标趋势、指标聚合

**运维管理：**工单管理

**能源管理：**能源大屏、区域能耗、用电监测、用水监测、用气监测、冷热量监测

**系统管理：**用户管理、角色管理、部门管理、菜单管理、字典管理、业务日志管理、登录日志管理、参数管理、任务管理

## 系统架构图
四象物联设备接线示意图
![输入图片说明](https://www.sxlinks.com/static/scada/images/软件架构/四象物联设备接线网络示意图.png)


## 业务架构图
![输入图片说明](https://www.sxlinks.com/static/scada/images/软件架构/四象工业网关业务架构图.png)
四象工业网关/软网关业务架构图
![输入图片说明](https://www.sxlinks.com/static/scada/images/软件架构/四象工业网关业务架构图.png)

## **系统功能清单**

| 功能                                                         | 是否支持 |
| ------------------------------------------------------------ | -------- |
| 产品管理：产品信息、产品物模型管理                           | 支持     |
| 设备管理：设备信息、设备物模型管理(创建设备时继承产品的物模型,可以单独编辑设备的物模型) | 支持     |
| 设备地图：以地图形式呈现设备的位置、基本信息、实时数据、告警数据 | 支持     |
| 网关管理：管理软网关、硬件网关的操作信息                     | 支持     |
| 视频监控：视频监控管理(视频在线推流实时播放、推流服务器管理) | 支持     |
| 语音广播：语音广播管理(语音广播设备(itc、世邦协议)管理)      | 支持     |
| 项目管理：项目信息管理                                       | 支持     |
| 分组管理：分组位置信息管理                                   | 支持     |
| rs232/rs485的标准modbus-rtu、modbus-tcp、数十种plc原生协议、dtu设备(dtu-modbus-rtu、dtu-modbus-tcp协议) | 支持     |
| mqtt协议接入                                                 | 支持     |
| tcp/udp/websocket协议-按字符分隔匹配接入                     | 支持     |
| tcp/udp/websocket协议-按字符长度匹配接入                     | 支持     |
| tcp/udp/websocket协议-按json结构解析接入                     | 支持     |
| nbiot协议接入                                                | 支持     |
| 视频监控设备(gb28181协议)接入                                | 支持     |
| 语音广播设备(itc、世邦协议)接入                              | 支持     |
| 设备实时数据清洗、封装成模物型推送给云平台数据处理中心       | 支持     |
| 设备数据海量存储                                             | 支持     |
| 设备控制指令下发                                             | 支持     |
| 根据预警条件产生实时预警                                     | 支持     |
| 根据联动控制条件触发相应的设备控制功能                       | 支持     |
| 根据数据动态归类条件将相应的实时数据归类到相应的数据单元，例如实时统计电表、水表、天燃气表、冷热量表的递增数据及累计数据 | 支持     |
| 设备实时数据：浏览设备的实时运行数据                         | 支持     |
| 设备历史数据：浏览设备的历史运行数据                         | 支持     |
| 预警实时数据：浏览设备的实时预警数据                         | 支持     |
| 预警历史数据：浏览设备的历史预警数据                         | 支持     |
| 预警设置                                                     | 支持     |
| 设备联动                                                     | 支持     |
| 数据归类                                                     | 支持     |
| 数据转发                                                     | 支持     |
| 统计概况                                                     | 支持     |
| 地理分布                                                     | 支持     |
| 总体趋势                                                     | 支持     |
| 指标趋势                                                     | 支持     |
| 指标聚合                                                     | 支持     |
| 能源大屏：以大屏报表形式呈现能耗类数据                       | 支持     |
| 区域能耗：以区域形式呈现能耗类数据                           | 支持     |
| 用电监测：以时、日、月、年为时间刻度监测耗电类设备的递增数据及统计数据 | 支持     |
| 用水监测：以时、日、月、年为时间刻度监测耗水类设备的递增数据及统计数据 | 支持     |
| 用气监测：以时、日、月、年为时间刻度监测耗气类设备的递增数据及统计数据 | 支持     |
| 冷热量监测：以时、日、月、年为时间刻度监测能量类设备的递增数据及统计数据 | 支持     |
| 工单管理                                                     | 支持     |
| 大屏管理：可视化设计大屏、预览大屏                           | 支持     |
| 组态管理：可视化设计组态、预览组态                           | 支持     |
| Excel报表管理：可视化设计Excel报表、预览Excel报表            | 支持     |
| 数据源管理                                                   | 支持     |
| 数据集管理                                                   | 支持     |
| 用户管理                                                     | 支持     |
| 角色管理                                                     | 支持     |
| 权限管理                                                     | 支持     |
| 菜单管理                                                     | 支持     |
| 字典管理                                                     | 支持     |
| 部门管理                                                     | 支持     |

## 演示界面

登录
![输入图片说明](https://www.sxlinks.com/static/cloud/images/登录.png)
首页
![输入图片说明](https://www.sxlinks.com/static/cloud/images/首页.png)
大屏
![输入图片说明](https://www.sxlinks.com/static/cloud/images/大屏.png)
登录
![输入图片说明](https://www.sxlinks.com/static/cloud/images/登录.png)
产品管理-列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/产品管理/产品管理_列表.png)
产品管理_产品编辑
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/产品管理/产品管理_产品编辑.png)
产品管理_产品信息
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/产品管理/产品管理_产品信息.png)
产品管理_物模型_属性-列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/产品管理/产品管理_物模型_属性.png)
设备管理-列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备管理_列表.png)
设备详情_基本信息
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_基本信息.png)
设备编辑
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备编辑.png)
设备详情_物模型
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型.png)
设备详情_运行状态
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_运行状态.png)
设备详情_运行状态_历史数据
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_运行状态_历史数据.png)
设备详情_运行状态_下发指令
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_运行状态_下发指令.png)
设备详情_物模型_属性编辑
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性编辑.png)
设备详情_物模型_属性_json属性列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性_json属性列表.png)
设备详情_物模型_属性编辑_json点位
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性编辑_json点位.png)
设备详情_物模型_属性编辑_json格式
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性编辑_json格式.png)
设备详情_物模型_属性_modbus点位
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性_modbus点位.png)
设备详情_物模型_属性_字符串分隔匹配
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性_字符串分隔匹配.png)
设备详情_物模型_属性_字符串长度匹配
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性_字符串长度匹配.png)
设备详情_物模型_属性编辑_字符串匹配
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_属性编辑_字符串匹配.png)
设备详情_物模型_字符串点位列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备管理/设备详情_物模型_字符串点位列表.png)
设备地图
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/设备地图/设备地图_列表.png)
实时监控_列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/监控管理/实时监控_列表.png)
监控管理_监控接入_列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/监控管理/监控管理_监控接入_列表.png)
监控管理_创建监控_详情
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/监控管理/监控管理_创建监控_详情.png)
监控管理_服务器_列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/监控管理/监控管理_服务器_列表.png)
监控管理_服务器_详情
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/监控管理/监控管理_服务器_详情.png)
项目管理_列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/项目管理/项目管理_列表.png)
项目管理_编辑
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/项目管理/项目管理_编辑.png)
项目管理_编辑_选取地图
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/项目管理/项目管理_编辑_选取地图.png)
网关管理_列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/网关管理/网关管理_列表.png)
网关管理_绑定的子设备列表
![输入图片说明](https://www.sxlinks.com/static/cloud/images/产品中心/网关管理/网关管理_绑定的子设备列表.png)


## 后端技术架构

- 基础框架：Spring Boot 2.3.5.RELEASE

- 持久层框架：Mybatis-plus 3.4.1

- 安全框架：Apache Shiro 1.7.0，Jwt 3.11.0

- 数据库连接池：阿里巴巴Druid 1.1.22

- 缓存框架：redis

- 日志打印：logback

- 其他：fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。



## 开发环境

- 语言：Java 8

- IDE(JAVA)： Eclipse安装lombok插件 或者 IDEA

- 依赖管理：Maven

- 数据库：MySQL5.7、ElasticSearch7.6

- 缓存：Redis


## 项目结构
注：web项目和数据处理项目 是独立分开的两个项目，相互之间没有引用.
web项目后端
	base - web后端基本工具类
	Service - web 基本服务操作类
	system - web后端接口类

数据处理项目后端
	mdm - 数据操作
	process - 数据处理

## 项目配置
只需要配置redis、mysql、es地址即可，其它不用配置
