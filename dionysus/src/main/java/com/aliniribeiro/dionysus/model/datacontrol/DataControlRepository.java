package com.aliniribeiro.dionysus.model.datacontrol;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataControlRepository extends JpaRepository<DataControlEntity, UUID> {
}
