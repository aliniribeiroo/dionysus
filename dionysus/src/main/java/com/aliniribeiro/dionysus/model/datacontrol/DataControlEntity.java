package com.aliniribeiro.dionysus.model.datacontrol;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "data_control")
public class DataControlEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false)
    private UUID id;


    @Column(name = "LAST_UPDATE")
    private LocalDate lastUpdate;

    @Column(name = "FIRST_LOADED")
    private Boolean firstLoaded;

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Boolean getFirstLoaded() {
        return firstLoaded;
    }

    public void setFirstLoaded(Boolean firstLoaded) {
        this.firstLoaded = firstLoaded;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
