package com.aliniribeiro.dionysus.model.person;

public interface PersonRepositoryCustom {

    /**
     * Busca uma pessoa pelo seu CPF
     *
     * @param cpf CPF da pessoa.
     * @return Pessoa que possui o CPF informado.
     */
    PersonEntity getByCPF(String cpf);

}
