package com.aliniribeiro.dionysus.model.common;

public class PageRequest {

    private Long page = 1l;// 1 is the first page
    private Long limit = 10L;

    public PageRequest(Long page, Long limit) {
        this.limit = limit;
        this.page = page;
    }

    public Long getPage() {
        return page;
    }

    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return (page - 1l) * limit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((limit == null) ? 0 : limit.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PageRequest other = (PageRequest) obj;
        if (limit == null) {
            if (other.limit != null) return false;
        } else if (!limit.equals(other.limit)) return false;
        if (page == null) {
            if (other.page != null) return false;
        } else if (!page.equals(other.page)) return false;
        return true;
    }
}
