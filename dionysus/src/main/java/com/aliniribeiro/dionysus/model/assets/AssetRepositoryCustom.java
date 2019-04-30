package com.aliniribeiro.dionysus.model.assets;

public interface AssetRepositoryCustom {

    /**
     * Método que busca uma bem da base de dados, de acordo com o ID original.
     *
     * @param originalId ID  do serviço de origem.
     * @return Bem que a pessoa possui.
     */
    AssetEntity getByOriginalId(String originalId);

}
