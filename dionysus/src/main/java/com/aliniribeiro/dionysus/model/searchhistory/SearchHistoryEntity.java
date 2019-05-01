package com.aliniribeiro.dionysus.model.searchhistory;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "search_history")
public class SearchHistoryEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "search_date")
    private LocalDate searchDate;

    @Column(name = "username", updatable = false)
    private String username;

    @Column(name = "cpf")
    private String personCPF;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonCPF() {
        return personCPF;
    }

    public void setPersonCPF(String personCPF) {
        this.personCPF = personCPF;
    }
}
