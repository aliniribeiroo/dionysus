package com.aliniribeiro.dionysus.model.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {

    PersonEntity findBycpf(String cpf);
}
