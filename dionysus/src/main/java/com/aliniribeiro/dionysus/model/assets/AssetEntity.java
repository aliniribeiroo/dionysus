package com.aliniribeiro.dionysus.model.assets;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "assets")
public class AssetEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "original_id", updatable = false)
    private String originalId;

    @Column(name = "cpf")
    private String personCPF;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "asset_value", nullable = false)
    private Double assetsValue;

    @Column(name = "type", nullable = false)
    private String type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getPersonCPF() {
        return personCPF;
    }

    public void setPersonCPF(String personCPF) {
        this.personCPF = personCPF;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Double getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(Double assetsValue) {
        this.assetsValue = assetsValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPersonId(String personCPF) {
        this.personCPF = personCPF;
    }
}
