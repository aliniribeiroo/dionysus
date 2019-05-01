package com.aliniribeiro.dionysus.controller.assets.mapper;

import com.aliniribeiro.dionysus.controller.assets.contracts.AssetsDTO;
import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetsMapper {

    /**
     * Mapeia a entidade assetEntity para um AssetsDTO
     * @param assetEntity entidade que será mapeada.
     * @return Objeto AssetsDTO com os dados da entidade.
     */
    public AssetsDTO toAssetsDTO(AssetEntity assetEntity) {
        AssetsDTO assetsDTO = new AssetsDTO();
        assetsDTO.type = assetEntity.getType();
        assetsDTO.value = assetEntity.getAssetsValue();
        assetsDTO.locale = assetEntity.getLocale();

        return assetsDTO;
    }

}
