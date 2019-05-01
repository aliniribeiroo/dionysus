package com.aliniribeiro.dionysus.model.searchhistory;

import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.QPersonEntity;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;
import java.util.Optional;

public class SearchHistoryRepositoryImpl extends RepositoryBaseImpl implements SearchHistoryRepositoryCustom {


    @Override
    public List<SearchHistoryEntity> getByCPF(String cpf) {
        QSearchHistoryEntity history = QSearchHistoryEntity.searchHistoryEntity;
        JPAQuery<SearchHistoryEntity> query = select(history).from(history);
        query.where(history.personCPF.eq(cpf));
        return query.fetch();
    }
}
