package com.sxlinks.modules.system.controller.report.annotation.valid.keyvalue;


import com.sxlinks.modules.system.controller.report.cache.CacheHelper;
import com.sxlinks.modules.system.controller.report.constant.GaeaConstant;
import com.sxlinks.modules.system.controller.report.constant.GaeaKeyConstant;
import com.sxlinks.modules.system.controller.report.holder.UserContentHolder;
import com.sxlinks.modules.system.controller.report.utils.ApplicationContextUtils;
import com.sxlinks.modules.system.controller.report.utils.GaeaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验注解
 * @author lr
 * @since 2021-05-17
 */
public class AssertKeyValueValidator implements ConstraintValidator<AssertKeyValue, Object> {

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 缓存key
     */
    private String key;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        CacheHelper cacheHelper = ApplicationContextUtils.getBean(CacheHelper.class);

        //当不存在缓存时，直接跳过
        if (cacheHelper == null) {
            return true;
        }

        //当值为null时
        if (value == null || (value instanceof String && StringUtils.isBlank((String) value))) {
            return true;
        }

        //国际化
        String locale = LocaleContextHolder.getLocale().getLanguage();

        Map<String, String> map = new HashMap<>();

        //判断字典
        if (StringUtils.isNotBlank(dictCode)) {
            String dictKey = GaeaKeyConstant.DICT_PREFIX + locale + GaeaConstant.REDIS_SPLIT + dictCode;
            map = cacheHelper.hashGet(dictKey);
        } else if (StringUtils.isNotBlank(key)) {
            if (key.contains(GaeaConstant.URL_PATTERN_MARK)) {
                Map<String, Object> params = UserContentHolder.getContext().getParams();
                key = GaeaUtils.replaceFormatString(key, params);
            }
            map = cacheHelper.hashGet(key);
        } else {
            //当dictCode与key同时为空时，放过不校验
            return true;
        }

        //当存在逗号隔开时
        if(String.valueOf(value).contains(GaeaConstant.SPLIT)) {
            String[] values = ((String) value).split(GaeaConstant.SPLIT);
            for (String v : values) {
                if (!map.containsKey(v)) {
                    return false;
                }
            }
            return true;
        } else if (map.containsKey(String.valueOf(value))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(AssertKeyValue assertKeyValue) {
        this.dictCode = assertKeyValue.dictCode();
        this.key = assertKeyValue.key();
    }
}
