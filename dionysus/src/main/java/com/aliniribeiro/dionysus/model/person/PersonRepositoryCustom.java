package com.aliniribeiro.dionysus.model.person;

import java.util.Optional;

public interface PersonRepositoryCustom {

    /**
     * Busca uma pessoa pelo seu CPF
     *
     * @param cpf CPF da pessoa.
     * @return Pessoa que possui o CPF informado.
     */
    Optional<PersonEntity> getByCPF(String cpf);

}
