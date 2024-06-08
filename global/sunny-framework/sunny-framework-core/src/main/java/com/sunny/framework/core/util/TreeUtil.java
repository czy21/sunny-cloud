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
        return getTree(all, rootPredicate, decoNodeFunc, SortKind.ASC);
    }

    public static <V, T extends TreeNode<V>> List<T> getTree(List<T> all,
                                                             Predicate<T> rootPredicate,
                                                             Consumer<T> decoNodeFunc,
                                                             SortKind sortKind) {
        if (decoNodeFunc != null) {
            all.forEach(decoNodeFunc);
        }
        Comparator<TreeNode<V>> sortComparator = sortKind == SortKind.ASC
                ? Comparator.comparing(TreeNode::getSort)
                : Comparator.comparing(TreeNode<V>::getSort).reversed();
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
