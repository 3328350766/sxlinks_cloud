package com.sxlinks.dao.cache;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**

 **/

public interface RedisDao {
    //频道发布消息
    void  publish(String channel, String data);
    /**
     * 添加
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean cacheValue(String key, String value, long time);

    /**
     * 添加
     *
     * @param key
     * @param value
     * @return
     */
    boolean cacheValue(String key, String value);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsValueKey(String key);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsSetKey(String key);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsListKey(String key);

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    boolean containsKey(String key);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    String getValue(String key);

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeValue(String key);

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeSet(String key);

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    boolean removeList(String key);

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean cacheSet(String key, String value, long time);

    /**
     * 缓存Set
     *
     * @param key
     * @param value
     * @return
     */
    boolean cacheSet(String key, String value);

    /**
     * 缓存Set
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheSet(String k, Set<String> v, long time);

    /**
     * 缓存Set
     *
     * @param k
     * @param v
     * @return
     */
    boolean cacheSet(String k, Set<String> v);

    /**
     * 获取Set
     *
     * @param k
     * @return
     */
    Set<String> getSet(String k);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheList(String k, String v, long time);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @return
     */
    boolean cacheList(String k, String v);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @param time
     * @return
     */
    boolean cacheList(String k, List<String> v, long time);

    /**
     * 缓存List
     *
     * @param k
     * @param v
     * @return
     */
    boolean cacheList(String k, List<String> v);

    /**
     * 获取List
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    List<String> getList(String k, long start, long end);

    /**
     * 获取页码
     *
     * @param key
     * @return
     */
    long getListSize(String key);

    /**
     * 获取页码
     *
     * @param listOps
     * @param k
     * @return
     */
    long getListSize(ListOperations<String, String> listOps, String k);

    /**
     * 移除list缓存
     *
     * @param k
     * @return
     */
    boolean removeOneOfList(String k);


    /** -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key
     * @param field
     * @return
     */
    public Object hGet(String key, String field);

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hGetAll(String key);

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @param fields
     * @return
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields);
    public void hPut(String key, String hashKey, String value);

    public void hPutAll(String key, Map<String, String> maps);
    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public Boolean hPutIfAbsent(String key, String hashKey, String value);
    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public Long hDelete(String key, Object... fields);

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hExists(String key, String field);

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public Long hIncrBy(String key, Object field, long increment) ;

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public Double hIncrByFloat(String key, Object field, double delta) ;
    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    public Set<Object> hKeys(String key);

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public Long hSize(String key);

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    public List<Object> hValues(String key);

    /**
     * 迭代哈希表中的键值对
     *
     * @param key
     * @param options
     * @return
     */
    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) ;


}
