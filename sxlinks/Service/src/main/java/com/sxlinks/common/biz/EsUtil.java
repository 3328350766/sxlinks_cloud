package com.sxlinks.common.biz;

import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.metadata.DeviceModelTypeEnum;
import com.sxlinks.common.metadata.ProductModelTypeEnum;
import com.sxlinks.utils.DateUtil;

import java.util.Date;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: EsUtil.java, v 0.1 2020-12-12  Exp $$
  */
public class EsUtil {
    private static String env;
    public static void setEnv(String env){
        EsUtil.env = env;
    }
    /**
     * 记录设备属性/事件上报记录
     * 系统统一前缀 + product + funcType + property + 月份
     * */
    public static  String buildDevIndex(ProductModelTypeEnum funcTypeEnum, String productCode, String identifier){
        return buildDevIndexByDate(new Date(), funcTypeEnum, productCode, identifier);
    }

    /**
     * 生成属性保存的索引路经
     * @param date
     * @param funcTypeEnum
     * @param productCode
     * @param identifier
     * @return
     */
    public static  String buildDevIndexByDate(Date date, ProductModelTypeEnum funcTypeEnum, String productCode, String identifier){
        String month = DateUtil.formatMonth(date);
        String aliases = buildIndexAliases( productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer();
        sb.append(aliases)
                .append(SXConstants.ES.INDEX_SPLIT )
                .append(month)
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(EsUtil.env);
        return sb.toString().toLowerCase();
    }

    /**
     * 记录设备属性设置下发指令, 路由是deviceCode
     * 系统统一前缀 + product + funcType + property  + set + 月份(yyyyMM)
     * */
    public static  String buildDevPropertyIndex(ProductModelTypeEnum funcTypeEnum, String productCode, String identifier){
        return buildDevPropertyIndexByDate(new Date(), funcTypeEnum, productCode, identifier);
    }

    /**
     * 记录设备属性设置下发指令, 路由是deviceCode, 指定日期
     * 系统统一前缀 + product + funcType + property  + set + 月份(yyyyMM)
     * */
    public static  String buildDevPropertyIndexByDate(Date date, ProductModelTypeEnum funcTypeEnum, String productCode, String identifier){
        String month = DateUtil.formatMonth(date);
        String aliases = buildPropSetIndexAliases( productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer();
        sb.append(aliases)
                .append(SXConstants.ES.INDEX_SPLIT )
                .append(month)
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(EsUtil.env);
        return sb.toString().toLowerCase();
    }
    /**
     * 生成设备数据存储相关的模版
     * */
    public static String buildDeviceTemplateName(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        return SXConstants.ES.INDEX_TEMPLATE_PREFIX + productCode + SXConstants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + SXConstants.ES.INDEX_SPLIT  + identifier;
    }
    public static String buildSetPropertyTemplateName(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        return SXConstants.ES.INDEX_TEMPLATE_PREFIX + SXConstants.ES.PROPERTY_SET_NAME + SXConstants.ES.INDEX_SPLIT + productCode + SXConstants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + SXConstants.ES.INDEX_SPLIT  + identifier;
    }
    public static String buildSetFunctionTemplateName(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        return SXConstants.ES.INDEX_TEMPLATE_PREFIX + SXConstants.ES.FUNCTION_SET_NAME + SXConstants.ES.INDEX_SPLIT + productCode + SXConstants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + SXConstants.ES.INDEX_SPLIT  + identifier;
    }
    //后期改动，设备的
    public static String buildDeviceTemplateName(String productCode, DeviceModelTypeEnum funcTypeEnum, String identifier){
        return SXConstants.ES.INDEX_TEMPLATE_PREFIX + productCode + SXConstants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + SXConstants.ES.INDEX_SPLIT  + identifier;
    }
    public static String buildSetPropertyTemplateName(String productCode, DeviceModelTypeEnum funcTypeEnum, String identifier){
        return SXConstants.ES.INDEX_TEMPLATE_PREFIX + SXConstants.ES.PROPERTY_SET_NAME + SXConstants.ES.INDEX_SPLIT + productCode + SXConstants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + SXConstants.ES.INDEX_SPLIT  + identifier;
    }
    public static String buildSetFunctionTemplateName(String productCode, DeviceModelTypeEnum funcTypeEnum, String identifier){
        return SXConstants.ES.INDEX_TEMPLATE_PREFIX + SXConstants.ES.FUNCTION_SET_NAME + SXConstants.ES.INDEX_SPLIT + productCode + SXConstants.ES.INDEX_SPLIT + funcTypeEnum.getType().toLowerCase() + SXConstants.ES.INDEX_SPLIT  + identifier;
    }
    /**
     * 生成设备数据存储相关的模版对应的索引前缀
     * */
    public static String buildIndexPatterns(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        String aliases =  buildIndexAliases(productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer(aliases)
                .append(SXConstants.ES.INDEX_SPLIT )
                .append("*");
        return sb.toString().toLowerCase();
     }
    /**
     * 业务数据存储索引创建别名
     * */
    public static String buildIndexAliases(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        StringBuffer sb = new StringBuffer(SXConstants.ES.INDEX_DEVICE_PREFIX );
        sb.append(productCode)
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(funcTypeEnum.getType())
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(identifier);
        return sb.toString().toLowerCase();
    }

    /**
     * 属性下发别名
     * */
    public static String buildPropSetIndexAliases(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        StringBuffer sb = new StringBuffer(SXConstants.ES.INDEX_DEVICE_PREFIX );
        sb.append(SXConstants.ES.PROPERTY_SET_NAME)
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(productCode)
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(funcTypeEnum.getType())
                .append(SXConstants.ES.INDEX_SPLIT)
                .append(identifier);
        return sb.toString().toLowerCase();
    }
    /**
     * 属性下发别名模版索引规则
     * */
    public static String buildPropSetIndexPatterns(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier){
        String aliases =  buildPropSetIndexAliases(productCode, funcTypeEnum, identifier);
        StringBuffer sb = new StringBuffer(aliases)
                .append(SXConstants.ES.INDEX_SPLIT )
                .append("*");
        return sb.toString().toLowerCase();
    }

}
