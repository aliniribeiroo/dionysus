package com.aliniribeiro.dionysus.model.person;

import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

public class PersonRepositoryImpl extends RepositoryBaseImpl implements PersonRepositoryCustom {

    @Override
    public PersonEntity getByCPF(String cpf) {
        QPersonEntity person = QPersonEntity.personEntity;
        JPAQuery<PersonEntity> query = select(person).from(person);
        query.where(person.cpf.eq(cpf));
        return query.fetchFirst();
    }
}
