package com.sxlinks.modules.system.controller.report.utils;


import com.sxlinks.modules.system.controller.report.annotation.DtoSkip;
import com.sxlinks.modules.system.controller.report.annotation.Formatter;
import com.sxlinks.modules.system.controller.report.annotation.FormatterType;
import com.sxlinks.modules.system.controller.report.cache.CacheHelper;
import com.sxlinks.modules.system.controller.report.constant.Enabled;
import com.sxlinks.modules.system.controller.report.constant.GaeaConstant;
import com.sxlinks.modules.system.controller.report.constant.GaeaKeyConstant;
import com.sxlinks.modules.system.controller.report.holder.UserContentHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
//import com.anji.plus.gaea.holder.UserContentHolder;

/**
 * 字段翻译
 *
 * @author lr
 * @since 2021-01-12
 */
public abstract class GaeaBeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(GaeaBeanUtils.class);

    /**
     * 字段类型转换
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyAndFormatter(Object source, T target) {
        //获取目标类并翻译
        Field[] declaredFields = ReflectionUtils.getAllFieldsArr(target);
        List<Field> fields = new ArrayList<>(declaredFields.length);

        //跳过翻译的字段
        List<String> skipFields = new ArrayList<>();

        //非普通类型的翻译即对象，需要翻译其对象
        List<Field> formatterTypeFields = new ArrayList<>();

        //过滤掉DtoSkip注解的字段
        setFormatterFields(target.getClass(), declaredFields, fields, skipFields, formatterTypeFields);

        //entity翻译成DTO,跳过忽略DtoSkip的字段和类型翻译FormatterType的字段
        BeanUtils.copyProperties(source, target, skipFields.toArray(new String[0]));

        //遍历字段，找出 Formatter注解注释的字段,并翻译
        //
       formatterHandler(source, target, fields);

        //翻译非基础类型即：对象或者集合
        for (Field field : formatterTypeFields) {
            formatSubFields(source, (T) target, field);
        }

        return target;
    }

    /**
     * 复制集合
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<? extends Object> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        Field[] declaredFields = targetClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>(declaredFields.length);
        //跳过翻译的字段
        List<String> skipFields = new ArrayList<>();
        //非普通类型的翻译即对象，需要翻译其对象
        List<Field> formatterTypeFields = new ArrayList<>();
        //过滤掉DtoSkip注解的字段
        setFormatterFields(targetClass, declaredFields, fields, skipFields, formatterTypeFields);
        List<T> ret = new ArrayList<>();
        try {
            for (Object source : sourceList) {
                T target = targetClass.newInstance();
                //entity翻译成DTO,跳过忽略DtoSkip的字段和类型翻译FormatterType的字段
                BeanUtils.copyProperties(source, target, skipFields.toArray(new String[0]));
                ret.add(target);
            }
            //遍历字段，找出 Formatter注解注释的字段,并翻译
            Locale locale = UserContentHolder.getLocale();
            //语言
            String language = locale.getLanguage();
            logger.info("copyList-lang:{},target:{}",language,targetClass.getSimpleName());
            Map<String, Object> params = UserContentHolder.getContext().getParams();
            // 需要处理的字段
            List<Field> formatterFields = fields.stream().parallel()
                    .filter(field -> field.isAnnotationPresent(Formatter.class)).collect(Collectors.toList());

            // 数据准备
            Map<String, Set<String>> hashKeys = new HashMap<>();
            for (int i = 0; i < sourceList.size(); i++) {
                Object s1 = sourceList.get(i);
                T t1 = ret.get(i);
                formatterFields.forEach(field -> {
                    extractDefinition(s1, t1, language, params, hashKeys, field);
                });
            }
            // 批量获取cache数据到本地
            Map<String, Map<String, String>> cacheResult = new HashMap<>();
            if (cacheHelper == null) {
                cacheHelper = ApplicationContextUtils.getBean(CacheHelper.class);
            }
            for (Entry<String, Set<String>> item : hashKeys.entrySet()) {
                fillCacheMap(cacheResult, item);
            }
            // 转换
            for (int i = 0; i < sourceList.size(); i++) {
                Object s2 = sourceList.get(i);
                T t2 = ret.get(i);
                formatterFields.forEach(field -> {
                    fieldFormat(s2, t2, language, params, cacheResult, field);
                });
            }
            for (int i = 0; i < sourceList.size(); i++) {
                Object s3 = sourceList.get(i);
                T t3 = ret.get(i);
                //翻译非基础类型即：对象或者集合
                for (Field field : formatterTypeFields) {
                    formatSubFields(s3, t3, field);
                }
            }
        } catch (Exception ex) {

        }
        return ret;
    }

    private static <T> void formatSubFields(Object s3, T t3, Field field) {
        try {
            PropertyDescriptor sd = new PropertyDescriptor(field.getName(), s3.getClass());
            PropertyDescriptor td = new PropertyDescriptor(field.getName(), t3.getClass());
            Object fieldSource = sd.getReadMethod().invoke(s3);
            if (fieldSource == null) {
                return;
            }
            Method writeMethod = td.getWriteMethod();
            FormatterType ft = field.getAnnotation(FormatterType.class);
            switch (ft.type()) {
                case OBJECT:
                    Object fieldTarget = field.getType().newInstance();
                    copyAndFormatter(fieldSource, fieldTarget);
                    writeMethod.invoke(t3, fieldTarget);
                    break;
                case LIST:
                    Type genericType = field.getGenericType();
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Class fieldTargetClass = (Class) parameterizedType.getActualTypeArguments()[0];
                    if(!ft.target().getName().equals(Object.class.getName())){
                        fieldTargetClass = ft.target();
                    }
                    List fieldTargetList = copyList((List) fieldSource, fieldTargetClass);
                    writeMethod.invoke(t3, fieldTargetList);
                    break;
                default:
            }
        } catch (Exception e) {
            logger.error("FormatterType处理异常", e);
        }
    }

    private static <T> void setFormatterFields(Class<T> targetCls, Field[] declaredFields,
                                               List<Field> fields, List<String> skipFields,
                                               List<Field> formatterTypeFields) {
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(DtoSkip.class)) {
                skipFields.add(field.getName());
                continue;
            }

            if (field.isAnnotationPresent(FormatterType.class)) {
                formatterTypeFields.add(field);
                continue;
            }

            fields.add(field);
        }
        //都要放过
        skipFields.addAll(formatterTypeFields.stream().map(Field::getName).collect(Collectors.toList()));

        Field[] superDeclaredFields = targetCls.getSuperclass().getDeclaredFields();
        fields.addAll(Arrays.asList(superDeclaredFields));
    }

    private static CacheHelper cacheHelper = null;

    /**
     * 翻译被Formatter注解的字段
     *
     * @param target
     * @param fields
     * @param <T>
     */
    private static <T> void formatterHandler(Object source, T target, List<Field> fields) {
        //遍历字段，找出 Formatter注解注释的字段,获取对应字典中的值,国际化
        Locale locale = UserContentHolder.getLocale();
        //语言
        String language = locale.getLanguage();
        logger.info("copyItem-lang:{},target:{}",language,target.getClass().getSimpleName());
        Map<String, Object> params = UserContentHolder.getContext().getParams();
        // 需要处理的字段
        List<Field> formatterFields = fields.stream().parallel()
                .filter(field -> field.isAnnotationPresent(Formatter.class)).collect(Collectors.toList());

        // 数据准备
        Map<String, Set<String>> hashKeys = new HashMap<>();
        formatterFields.forEach(field -> {
            extractDefinition(source, target, language, params, hashKeys, field);
        });

        Map<String, Map<String, String>> cacheResult = new HashMap<>();
//        if (cacheHelper == null) {
//            cacheHelper = ApplicationContextUtils.getBean(CacheHelper.class);
//        }
        for (Entry<String, Set<String>> item : hashKeys.entrySet()) {
            fillCacheMap(cacheResult, item);
        }

        formatterFields.forEach(field -> {
            fieldFormat(source, target, language, params, cacheResult, field);
        });
    }

    private static <T> void fieldFormat(Object source, T target, String language, Map<String, Object> params,
                                        Map<String, Map<String, String>> cacheResult, Field field) {
        try {
            Formatter tag = field.getAnnotation(Formatter.class);
            String key = getCacheKey(tag, language, params, source);
            PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), target.getClass());
            Method readMethod = descriptor.getReadMethod();
            Object r = readMethod.invoke(target);
            if(Objects.isNull(r)){
                r = getFieldVal(source, field);
            }

            //读取属性值
            String val = Objects.nonNull(r)? r+"":"";
            String cache = "";
            if(val.length()>0) {
                cache = Optional.ofNullable(cacheResult.getOrDefault(key,new HashMap<>()).get(val)).orElse(val);
            }

            PropertyDescriptor t = new PropertyDescriptor(field.getName(), target.getClass());
            // 替换
            if (StringUtils.isBlank(tag.targetField()) || Objects.equals(tag.targetField(),field.getName())) {
                Method writeMethod = t.getWriteMethod();
                if(tag.append() && !Objects.equals(val,cache)){
                    cache = String.format(tag.format(),cache,val);
                }
                writeMethod.invoke(target, cache);
            } else {
                t = new PropertyDescriptor(tag.targetField(), target.getClass());
                if (t != null) {
                    t.getWriteMethod().invoke(target, cache);
                }
            }
        } catch (Exception ex) {
            logger.error("convert-err:source:{},field:{},{}",source,field.getName(),ex.getMessage());
        }
    }

    private static <T> Object getFieldVal(Object source, Field field)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException,NoSuchFieldException {
        Object r = null;
        Field srcField = ReflectionUtils.getField(source.getClass(),field.getName());
        if(Objects.nonNull(srcField)){
            // 类型不同，从source获取
            if(!field.getType().isAssignableFrom(srcField.getType())){
                PropertyDescriptor pd = new PropertyDescriptor(srcField.getName(), source.getClass());
                Method m = pd.getReadMethod();
                r = m.invoke(source);
            }
        }
        return r;
    }

    private static void fillCacheMap(Map<String, Map<String, String>> cacheResult, Entry<String, Set<String>> item) {
        List<String> keys = item.getValue().stream().distinct().collect(Collectors.toList());
        List<String> values = cacheHelper.hashMultiGet(item.getKey(), keys,true);
        //List<String> lowcase = new ArrayList<>();
        Map<String, String> kv = new HashMap<>();
        if (!CollectionUtils.isEmpty(values)) {
            for (int i = 0; i < values.size(); i++) {
                // key小写
                if (StringUtils.isEmpty(values.get(i))) {
                    //lowcase.add(keys.get(i));
                } else {
                    kv.put(keys.get(i), values.get(i));
                }
            }
        }
        cacheResult.put(item.getKey(), kv);
    }

    private static <T> void extractDefinition(Object source, T target, String language, Map<String, Object> params,
                                              Map<String, Set<String>> hashKeys, Field field) {
        try {
            //判断是否有注解Formatter
            PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), target.getClass());
            Method readMethod = descriptor.getReadMethod();
            //读取属性值
            Object result = readMethod.invoke(target);
            if (result instanceof Boolean) {
                result = (Boolean) result ? Enabled.YES.getValue() : Enabled.NO.getValue();
            }
            if(Objects.isNull(result)){
                // 类型不一致，target为空，source相应字段不为空
                result = getFieldVal(source, field);
            }
            //非空判断
            if (result != null) {
                Formatter annotation = field.getAnnotation(Formatter.class);
                String key = getCacheKey(annotation, language, params, source);
                //只需第一次从Redis获取并缓存
                String hashKey = result.toString();
                hashKeys.putIfAbsent(key, new HashSet<>());
                hashKeys.get(key).add(hashKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCacheKey(Formatter annotation, String language, Map params, Object source) {
        if (StringUtils.isBlank(annotation.key())) {
            String dictCode = annotation.dictCode();
            return GaeaKeyConstant.DICT_PREFIX + language + GaeaConstant.REDIS_SPLIT + dictCode;
        } else {
            //支持动态key，比如key=DICT:${DEMO}，注解Formatter的replace=["demo"]
            return formatKey(annotation.key(), annotation.replace(), params, source);
        }
    }

    /**
     * 替换占位符key
     *
     * @param key
     * @param replaceArray 替换
     * @param source
     * @return
     */
    public static String formatKey(String key, String[] replaceArray, Map<String, Object> params, Object source) {
        if (key.contains(GaeaConstant.URL_PATTERN_MARK)) {
            Map<String, Object> keyPatternMap = new HashMap<>(2);
            for (String fieldName : replaceArray) {
                try {
                    Object value = params.get(fieldName);
                    if (null == value || "".equals(value)) {
                        Field declaredField = ReflectionUtils.getField(source.getClass(),fieldName);
                        declaredField.setAccessible(true);
                        value = declaredField.get(source);
                    }
                    keyPatternMap.put(fieldName, value);
                } catch (Exception e) {
                    continue;
                }
            }

            key = GaeaUtils.replaceFormatString(key, keyPatternMap);
            if (key.contains(GaeaConstant.URL_PATTERN_MARK)) {
                return null;
            }
        }

        return key;
    }
}
