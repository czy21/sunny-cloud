package com.sunny.framework.core.util;


import com.sunny.framework.core.model.TreeNode;

import javax.swing.text.html.Option;
import java.util.*;
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

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> void processPath(Supplier<T> supplier, T root, T item, Consumer<T> decoNodeFunc) {
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
            current = node;
            if (decoNodeFunc != null) {
                decoNodeFunc.accept(current);
            }
        }
    }


    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all) {
        return buildByPath(supplier, all, null);
    }

    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all, V rootValue) {
        return buildByPath(supplier, all, rootValue, null);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> List<T> buildByPath(Supplier<T> supplier, List<T> all, V rootValue, Consumer<T> decoNodeFunc) {
        T root = supplier.get();
        root.setId(rootValue);
        root.setChildren(new ArrayList<>());
        for (T t : all) {
            processPath(supplier, root, t, decoNodeFunc);
        }
        return (List<T>) root.getChildren();
    }

    public static <V, T extends TreeNode<V>> List<T> build(List<T> all, Predicate<T> rootPredicate) {
        return build(all, rootPredicate, null);
    }

    public static <V, T extends TreeNode<V>> List<T> build(List<T> all, Predicate<T> rootPredicate, Consumer<T> decoNodeFunc) {
        return build(all, rootPredicate, decoNodeFunc, false);
    }

    public static <V, T extends TreeNode<V>> List<T> build(List<T> all, Predicate<T> rootPredicate, Consumer<T> decoNodeFunc, boolean isDesc) {
        if (decoNodeFunc != null) {
            all.forEach(decoNodeFunc);
        }
        Comparator<TreeNode<V>> sortComparator = !isDesc ? Comparator.naturalOrder() : Comparator.reverseOrder();
        return all.stream()
                .filter(rootPredicate)
                .map(t -> buildChildren(all, t, sortComparator))
                .sorted(sortComparator)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> T buildChildren(List<T> items, T node, Comparator<TreeNode<V>> sortComparator) {
        List<T> children = items.stream()
                .filter(t -> node.getId().equals(t.getParentId()))
                .map(t -> buildChildren(items, t, sortComparator))
                .collect(Collectors.toList());
        Optional.ofNullable(node.getChildren())
                .ifPresentOrElse(t -> t.addAll((List) children), () -> node.setChildren(children));
        node.getChildren().sort(sortComparator);
        return node;
    }
}
