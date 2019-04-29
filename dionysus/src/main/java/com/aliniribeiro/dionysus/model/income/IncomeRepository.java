package com.aliniribeiro.dionysus.model.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomeRepository extends JpaRepository<IncomeEntity, UUID> {

    IncomeEntity findByOriginalId(String originalId);
}
