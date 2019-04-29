package com.aliniribeiro.dionysus.controller.datacontrol;

import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DataControlService {

    @Autowired
    DataControlRepository dataControlRepository;

    public Optional<DataControlEntity> getDataControl() {
        DataControlEntity dataControl = dataControlRepository.findAll().get(0);
        if (dataControl != null) {
            return Optional.of(dataControl);
        }
        return Optional.empty();
    }

    @Transactional
    public void updateData() {
        DataControlEntity dataControl = dataControlRepository.findAll().get(0);
        if (dataControl != null) {
            dataControl.setFirstLoaded(true);
            dataControl.setLastUpdate(LocalDate.now());
            dataControlRepository.save(dataControl);
        }
    }
}
