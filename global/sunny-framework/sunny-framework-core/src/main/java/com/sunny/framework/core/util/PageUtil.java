package com.sunny.framework.core.util;

import com.github.pagehelper.PageInfo;
import com.sunny.framework.core.model.PagingResult;

import java.util.List;
import java.util.function.Function;

public class PageUtil {
    public static <T> PagingResult<T> convert(PageInfo<T> page) {
        if (page == null) {
            return null;
        }
        PagingResult<T> pagingResult = new PagingResult<>();
        pagingResult.setTotal((int) page.getTotal());
        pagingResult.setList(page.getList());
        pagingResult.setPage(page.getPageNum());
        pagingResult.setPageSize(page.getPageSize());
        return pagingResult;
    }

    public static <S, T> PagingResult<T> convert(PageInfo<S> page, Function<List<S>, List<T>> convertListFunc) {
        if (page == null) {
            return null;
        }
        PagingResult<T> pagingResult = new PagingResult<>();
        pagingResult.setTotal((int)page.getTotal());
        pagingResult.setList(convertListFunc.apply(page.getList()));
        pagingResult.setPage(page.getPageNum());
        pagingResult.setPageSize(page.getPageSize());
        return pagingResult;
    }


}