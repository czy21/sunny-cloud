package com.sunny.framework.core.util;


import com.sunny.framework.core.model.TreeNode;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
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

    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all, V rootValue) {
        return buildByPath(supplier, all, rootValue, null, null);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all, V rootValue, Consumer<T> decoNodeFunc, Comparator<T> sortComparator) {
        T root = supplier.get();
        root.setId(rootValue);

        for (T t : all) {
            processPath(supplier, root, t, decoNodeFunc, sortComparator);
        }

        return (List<T>) root.getChildren();
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> void processPath(Supplier<T> supplier, T root, T item, Consumer<T> decoNodeFunc, Comparator<T> sortComparator) {
        T current = root;
        for (V t : Optional.ofNullable(item.getPathIds()).orElse(new ArrayList<>())) {
            V currentId = current.getId();
            if (current.getChildren() == null) {
                current.setChildren(new ArrayList<>());
            }
            T node = (T) current.getChildren().stream().filter(p -> t.equals(p.getId())).findFirst().orElse(null);
            if (node == null) {
                node = supplier.get();
                node.setId(t);
                node.setParentId(currentId);
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

    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all) {
        return build(supplier, all, null);
    }

    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all, V rootValue) {
        return build(supplier, all, rootValue, null);
    }

    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all, V rootValue, Consumer<T> decoNodeFunc) {
        return build(supplier, all, rootValue, decoNodeFunc, null);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> List<T> build(Supplier<T> supplier, List<T> all, V rootValue, Consumer<T> decoNodeFunc, Comparator<T> sortComparator) {

        T root = supplier.get();
        root.setId(rootValue);

        buildChildren(all, root, decoNodeFunc, sortComparator);

        return (List<T>) root.getChildren();
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> T buildChildren(List<T> all, T node, Consumer<T> decoNodeFunc, Comparator<T> sortComparator) {

        List<T> children = all.stream()
                .filter(t -> (node.getId() == t.getParentId()) || (node.getId() != null && node.getId().equals(t.getParentId())))
                .map(t -> buildChildren(all, t, decoNodeFunc, sortComparator))
                .collect(Collectors.toList());

        Optional.ofNullable((List<T>) node.getChildren())
                .ifPresentOrElse(t -> t.addAll(children), () -> node.setChildren(children));

        if (decoNodeFunc != null) {
            decoNodeFunc.accept(node);
        }

        if (sortComparator != null) {
            ((List<T>) node.getChildren()).sort(sortComparator);
        }

        return node;
    }
}
