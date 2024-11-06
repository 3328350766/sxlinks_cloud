package com.sxlinks.bean.vo.offcialSite;

import lombok.Data;

import java.util.List;

@Data
public class Banner {
    private Integer index = 0;
    private List<com.sxlinks.bean.entity.cms.Banner> list;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<com.sxlinks.bean.entity.cms.Banner> getList() {
        return list;
    }

    public void setList(List<com.sxlinks.bean.entity.cms.Banner> list) {
        this.list = list;
    }
}
