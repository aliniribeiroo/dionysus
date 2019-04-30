package com.aliniribeiro.dionysus.controller.datacontrol;

import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DataControlService {

    @Autowired
    DataControlRepository dataControlRepository;

    public Optional<DataControlEntity> getDataControl() {
        List<DataControlEntity> dataControl = dataControlRepository.findAll();
        if (!dataControl.isEmpty()) {
            return Optional.of(dataControl.get(0));
        }
        return Optional.empty();
    }

    @Transactional
    public void updateData() {
        List<DataControlEntity> dataControl = dataControlRepository.findAll();
        if (!dataControl.isEmpty()) {
            DataControlEntity data = dataControl.get(0);
            data.setFirstLoaded(true);
            data.setLastUpdate(LocalDate.now());
            dataControlRepository.save(data);
        }
    }
}
