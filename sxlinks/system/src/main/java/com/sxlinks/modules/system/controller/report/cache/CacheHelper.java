package com.sxlinks.modules.system.controller.report.cache;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存帮助类
 * @author lr
 * @since 2021-01-12
 */
public interface CacheHelper {

    /**
     * 获取指定key的String类型缓存
     * @param key
     * @return
     */
    default String stringGet(String key) {
        return null;
    }

    /**
     * 获取指定key的String类型缓存
     * @param key
     * @return
     */
    default Boolean setIfAbsent(String key, String value) {
        return false;
    }


    /**
     * 增加1
     * @param key
     * @return
     */
    default Long increment(String key) {
        return 0L;
    }

    /**
     * 设置失效时间
     * @param key
     * @param timeUnit
     * @param timeout
     */
    default void expire(String key, TimeUnit timeUnit, Long timeout) {
    }

    /**
     * 增加1
     * @param key
     * @return
     */
    default Long increment(String key, Long step) {
        return 0L;
    }

    /**
     * 是否存在指定KEY
     * @param key
     * @return
     */
    default boolean exist(String key) {
        return false;
    }

    /**
     * 模糊匹配
     * @param pattern
     * @return
     */
    default Set<String> keys(String pattern) {
        return new HashSet<>();
    }


    /**
     * 设置指定key的String类型缓存
     * @param key
     * @param value 缓存值
     * @return
     */
    default void stringSet(String key, String value) {

    }

    /**
     * 设置指定key的String类型缓存，包含过期时间
     * @param key
     * @param value
     * @param time
     * @param timeUnit 时间单位
     */
    default void stringSetExpire(String key, String value, long time, TimeUnit timeUnit) {

    }

    /**
     * 去掉前后空格
     * @param key
     * @return
     */
    default String regKey(String key) {
        return key.trim();
        //.toLowerCase();
    }


    /**
     * 设置指定key的String类型缓存，包含过期时间
     * @param key
     * @param value
     * @param seconds
     */
    default void stringSetExpire(String key, String value, long seconds) {
    }

    /**
     * 获取指定key的hash缓存
     * @param key
     * @return
     */
    default Map<String, String> hashGet(String key) {
        return new HashMap<>();
    }

    /***
     * 获取指定key的hash中对应的值
     *
     * @param key
     * @param keys
     * @return
     */
    default List<String> hashMultiGet(String key, Collection<String> keys){
        return hashMultiGet(key,keys,false);
    }

    /***
     * 获取指定key的hash中对应的值
     *
     * @param key
     * @param keys
     * @param includeDisabled 是否包括已禁用的数据
     * @return
     */
    default List<String> hashMultiGet(String key, Collection<String> keys, boolean includeDisabled){
        return null;
    }

    /***
     * disabled key后缀
     * @return
     */
    default String getDisabledKey(){
        return ":DISABLED";
    }

    /***
     * 批量获取hashSet
     * @param key
     * @param keys
     * @param includeDisabled
     * @return
     */
    default Map<String,String> multiGet(String key, List<String> keys, boolean includeDisabled){
        List<String> list = hashMultiGet(key,keys,includeDisabled);
        Map<String,String> ret = new HashMap<>();
        for(int i=0; i< keys.size();i++){
            if(Objects.isNull(list.get(i))){
                continue;
            }
            ret.put(keys.get(i),list.get(i));
        }
        return ret;
    }

    /**
     * 获取指定key的hash中对应的值
     * @param key
     * @param hashKey
     * @return
     */
    default String hashGetString(String key, String hashKey) {
        return null;
    }

    /**
     * 删除Hash中指定key的值
     * @param key
     * @param hashKey
     * @return
     */
    default void hashDel(String key, String hashKey) {
    }


    /**
     * 删除Hash中指定key的值
     * @param key
     * @param hashKeys
     * @return
     */
    default void hashBatchDel(String key, Set<String> hashKeys) {
        hashBatchDel(key,hashKeys,false);
    }

    /**
     * 删除Hash中指定key的值
     * @param key
     * @param hashKeys
     * @param toDisable 删除的数据 key-rename
     * @return
     */
    default void hashBatchDel(String key, Set<String> hashKeys, boolean toDisable) {

    }

    /**
     * 判断指定key的hash中包含指定hashKey
     * @param key
     * @param hashKey
     * @return
     */
    default boolean hashExist(String key, String hashKey) {
        return false;
    }

    /**
     * 判断指定key的hash中包含指定hashKeys中任何一个
     * @param key
     * @param hashKeys
     * @return
     */
    default boolean hashAnyExist(String key, String[] hashKeys) {
        return false;
    }



    /**
     * 设置指定key的hash缓存
     * @param key
     * @param hashKey
     * @param hashValue
     * @return
     */
    default void hashSet(String key, String hashKey, String hashValue) {

    }


    /**
     * 设置指定key的hash缓存
     * @param hash
     * @return
     */
    default void hashSet(String key, Map<String, String> hash) {

    }

    /**
     * 删除指定key
     * @param key
     * @return
     */
    default boolean delete(String key) {
        return false;
    }

    /**
     * 删除指定key
     * @param keys
     * @return
     */
    default boolean delete(List<String> keys) {
        return false;
    }

    /**
     * 向集合中添加
     * @param key
     * @param values
     * @return
     */
    default Long setAdd(String key, String[] values) {
        return 0L;
    }


    /**
     * 向集合中添加
     * @param key
     * @param values
     * @param clear 是否清空旧数据
     * @return
     */
    default Long setAdd(String key, String[] values, boolean clear) {
        return 0L;
    }

    /**
     * 返回对应key的集合
     * @param key
     * @return
     */
    default Set<String> setMembers(String key) {
        return new HashSet<>();
    }

    /**
     * 判断集合中是否有对应的value
     * @param key
     * @param value
     * @return
     */
    default Boolean setExist(String key, String value) {
        return false;
    }
}
