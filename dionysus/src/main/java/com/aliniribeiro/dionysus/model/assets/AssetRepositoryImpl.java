package com.aliniribeiro.dionysus.model.assets;

import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepositoryCustom;
import com.aliniribeiro.dionysus.model.debt.QDebtEntity;
import com.querydsl.jpa.impl.JPAQuery;

public class AssetRepositoryImpl extends RepositoryBaseImpl implements AssetRepositoryCustom {

    @Override
    public AssetEntity getByOriginalId(String originalId) {
        QAssetEntity asset = QAssetEntity.assetEntity;
        JPAQuery<AssetEntity> query = select(asset).from(asset);
        query.where(asset.originalId.eq(originalId));
        return query.fetchFirst();
    }
}
