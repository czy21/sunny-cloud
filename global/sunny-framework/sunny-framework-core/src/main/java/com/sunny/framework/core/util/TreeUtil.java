package com.sunny.framework.core.util;


import com.sunny.framework.core.model.TreeNode;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
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
    public static <V, T extends TreeNode<V>> void processPath(T rootNode, T item, Comparator<TreeNode<V>> sortComparator) {
        TreeNode<V> current = rootNode;
        for (V t : Optional.ofNullable(item.getPathIds()).orElse(new ArrayList<>())) {
            V currentId = current.getId();
            if (current.getChildren() == null) {
                current.setChildren(new ArrayList<>());
            }
            TreeNode<V> node = current.getChildren().stream().filter(p -> p.equals(t)).findFirst().orElse(null);
            if (node == null) {
                node = new TreeNode<>() {
                    @Override
                    public V getId() {
                        return t;
                    }

                    @Override
                    public V getParentId() {
                        return currentId;
                    }

                    @Override
                    public List<? extends TreeNode<V>> getChildren() {
                        return null;
                    }
                };
                ((List) current.getChildren()).add(node);
            }
            current = node;
            current.getChildren().sort(sortComparator);
        }
    }


    public static <V, T extends TreeNode<V>> List<T> buildByPath(List<T> all) {
        return buildByPath(all, null);
    }

    public static <V, T extends TreeNode<V>> List<T> buildByPath(List<T> all, V rootValue) {
        return buildByPath(all, rootValue, null);
    }

    public static <V, T extends TreeNode<V>> List<T> buildByPath(List<T> all, V rootValue, Consumer<TreeNode<V>> decoNodeFunc) {
        return buildByPath(all, rootValue, decoNodeFunc, false);
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> List<T> buildByPath(List<T> all, V rootValue, Consumer<TreeNode<V>> decoNodeFunc, boolean isDesc) {
        Comparator<TreeNode<V>> sortComparator = !isDesc ? Comparator.naturalOrder() : Comparator.reverseOrder();
        TreeNode<V> root = new TreeNode<>() {
            @Override
            public V getId() {
                return rootValue;
            }

            @Override
            public V getParentId() {
                return null;
            }

            @Override
            public List<? extends TreeNode<V>> getChildren() {
                return new ArrayList<>();
            }
        };
        if (decoNodeFunc != null) {
            all.forEach(t -> {
                decoNodeFunc.accept(t);
                processPath(root, t, sortComparator);
            });
        } else {
            all.forEach(t -> {
                processPath(root, t, sortComparator);
            });
        }
        List<T> tree = (List<T>) root.getChildren();
        tree.sort(sortComparator);
        return tree;
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
