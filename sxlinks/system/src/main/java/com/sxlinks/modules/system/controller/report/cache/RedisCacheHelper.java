package com.sxlinks.modules.system.controller.report.cache;

import com.sxlinks.modules.system.controller.report.constant.GaeaConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lr
 * @since 2021-06-22
 */
public class RedisCacheHelper implements CacheHelper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取指定key的String类型缓存
     *
     * @param key
     * @return
     */
    @Override
    public String stringGet(String key) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return "";
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        return operations.get();
    }

    /**
     * 获取指定key的String类型缓存
     *
     * @param key
     * @return
     */
    @Override
    public Boolean setIfAbsent(String key, String value) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);

        return operations.setIfAbsent(value);
    }


    /**
     * 增加1
     *
     * @param key
     * @return
     */
    @Override
    public Long increment(String key) {
        String regKey = regKey(key);
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        return operations.increment();
    }

    /**
     * 设置失效时间
     *
     * @param key
     * @param timeUnit
     * @param timeout
     */
    @Override
    public void expire(String key, TimeUnit timeUnit, Long timeout) {
        String regKey = regKey(key);
        stringRedisTemplate.expire(regKey, timeout, timeUnit);
    }

    /**
     * 增加1
     *
     * @param key
     * @return
     */
    @Override
    public Long increment(String key, Long step) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey(key));
        return operations.increment(step);
    }

    /**
     * 是否存在指定KEY
     *
     * @param key
     * @return
     */
    @Override
    public boolean exist(String key) {
        return stringRedisTemplate.hasKey(regKey(key));
    }

    /**
     * 模糊匹配,
     *
     * @param pattern
     * @return
     */
    @Override
    public Set<String> keys(String pattern) {
        // 危险操作，线上可能会禁用该指令
        // return stringRedisTemplate.keys(pattern);
        ScanOptions options = ScanOptions.scanOptions().count(1000).match(pattern).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
        Set<String> result = new HashSet<>();
        try (Cursor cursor = stringRedisTemplate.executeWithStickyConnection(
                redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize))) {
            while (cursor.hasNext()) {
                result.add(cursor.next().toString());
            }
        } catch (Exception ex) {

        }
        return result;
    }


    /**
     * 设置指定key的String类型缓存
     *
     * @param key
     * @param value 缓存值
     * @return
     */
    @Override
    public void stringSet(String key, String value) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        operations.set(value);
    }

    /**
     * 设置指定key的String类型缓存，包含过期时间
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit 时间单位
     */
    @Override
    public void stringSetExpire(String key, String value, long time, TimeUnit timeUnit) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        operations.set(value, time, timeUnit);
    }

    /**
     * 设置指定key的String类型缓存，包含过期时间
     *
     * @param key
     * @param value
     * @param seconds
     */
    @Override
    public void stringSetExpire(String key, String value, long seconds) {
        stringSetExpire(regKey(key), value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取指定key的hash缓存
     *
     * @param key
     * @return
     */
    @Override
    public Map<String, String> hashGet(String key) {
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey(key));

        return operations.entries();
    }

    @Override
    public List<String> hashMultiGet(String key, Collection<String> keys, boolean includeDisabled) {
        if (Objects.isNull(key) || CollectionUtils.isEmpty(keys)) {
            return Arrays.asList("");
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey(key));
        List<String> itemKeys = keys.stream().map(t -> regKey(t)).collect(Collectors.toList());
        List<String> list = operations.multiGet(itemKeys);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, Optional.ofNullable(list.get(i)).orElse(""));
        }
        if (includeDisabled) {
            //list = list.stream().filter(i->StringUtils.isEmpty(i)).collect(Collectors.toList());
            Map<Integer, String> nullKeys = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                if (StringUtils.isEmpty(list.get(i))) {
                    nullKeys.put(i, itemKeys.get(i));
                }
            }
            if (nullKeys.isEmpty()) {
                return list;
            }
            // 查询禁用的数据
            BoundHashOperations<String, String, String> d =
                    stringRedisTemplate.boundHashOps(regKey(key).concat(getDisabledKey()));
            List<String> nullItemKeys = new ArrayList<>(nullKeys.values());
            List<String> disabledVals = d.multiGet(nullItemKeys);
            if (!CollectionUtils.isEmpty(disabledVals)) {
                Map<String, String> dis = new HashMap<>();
                for (int i = 0; i < disabledVals.size(); i++) {
                    dis.put(nullItemKeys.get(i), Optional.ofNullable(disabledVals.get(i)).orElse(""));
                }
                // 替换为禁用的数据
                for (Entry<Integer, String> src : nullKeys.entrySet()) {
                    list.set(src.getKey(), dis.get(src.getValue()));
                }
            }
        }
        return list;
    }

    /**
     * 获取指定key的hash中对应的值
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public String hashGetString(String key, String hashKey) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return "";
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return "";
        }

        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        if (hashKey.contains(GaeaConstant.SPLIT)) {
            String[] split = hashKey.split(GaeaConstant.SPLIT);
            String reduce = Arrays.stream(split).reduce("", (all, item) -> {
                if (StringUtils.isBlank(all)) {
                    all = operations.get(item);
                } else {
                    all = all + "," + operations.get(item);
                }
                return all;
            });
            return reduce;
        }

        return operations.entries().get(regHashKey);
    }

    /**
     * 删除Hash中指定key的值
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public void hashDel(String key, String hashKey) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        operations.delete(regHashKey);
    }


    /**
     * 删除Hash中指定key的值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    @Override
    public void hashBatchDel(String key, Set<String> hashKeys, boolean toDisable) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey) || CollectionUtils.isEmpty(hashKeys)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        if (toDisable) {
            BoundHashOperations<String, String, String> disableOpt = stringRedisTemplate.boundHashOps(regKey.concat(getDisabledKey()));
            List<String> keys = hashKeys.stream().collect(Collectors.toList());
            List<String> dels = operations.multiGet(hashKeys);
            for (int i = 0; i < keys.size(); i++) {
                disableOpt.put(keys.get(i), Optional.ofNullable(dels.get(i)).orElse(""));
            }
        }

        operations.delete(hashKeys.toArray(new String[0]));
    }

    /**
     * 判断指定key的hash中包含指定hashKey
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public boolean hashExist(String key, String hashKey) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return false;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        return operations.hasKey(regHashKey);
    }

    /**
     * 判断指定key的hash中包含指定hashKeys中任何一个
     *
     * @param key
     * @param hashKeys
     * @return
     */
    @Override
    public boolean hashAnyExist(String key, String[] hashKeys) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        for (String hashKey : hashKeys) {
            if (operations.hasKey(regKey(hashKey))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 设置指定key的hash缓存
     *
     * @param key
     * @param hashKey
     * @param hashValue
     * @return
     */
    @Override
    public void hashSet(String key, String hashKey, String hashValue) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey) || StringUtils.isBlank(hashKey)) {
            return;
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey) || StringUtils.isBlank(hashValue)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        operations.put(regHashKey, hashValue);
    }


    /**
     * 设置指定key的hash缓存
     *
     * @param hash
     * @return
     */
    @Override
    public void hashSet(String key, Map<String, String> hash) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        if (CollectionUtils.isEmpty(hash)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        operations.putAll(hash);
    }

    /**
     * 删除指定key
     *
     * @param key
     * @return
     */
    @Override
    public boolean delete(String key) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        return stringRedisTemplate.delete(regKey);
    }

    /**
     * 删除指定key
     *
     * @param keys
     * @return
     */
    @Override
    public boolean delete(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return false;
        }

        Long count = stringRedisTemplate.delete(keys.stream().map(t -> regKey(t)).collect(Collectors.toList()));
        return count > 0;
    }

    /**
     * 向集合中添加
     *
     * @param key
     * @param values
     * @return
     */
    @Override
    public Long setAdd(String key, String[] values) {
        return setAdd(regKey(key), values, false);
    }


    /**
     * 向集合中添加
     *
     * @param key
     * @param values
     * @param clear  是否清空旧数据
     * @return
     */
    @Override
    public Long setAdd(String key, String[] values, boolean clear) {
        String regKey = regKey(key);
        if (clear) {
            stringRedisTemplate.delete(regKey);
        }
        if (values != null && values.length == 0) {
            return 0L;
        }
        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(regKey);
        return setOperations.add(values);
    }

    /**
     * 返回对应key的集合
     *
     * @param key
     * @return
     */
    @Override
    public Set<String> setMembers(String key) {
        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(regKey(key));
        return setOperations.members();
    }

    /**
     * 判断集合中是否有对应的value
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Boolean setExist(String key, String value) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }

        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(regKey);
        return setOperations.isMember(value);
    }
}
