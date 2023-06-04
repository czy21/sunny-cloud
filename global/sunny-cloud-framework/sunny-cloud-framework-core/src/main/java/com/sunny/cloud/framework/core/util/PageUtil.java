package com.sunny.cloud.framework.core.util;

import com.github.pagehelper.PageInfo;
import com.sunny.cloud.framework.core.model.PagingResult;

public class PageUtil {
    /**
     * 转换为pageResult
     *
     * @param page 分页结果
     */
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

    /**
     * 转换分页结果;PO转DTO
     *
     * @param page
     * @param convertListFunc
     * @param <T>
     * @param <S>
     * @return
     */
    public static <S, T> PagingResult<T> convert(PageInfo<S> page, Function<List<S>, List<T>> convertListFunc) {
        if (page == null) {
            return null;
        }
        PagingResult<T> pagingResult = new PagingResult<>();
        pagingResult.setTotal(page.getTotal());
        pagingResult.setList(convertListFunc.apply(page.getList()));
        pagingResult.setPage(page.getPageNum());
        pagingResult.setPageSize(page.getPageSize());
        return pagingResult;
    }


}