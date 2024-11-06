package com.sxlinks.bean.dictmap;

import com.sxlinks.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author gaoliang
 * @date 2017-05-06 15:43
 */
public class CfgDict extends AbstractDictMap {

    @Override
    public void init() {
        put("cfgId","参数id");
        put("cfgName","参数名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
