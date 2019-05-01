package com.aliniribeiro.dionysus.model.income;

import com.aliniribeiro.dionysus.model.common.PageResult;

public interface IncomeRepositoryCustom {

    /**
     * Método que busca uma renda da base de dados, de acordo com o ID original.
     *
     * @param originalId ID  do serviço de origem.
     * @return Renda que a pessoa possui.
     */
    IncomeEntity getByOriginalId(String originalId);

    /**
     * Método que retorna todas as rendas do CPF.
     *
     * @param cpf  CPF que a dívida será solicitada.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return PageResult com as informações das dívidas encontradas.
     */
    PageResult getIncomes(String cpf, Long page, Long size);

}
