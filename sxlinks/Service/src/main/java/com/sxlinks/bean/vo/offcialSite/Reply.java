package com.sxlinks.bean.vo.offcialSite;

import lombok.Data;

import java.util.Date;

@Data
public class Reply {
    private String content;
    private Date createAt;
    private Author author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
