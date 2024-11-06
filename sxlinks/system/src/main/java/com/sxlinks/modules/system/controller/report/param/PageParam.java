package com.sxlinks.modules.system.controller.report.param;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 分页参数
 * @author lr
 * @since 2021-01-12
 */
public class PageParam {

    private boolean pageable = false;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
    /**
     * 页数
     */
    private Integer pageNumber = 1;

    /**
     * 每页的记录数
     */
    private Integer pageSize = 10;

    /**
     * 升序还是降序
     */
    private String order;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 偏移量
     */
    private Integer offset;

    /**
     * 批量操作idList
     */
    private List<Long> ids;

    public Integer getOffset() {
        return (pageNumber - 1) * pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }
}
