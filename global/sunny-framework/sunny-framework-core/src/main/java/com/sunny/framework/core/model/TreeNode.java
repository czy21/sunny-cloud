package com.sunny.framework.core.model;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public interface TreeNode<T> extends Comparable<TreeNode<T>> {

    T getId();

    void setId(T id);

    T getParentId();

    void setParentId(T parentId);

    List<T> getParentIds();

    void setParentIds(List<T> parentIds);

    List<? extends TreeNode<T>> getChildren();

    void setChildren(List<? extends TreeNode<T>> children);

    default Integer getSort() {
        return 0;
    }

    @Override
    default int compareTo(TreeNode<T> o) {
        return ObjectUtils.compare(this.getSort(), o.getSort());
    }
}
