package com.aliniribeiro.dionysus.model.debt;

import com.aliniribeiro.dionysus.model.common.PageRequest;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

public class DebtRepositoryImpl extends RepositoryBaseImpl implements DebtRepositoryCustom {

    @Override
    public DebtEntity getByOriginalId(String originalId) {
        QDebtEntity debt = QDebtEntity.debtEntity;
        JPAQuery<DebtEntity> query = select(debt).from(debt);
        query.where(debt.originalId.eq(originalId));
        return query.fetchFirst();
    }

    @Override
    public PageResult getDebts(String cpf, Long page, Long size) {
        QDebtEntity debt = QDebtEntity.debtEntity;
        JPAQuery<DebtEntity> query = select(debt).from(debt);
        query.where(debt.personCPF.eq(cpf));

        PageRequest pageRequest = new PageRequest(page, size);
        return getPagedQuery(query, pageRequest);
    }
}
