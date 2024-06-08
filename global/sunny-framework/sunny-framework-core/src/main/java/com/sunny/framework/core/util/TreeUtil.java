package com.sunny.framework.core.util;


import com.sunny.framework.core.model.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeUtil {

    public enum SortKind {
        ASC,
        DESC
    }

    public static <V, T extends TreeNode<V>> void assemble(List<T> all) {
        all.forEach(t -> t.setParentIds(getParentIds(all, t)));
    }

    public static <V, T extends TreeNode<V>> List<V> getParentIds(List<T> items, T node) {
        return items.stream()
                .filter(t -> t.getId().equals(node.getParentId()))
                .flatMap(t -> Stream.concat(getParentIds(items, t).stream(), Stream.of(node.getParentId())))
                .collect(Collectors.toList());
    }


    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all, Predicate<T> rootPredicate) {
        return getTree(all, rootPredicate, null);
    }

    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all,
                                                             Predicate<T> rootPredicate,
                                                             Consumer<T> decoNodeFunc) {
        return getTree(all, rootPredicate, decoNodeFunc, TreeNode::getSort, SortKind.ASC);
    }

    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all,
                                                             Predicate<T> rootPredicate,
                                                             Consumer<T> decoNodeFunc,
                                                             Function<TreeNode<V>, Integer> sortFunc,
                                                             SortKind sortKind) {
        if (decoNodeFunc != null) {
            all.forEach(decoNodeFunc);
        }
        List<T> tree = all.stream().filter(rootPredicate).map(t -> buildChildren(all, t)).collect(Collectors.toList());
        if (sortFunc != null) {
            tree.forEach(t -> sortBy(t, sortFunc, sortKind));
            tree = tree.stream()
                    .sorted(sortKind == SortKind.ASC ? Comparator.comparing(sortFunc) : Comparator.comparing(sortFunc).reversed())
                    .collect(Collectors.toList());
        }
        return tree;
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> T buildChildren(List<T> items, T node) {
        List<T> children = new ArrayList<>();
        items.forEach(t -> {
            if (node.getId().equals(t.getParentId())) {
                children.add(buildChildren(items, t));
            }
        });
        if (!children.isEmpty()) {
            Optional.ofNullable(node.getChildren())
                    .ifPresentOrElse(t -> t.addAll((List) children), () -> node.setChildren(children));
        }
        return node;
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends TreeNode<V>> void sortBy(T item, Function<TreeNode<V>, Integer> sortFunc, SortKind sortKind) {
        if (CollectionUtils.isNotEmpty(item.getChildren())) {
            item.setChildren(item.getChildren().stream().sorted(sortKind == SortKind.ASC ? Comparator.comparing(sortFunc) : Comparator.comparing(sortFunc).reversed()).collect(Collectors.toList()));
            item.getChildren().forEach(t -> sortBy((T) t, sortFunc, sortKind));
        }
    }

}
