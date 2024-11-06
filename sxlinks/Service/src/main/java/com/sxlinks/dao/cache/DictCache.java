package com.sxlinks.dao.cache;

import com.sxlinks.bean.entity.system.Dict;

import java.util.List;

/**
 * 字典缓存
 *
 * @author zt
 * @version 2018/12/23 0023
 */
public interface DictCache  extends Cache{

    List<Dict> getDictsByPname(String dictName);
    String getDict(Long dictId);
}
