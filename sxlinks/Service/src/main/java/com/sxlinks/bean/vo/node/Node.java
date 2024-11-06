package com.sxlinks.bean.vo.node;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Node
 *
 * @author gaoliang
 * @version 2018/11/24 0024
 */
@Data
public class Node {
    private Long id;
    private Long pid;
    private String name;
    private Boolean checked;
    private List<Node> children = new ArrayList<>(10);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
