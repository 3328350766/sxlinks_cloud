package com.sxlinks.common.constants;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: SXConstants.java, v 0.1 2020-12-05  Exp $$
  */
public interface SXConstants {
    interface MQTT{
        /**内部客户端的clientId前缀*/
        String SYS_CLIENT_TOPIC_PREFIX = "sxlinks";
        /**ByteCub 整个平台上行topic前缀*/
        String GLOBAL_UP_PREFIX = "up/";
        /**ByteCub 整个平台下行topic前缀*/
        String GLOBAL_DOWN_PREFIX = "down/";
    }
    interface GLOBAL{
        String CHARSET_UFT8 = "UTF-8";
        String CHARSET_GB2312 = "GB2312";
        String TOKEN = "token";
        String RESOURCE_PREFIX = "/profile";
    }

    interface ES{
        String HEADER_DEVICE    = "deviceCode";/**存储数据头字段*/
        String HEADER_TIMESTAMP = "timestamp";/**存储数据头字段*/
        /**系统所有索引前缀*/
        String INDEX_PREFIX     = "sxlinks_";
        String INDEX_DEVICE_PREFIX     = "sxlinks_device_";
        String INDEX_TEMPLATE_PREFIX  = "sxlinks_template_";
        String INDEX_SPLIT      = "_";
        /**设备属性下发后缀*/
        String PROPERTY_SET_NAME = "setProperty";
        String FUNCTION_SET_NAME = "setFunction";
    }

    interface AUTH{
        String SIGN_SPLIT = "|";
//        Long EXPIRED = 1000*60*10L; /**验签时效性，10分钟*/
    }

    interface TOPIC{
        String PREFIX = "sxlinks";
        String Register = "register"; //子设备注册，允计网关内的设备自动注册,将所有子设备上传
        String UnRegister = "un_register"; //子设备卸载
        String PROP = "prop"; //设备上报属性
        String EVENT = "event"; //设备卸载
        String MSG_REPLY = "message/reply";
        String PROP_GET_REPLY = "property/getreply";
        String SERVICE_INVOKE = "service/invoke";
        String PROP_SET = "property/set";
        String PROP_GET = "property/get";
        String SUB = "sub";
        String UPGRADE_REPLY = "upgrade/reply";

        String SUB_MSG_REPLY = "message/reply/sub";
        String SUB_PROP_GET_REPLY = "property/getreply/sub";
        String SUB_SERVICE_INVOKE = "service/invoke/sub";
        String SUB_PROP_SET = "property/set/sub";
        String SUB_PROP_GET = "property/get/sub";
        String SUB_UPGRADE_REPLY = "upgrade/reply/sub";
    }

    interface REDIS_CHANNEL{
        /**属性设置下发*/
        String PROP_SET = "propertySet";
        /**服务调用下发*/
        String SERVICE_INVOKE = "serviceInvoke";
        /**读取设备属性*/
        String PROP_READER = "propertyReader";
        /**升级*/
        String UPGRADE = "deviceUpgrade";
        /**设备在线*/
        String ONLINE = "deviceOnline";
    }

    interface REDIS_DEF{
        /**token有效期为12小时*/
        int TOKEN_EXPIRED = 60*60*12;
        /**设备基本信息缓存，10分钟*/
        int DEVICE_INFO_EXPIRED = 60*10;
        /**产品基本信息缓存，一分钟*/
        int PRODUCT_INFO_EXPIRED = 60;
        /**auth，一分钟*/
        int AUTH_INFO_EXPIRED = 60;
        /**通用缓存*/
        int GENERAL_EXPIRED = 60;
    }
    interface REDIS_KEY{
        String GLOBAL_PREFIX = "sxlinks:";
        String TOKEN = GLOBAL_PREFIX + "user:token:";
        String CAPTCH_KEY = GLOBAL_PREFIX + "captch:token:";
        /**存储设备实时运行状态前缀，后面跟的是设备编码，存储结构是hash结构*/
        String DEV_RT_DATA = GLOBAL_PREFIX + "device:runtime:";
        /**设备基本信息缓存*/
        String DEV_INFO = GLOBAL_PREFIX + "device:info:";
        /**产品基本信息缓存*/
        String PRODUCT_INFO = GLOBAL_PREFIX + "product:info:";
        /**外部应用*/
        String OPEN_AUTH_INFO = GLOBAL_PREFIX + "open:info:";
        /**属性读取设备回执缓存，根据messageId区分*/
        String PROPERTY_GET = GLOBAL_PREFIX + "property:get:";
        /**物模型数据缓存*/
        String PRODUCT_FUNC = GLOBAL_PREFIX + "model:map:";
        /**今日实时消息数*/
        String MESSAGE_TODAY = GLOBAL_PREFIX + "message:today:total:";
        /**首页面板近七日消息数*/
        String MESSAGE_DASH_LINE = GLOBAL_PREFIX + "message:dash:line:";
        /**设备在线状态缓存*/
        String DEVICE_OFFLINE = GLOBAL_PREFIX + "device:status:";
        /**分布式任务锁*/
        String JOB_LOCK = GLOBAL_PREFIX + "lock:job:";
        /**属性设置分布式锁*/
        String PROP_SET_LOCK = GLOBAL_PREFIX + "lock:prop:set:";
        /**服务调用分布式锁*/
        String SERVICE_INVOKE_LOCK = GLOBAL_PREFIX + "lock:service:invoke:";
        /**在线设备列表 sortedset结构*/
        String DEVICE_ONLINE = GLOBAL_PREFIX + "device:online";
        /**，UDP下记录*/
        String DOWN_MSG_UDP = GLOBAL_PREFIX + "device:down:udp:";

        String FIRMWARE_FILE = GLOBAL_PREFIX + "firmware:file:";
    }

    interface URL_PREFIX{
        String MGR = "admin/";
        String OPEN_API = "open/api/";
    }

    interface TASK{
        String DEVICE_ACTIVE_NAME = "deviceActiveTask";
        String DEVICE_UP_MESSAGE_NAME = "deviceUpMessageTask";
        String DEVICE_REPLY_MESSAGE_NAME = "deviceReplyMessageTask";
        String DEVICE_DOWN_MESSAGE_NAME = "deviceDownMessageTask";
        String SERVICE_INVOKE_NAME = "serviceInvokeTask";
        String PROPERTY_SET_NAME = "propertySetTask";
        String PROPERTY_READER_NAME = "propertyReaderTask";
        /**MQTT client 监听上行消息客户端*/
        String MQTT_UP_NAME = "mqttUpMessageTask";

        String TASK_CONSUME = "taskConsumePool";

        String DELAY_UPGRADE = "delayUpgrade";
    }

    interface NETWORK{
        String LOCAL_HOST = "127.0.0.1";
    }
}
