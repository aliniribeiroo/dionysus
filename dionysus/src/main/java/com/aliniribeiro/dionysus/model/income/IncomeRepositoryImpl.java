package com.aliniribeiro.dionysus.model.income;

import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.assets.AssetRepositoryCustom;
import com.aliniribeiro.dionysus.model.assets.QAssetEntity;
import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

public class IncomeRepositoryImpl extends RepositoryBaseImpl implements IncomeRepositoryCustom {

    @Override
    public IncomeEntity getByOriginalId(String originalId) {
        QIncomeEntity income = QIncomeEntity.incomeEntity;
        JPAQuery<IncomeEntity> query = select(income).from(income);
        query.where(income.originalId.eq(originalId));
        return query.fetchFirst();
    }
}
