package com.aliniribeiro.dionysus.model.debt;

import com.aliniribeiro.dionysus.model.person.PersonEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "debt")
public class DebtEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "original_id", updatable = false)
    private String originalId;

    @Column(name = "cpf")
    private String personCPF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpf", referencedColumnName = "cpf", insertable = false, updatable = false)
    private PersonEntity person;

    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @Column(name = "original_date")
    private LocalDate originalDate;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "debt_value", nullable = false)
    private Double debtValue;

    @Column(name = "description", nullable = false)
    private String description;


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

    public PersonEntity getPerson() {
        return person;
    }

    public void setPersonId(String personCPF) {
        this.personCPF = personCPF;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDate getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(LocalDate originalDate) {
        this.originalDate = originalDate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDebtValue() {
        return debtValue;
    }

    public void setDebtValue(Double debtValue) {
        this.debtValue = debtValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
