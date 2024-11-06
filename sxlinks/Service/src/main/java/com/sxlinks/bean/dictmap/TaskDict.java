package com.sxlinks.bean.dictmap;

import com.sxlinks.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author gaoliang
 * @date 2017-05-06 15:43
 */
public class TaskDict extends AbstractDictMap {

    @Override
    public void init() {
        put("taskId","任务id");
        put("name","任务名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
