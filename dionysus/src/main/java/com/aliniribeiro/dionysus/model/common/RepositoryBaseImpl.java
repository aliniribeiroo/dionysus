package com.aliniribeiro.dionysus.model.common;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;


public class RepositoryBaseImpl {

    @PersistenceContext
    private EntityManager ctx;

    public <U> JPAQuery<U> select(Expression<U> expr) {
        return new JPAQuery<>(ctx).select(expr);
    }

    public JPAQuery<Tuple> select(Expression<?>... exprs) {
        return new JPAQuery<>(ctx).select(exprs);
    }

    protected <T> PageResult<T> getPagedQuery(JPAQuery<T> query, PageRequest pageRequest) {
        Collection<T> rows = query.limit(pageRequest.getLimit()).offset(pageRequest.getOffset()).fetch();
        return new PageResult<>(rows, query.fetchCount());
    }

}
