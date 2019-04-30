package com.aliniribeiro.dionysus.model.income;

public interface IncomeRepositoryCustom {

    /**
     * Método que busca uma renda da base de dados, de acordo com o ID original.
     *
     * @param originalId ID  do serviço de origem.
     * @return Renda que a pessoa possui.
     */
    IncomeEntity getByOriginalId(String originalId);

}
