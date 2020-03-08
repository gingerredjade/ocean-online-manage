package com.ocean.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BootstrapUiTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;
    static final Logger log = LoggerFactory.getLogger(BootstrapUiTreeNode.class);

    private transient String id;          // 当前节点ID
    private String parentId;    // 父节点ID
    private String text;        // 文件夹或者文件名称
    private String path;        // 全路径,或则部分路径,自己决定


    // 子叶节点
    private List<BootstrapUiTreeNode> children = new ArrayList<>();

    // 统计该节点分类下文档的数量
    private List<String> tags = new ArrayList<>(); // tags: ['NUM']

    public BootstrapUiTreeNode(String parentId, String text, String path) {
        super();
        this.parentId = parentId;
        this.text = text;
        this.path = path;
    }

    public BootstrapUiTreeNode(String id, String parentId, String text, String path) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.path = path;
    }


    public BootstrapUiTreeNode(String id, String parentId, String text, String path, List<BootstrapUiTreeNode> children) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.children = children;
        this.path = path;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BootstrapUiTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<BootstrapUiTreeNode> children) {
        this.children = children;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "BootstrapUiTreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", path='" + path + '\'' +
                ", parentId=" + parentId +
                '}';
    }

}
