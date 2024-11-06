package com.sxlinks.bean.dictmap;

import com.sxlinks.bean.dictmap.base.AbstractDictMap;

/**
 * 采集规则的映射
 *
 * @author gaoliang
 * @date 2019-05-20 11:40
 */
public class TelephoneRuleDict extends AbstractDictMap {

    @Override
    public void init() {
        put("tips","备注");
    }

    @Override
    protected void initBeWrapped() {

    }

}
