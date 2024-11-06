package com.sxlinks.bean.vo.offcialSite;

import lombok.Data;

@Data
public class BannerItem {
    private String url="javascript:";
    private String img;
    private String title="";

    public BannerItem(){

    }

    public BannerItem(String url, String img, String title) {
        this.url = url;
        this.img = img;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
