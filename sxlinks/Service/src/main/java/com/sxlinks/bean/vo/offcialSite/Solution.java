package com.sxlinks.bean.vo.offcialSite;

import lombok.Data;

@Data
public class Solution {

    private Long id;
    private String name;
    private String img;
    public Solution(){

    }

    public Solution(Long id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
