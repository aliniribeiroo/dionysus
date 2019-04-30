package com.aliniribeiro.dionysus.model.debt;

import com.aliniribeiro.dionysus.model.common.PageResult;

import java.util.List;

public interface DebtRepositoryCustom {

    /**
     * Método que busca um débito da base de dados, de acordo com o ID original.
     *
     * @param originalId ID  do serviço de origem.
     * @return Débito que a pessoa possui.
     */
    DebtEntity getByOriginalId(String originalId);


    /**
     * Método que retorna todas as dívidas do CPF.
     *
     * @param cpf  CPF que a dívida será solicitada.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return GetDebtsOutput com as informações das dívidas encontradas.
     */
    PageResult getDebts(String cpf, Long page, Long size);

}
