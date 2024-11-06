package com.sxlinks.bean.dictmap;

import com.sxlinks.bean.dictmap.base.AbstractDictMap;

public class CommonDict extends AbstractDictMap {
    @Override
    public void init() {
        put("name","名称");
        put("code","编码");
        put("title","标题");
    }

    @Override
    protected void initBeWrapped() {

    }
}
