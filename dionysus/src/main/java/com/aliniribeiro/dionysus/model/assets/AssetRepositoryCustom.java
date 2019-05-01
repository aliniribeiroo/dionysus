package com.aliniribeiro.dionysus.model.assets;

import com.aliniribeiro.dionysus.model.common.PageResult;

public interface AssetRepositoryCustom {

    /**
     * Método que busca uma bem da base de dados, de acordo com o ID original.
     *
     * @param originalId ID  do serviço de origem.
     * @return Bem que a pessoa possui.
     */
    AssetEntity getByOriginalId(String originalId);

    /**
     * Método que retorna todos os bens do CPF.
     *
     * @param cpf  CPF que a dívida será solicitada.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return PageResult com as informações das dívidas encontradas.
     */
    PageResult getAssets(String cpf, Long page, Long size);
}
