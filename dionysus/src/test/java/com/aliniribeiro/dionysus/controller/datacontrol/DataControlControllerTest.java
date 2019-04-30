package com.aliniribeiro.dionysus.controller.datacontrol;

import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DataControlControllerTest {

    @Mock
    DataControlRepository dataControlRepository;

    @InjectMocks
    DataControlService service;

    @Test
    public void getDataControl_shouldReturnData(){
        DataControlEntity dataControl = new DataControlEntity();
        dataControl.setId(UUID.randomUUID());
        Mockito.when(dataControlRepository.findAll()).thenReturn(Arrays.asList(dataControl));
        Optional<DataControlEntity> output = service.getDataControl();

        Assertions.assertThat(output.isPresent()).isTrue();
        Assertions.assertThat(output.get()).isEqualTo(dataControl);
    }

    @Test
    public void getDataControl_should_not_ReturnData(){
        DataControlEntity dataControl = new DataControlEntity();
        dataControl.setId(UUID.randomUUID());
        Mockito.when(dataControlRepository.findAll()).thenReturn(Arrays.asList());
        Optional<DataControlEntity> output = service.getDataControl();

        Assertions.assertThat(output.isPresent()).isFalse();
    }

    @Test
    public void updateData(){
        DataControlEntity dataControl = new DataControlEntity();
        dataControl.setId(UUID.randomUUID());
        Mockito.when(dataControlRepository.findAll()).thenReturn(Arrays.asList(dataControl));
        service.updateData();
        Mockito.verify(dataControlRepository).findAll();
        Mockito.verify(dataControlRepository).save(Mockito.any());
    }

    @Test
    public void updateData_shouldNotSave(){
        DataControlEntity dataControl = new DataControlEntity();
        dataControl.setId(UUID.randomUUID());
        Mockito.when(dataControlRepository.findAll()).thenReturn(Arrays.asList());
        service.updateData();
        Mockito.verify(dataControlRepository).findAll();
        Mockito.verifyNoMoreInteractions(dataControlRepository);
    }
}
