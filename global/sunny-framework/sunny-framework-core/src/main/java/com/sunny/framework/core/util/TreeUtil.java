package com.sunny.framework.core.util;


import com.sunny.framework.core.model.TreeNode;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeUtil {

    public static <V, T extends TreeNode<V>> void assemble(List<T> all) {
        all.forEach(t -> t.setParentIds(getParentIds(all, t)));
    }

    public static <V, T extends TreeNode<V>> List<V> getParentIds(List<T> items, T node) {
        return items.stream()
                .filter(t -> t.getId().equals(node.getParentId()))
                .flatMap(t -> Stream.concat(getParentIds(items, t).stream(), Stream.of(node.getParentId())))
                .collect(Collectors.toList());
    }

    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all) {
        return buildByPath(supplier, all, null);
    }

    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all, Consumer<T> decoNodeFunc) {
        return buildByPath(supplier, all, decoNodeFunc, null);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all, Consumer<T> decoNodeFunc, Comparator<T> sortComparator) {

        T root = supplier.get();

        for (T t : all) {
            if (t.getPathIds() == null) {
                continue;
            }
            T current = root;
            for (int i = 0; i < t.getPathIds().size(); i++) {
                if (current.getChildren() == null) {
                    current.setChildren(new ArrayList<>());
                }
                V p = t.getParentIds().get(i);
                T node = null;
                for (TreeNode<V> child : current.getChildren()) {
                    if (Objects.equals(p, child.getId())) {
                        node = (T) child;
                        break;
                    }
                }
                if (node == null) {
                    node = supplier.get();
                    node.setId(p);
                    node.setParentId(current.getId());
                    node.setLevel(i + 1);
                    ((List<T>) current.getChildren()).add(node);
                }
                if (decoNodeFunc != null) {
                    decoNodeFunc.accept(node);
                }
                if (sortComparator != null) {
                    ((List<T>) node.getChildren()).sort(sortComparator);
                }
                current = node;
            }
        }
        return (List<T>) root.getChildren();
    }

    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all) {
        return build(supplier, all, null);
    }

    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all, Consumer<T> decoNodeFunc) {
        return build(supplier, all, decoNodeFunc, null);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all, Consumer<T> decoNodeFunc, Comparator<T> sortComparator) {

        T root = supplier.get();

        buildChildren(all, root, decoNodeFunc, sortComparator, 0);

        return (List<T>) root.getChildren();
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> void buildChildren(List<T> all, T node, Consumer<T> decoNodeFunc, Comparator<T> sortComparator, int level) {

        if (node.getChildren() == null) {
            node.setChildren(new ArrayList<>());
        }

        for (T t : all) {
            if (Objects.equals(node.getId(), t.getParentId())) {
                buildChildren(all, t, decoNodeFunc, sortComparator, level + 1);
                ((List<T>) node.getChildren()).add(t);
            }
        }

        node.setLevel(level);

        if (decoNodeFunc != null) {
            decoNodeFunc.accept(node);
        }

        if (sortComparator != null) {
            ((List<T>) node.getChildren()).sort(sortComparator);
        }
    }
}
