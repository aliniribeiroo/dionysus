package com.aliniribeiro.dionysus.model.debt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DebtRepository extends JpaRepository<DebtEntity, UUID> {
}
