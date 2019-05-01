package com.aliniribeiro.dionysus.model.income;

import com.aliniribeiro.dionysus.model.common.PageRequest;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

public class IncomeRepositoryImpl extends RepositoryBaseImpl implements IncomeRepositoryCustom {

    @Override
    public IncomeEntity getByOriginalId(String originalId) {
        QIncomeEntity income = QIncomeEntity.incomeEntity;
        JPAQuery<IncomeEntity> query = select(income).from(income);
        query.where(income.originalId.eq(originalId));
        return query.fetchFirst();
    }

    @Override
    public PageResult getIncomes(String cpf, Long page, Long size) {
        QIncomeEntity income = QIncomeEntity.incomeEntity;
        JPAQuery<IncomeEntity> query = select(income).from(income);
        query.where(income.personCPF.eq(cpf));

        PageRequest pageRequest = new PageRequest(page, size);
        return getPagedQuery(query, pageRequest);
    }

    @Override
    public List<IncomeEntity> getAllIncomes(String cpf) {
        QIncomeEntity income = QIncomeEntity.incomeEntity;
        JPAQuery<IncomeEntity> query = select(income).from(income);
        query.where(income.personCPF.eq(cpf));
        return query.fetch();
    }
}
