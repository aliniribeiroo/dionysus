package com.aliniribeiro.dionysus.model.person;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "last_address_update", nullable = false)
    private LocalDate lastAdrdessUpdate;

    @Column(name = "last_income_update")
    private LocalDate lastIncomeUpdate;


    @Column(name = "last_assets_update")
    private LocalDate lastAssetsUpdate;

    @Column(name = "last_debts_update")
    private LocalDate lastDebtsUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getLastAdrdessUpdate() {
        return lastAdrdessUpdate;
    }

    public void setLastAdrdessUpdate(LocalDate lastAdrdessUpdate) {
        this.lastAdrdessUpdate = lastAdrdessUpdate;
    }

    public LocalDate getLastIncomeUpdate() {
        return lastIncomeUpdate;
    }

    public void setLastIncomeUpdate(LocalDate lastIncomeUpdate) {
        this.lastIncomeUpdate = lastIncomeUpdate;
    }

    public LocalDate getLastAssetsUpdate() {
        return lastAssetsUpdate;
    }

    public void setLastAssetsUpdate(LocalDate lastAssetsUpdate) {
        this.lastAssetsUpdate = lastAssetsUpdate;
    }

    public LocalDate getLastDebtsUpdate() {
        return lastDebtsUpdate;
    }

    public void setLastDebtsUpdate(LocalDate lastDebtsUpdate) {
        this.lastDebtsUpdate = lastDebtsUpdate;
    }
}
