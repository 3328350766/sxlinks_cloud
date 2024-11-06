package com.sxlinks.bean.vo.offcialSite;

import lombok.Data;

@Data
public class News {
    /**
     * 资讯标题
     */
    private String desc;
    /**
     * 详情链接
     */
    private String url;
    /**
     * 图片地址
     */
    private String src;
    public News(){

    }

    public News(String desc, String url, String src) {
        this.desc = desc;
        this.url = url;
        this.src = src;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
