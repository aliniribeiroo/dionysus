package com.aliniribeiro.dionysus.model.searchhistory;

import java.util.List;

public interface SearchHistoryRepositoryCustom {

    /**
     * Busca uma pessoa pelo seu CPF
     *
     * @param cpf CPF da pessoa.
     * @return Pessoa que possui o CPF informado.
     */
    List<SearchHistoryEntity> getByCPF(String cpf);


}
