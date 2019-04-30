package com.aliniribeiro.dionysus.model.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, String>, PersonRepositoryCustom {

    PersonEntity findBycpf(String cpf);
}
