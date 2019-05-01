package com.aliniribeiro.dionysus.model.income;

import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.assets.AssetRepositoryCustom;
import com.aliniribeiro.dionysus.model.assets.QAssetEntity;
import com.aliniribeiro.dionysus.model.common.PageRequest;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.QDebtEntity;
import com.querydsl.jpa.impl.JPAQuery;

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
}
