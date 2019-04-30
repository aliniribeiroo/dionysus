package com.aliniribeiro.dionysus.model.common;

import java.util.Collection;

public class PageResult<T> {

    private Collection<T> rows;
    private Long pageCount;
    private Long totalCount;

    public PageResult(Collection<T> rows, Long totalCount) {
        this.rows = rows;
        this.pageCount = (long) rows.size();
        this.totalCount = totalCount;
    }

    public Collection<T> getRows() {
        return rows;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }
}
