package com.aliniribeiro.dionysus.model.person;

import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.Optional;

public class PersonRepositoryImpl extends RepositoryBaseImpl implements PersonRepositoryCustom {

    @Override
    public Optional<PersonEntity> getByCPF(String cpf) {
        QPersonEntity person = QPersonEntity.personEntity;
        JPAQuery<PersonEntity> query = select(person).from(person);
        query.where(person.cpf.eq(cpf));
        return Optional.ofNullable(query.fetchFirst());
    }
}
