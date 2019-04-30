package com.aliniribeiro.dionysus.model.assets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<AssetEntity, UUID>, AssetRepositoryCustom {

}
