package com.sxlinks.bean.dictmap;

import com.sxlinks.bean.dictmap.base.AbstractDictMap;

public class KeywordToutiaoDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id","编号");
        put("name","名称");
        put("description","描述");
    }

    @Override
    protected void initBeWrapped() {
       // putFieldWrapperMethodName("id",get(""));
    }
}
