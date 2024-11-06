package com.sxlinks.modules.system.controller.report.bean;

import java.util.List;

/**
 * 树节点
 * @author lr
 * @since 2021-01-12
 *
 **/

public class TreeNode implements Comparable<TreeNode>{

    private String id;
    private String label;
    private String type;
    private String icon;
    private String parent;
    private String resUrl;
    private List<TreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(TreeNode treeNode) {
        return this.getId().compareTo(treeNode.getId());
    }
}
