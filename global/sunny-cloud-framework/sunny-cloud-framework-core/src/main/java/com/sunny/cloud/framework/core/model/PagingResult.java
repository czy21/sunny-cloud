package com.sunny.cloud.framework.core.model;

import java.util.List;


public class PagingResult<E> {
    private final static int DEFAULT_CURRENT = 1;
    private final static int DEFAULT_PAGE_SIZE = 10;
    private int total;
    private int page;
    private int pageSize;
    private List<E> list;

    public PagingResult() {
        this(DEFAULT_CURRENT, DEFAULT_PAGE_SIZE);
    }

    public PagingResult(int current, int pageSize) {
        this.page = current;
        this.pageSize = pageSize;
    }

    public PagingResult(int current, int pageSize, int total) {
        this.page = current;
        this.pageSize = pageSize;
        this.setTotal(total);
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<E> getList() {
        return this.list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return (this.getTotal() + this.getPageSize() - 1) / this.getPageSize();
    }
}

