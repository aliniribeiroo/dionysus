package com.aliniribeiro.dionysus.model.assets;

import com.aliniribeiro.dionysus.model.common.PageRequest;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.common.RepositoryBaseImpl;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepositoryCustom;
import com.aliniribeiro.dionysus.model.debt.QDebtEntity;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import com.aliniribeiro.dionysus.model.income.QIncomeEntity;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

public class AssetRepositoryImpl extends RepositoryBaseImpl implements AssetRepositoryCustom {

    @Override
    public AssetEntity getByOriginalId(String originalId) {
        QAssetEntity asset = QAssetEntity.assetEntity;
        JPAQuery<AssetEntity> query = select(asset).from(asset);
        query.where(asset.originalId.eq(originalId));
        return query.fetchFirst();
    }

    @Override
    public PageResult getAssets(String cpf, Long page, Long size) {
        QAssetEntity assets = QAssetEntity.assetEntity;
        JPAQuery<AssetEntity> query = select(assets).from(assets);
        query.where(assets.personCPF.eq(cpf));

        PageRequest pageRequest = new PageRequest(page, size);
        return getPagedQuery(query, pageRequest);
    }

    @Override
    public List<AssetEntity> getAllAssets(String cpf) {
        QAssetEntity assets = QAssetEntity.assetEntity;
        JPAQuery<AssetEntity> query = select(assets).from(assets);
        query.where(assets.personCPF.eq(cpf));
        return query.fetch();
    }
}
