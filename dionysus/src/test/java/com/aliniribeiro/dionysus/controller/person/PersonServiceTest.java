package com.aliniribeiro.dionysus.controller.person;

import com.aliniribeiro.dionysus.controller.assets.AssetsService;
import com.aliniribeiro.dionysus.controller.assets.contracts.PersonAssetsOutput;
import com.aliniribeiro.dionysus.controller.datacontrol.DataControlService;
import com.aliniribeiro.dionysus.controller.debts.DebtService;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.controller.income.IncomeService;
import com.aliniribeiro.dionysus.controller.income.contracts.PersonIncomeOutput;
import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.controller.person.contracts.GetCPFPointsOutput;
import com.aliniribeiro.dionysus.controller.person.mapper.PersonMapper;
import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.assets.AssetRepository;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import com.aliniribeiro.dionysus.model.income.IncomeRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class PersonServiceTest {


    @Mock
    PersonRepository personRepository;

    @Mock
    DebtService debtService;

    @Mock
    IncomeService incomeService;

    @Mock
    AssetsService assetsService;

    @Mock
    MockServiceintegration mockServiceintegration;

    @Mock
    DataControlService dataControlService;

    @Mock
    IncomeRepository incomeRepository;

    @Mock
    AssetRepository assetRepository;

    @Mock
    DebtRepository debtRepository;

    @Mock
    PersonMapper personMapper;

    @InjectMocks
    private PersonService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = Mockito.spy(service);
    }

    @Test
    public void loadServiceAPersonData_shoulfCall_ParsePersonInformation() {
        DataControlEntity dataControl = new DataControlEntity();
        Mockito.when(dataControlService.getDataControl()).thenReturn(Optional.of(dataControl));
        String jsonStr = "JsonString";
        Mockito.when(mockServiceintegration.loadServiceAData(Optional.of(dataControl))).thenReturn(Optional.of(jsonStr));
        service.loadServiceAPersonData();
        Mockito.verify(service).parsePersonInformation(jsonStr);
    }

    @Test
    public void loadServiceBPersonData_shoulfCall_ParsePersonInformation() {
        DataControlEntity dataControl = new DataControlEntity();
        Mockito.when(dataControlService.getDataControl()).thenReturn(Optional.of(dataControl));
        String jsonStr = "JsonString";
        Mockito.when(mockServiceintegration.loadServiceBData(Optional.of(dataControl))).thenReturn(Optional.of(jsonStr));
        service.loadServicBPersonData();
        Mockito.verify(service).parsePersonIncomeAndAssets(jsonStr);
    }


    @Test
    public void getPersonDebts_shouldFindPerson() {
        String cpf = "12345";
        Long page = 1L;
        Long size = 1L;

        PersonEntity person = new PersonEntity();
        person.setName("Pink unicorn");
        person.setAddress("Pink unicorn Store");
        person.setCpf(cpf);

        Mockito.when(personRepository.getByCPF(cpf)).thenReturn(Optional.of(person));

        DebtEntity debtEntity = new DebtEntity();
        debtEntity.setPersonId(person.getCpf());
        Collection<DebtEntity> rows = Collections.singletonList(debtEntity);
        Long pageCount = 1L;

        PageResult<DebtEntity> debtsResult = new PageResult<DebtEntity>(rows, pageCount);
        Mockito.when(debtRepository.getDebts(cpf, page, size)).thenReturn(debtsResult);

        GetDebtsOutput mapperReturn = personMapper.toGetDebtsOutput(debtsResult, person.getName(), person.getCpf());
        Mockito.when(personMapper.toGetDebtsOutput(debtsResult, person.getName(), person.getCpf())).thenReturn(mapperReturn);

        GetDebtsOutput output = service.getPersonDebts(cpf, page, size);

        Assertions.assertThat(output).isEqualTo(mapperReturn);

    }
    @Test
    public void getCPFIncomeList_shouldFindPerson() {
        String cpf = "12345";
        Long page = 1L;
        Long size = 1L;

        PersonEntity person = new PersonEntity();
        person.setName("Pink unicorn");
        person.setAddress("Pink unicorn Store");
        person.setCpf(cpf);

        Mockito.when(personRepository.getByCPF(cpf)).thenReturn(Optional.of(person));

        IncomeEntity incomeEntity = new IncomeEntity();
        incomeEntity.setPersonId(person.getCpf());
        Collection<IncomeEntity> rows = Collections.singletonList(incomeEntity);
        Long pageCount = 1L;

        PageResult<IncomeEntity> incomeResults = new PageResult<IncomeEntity>(rows, pageCount);
        Mockito.when(incomeRepository.getIncomes(cpf, page, size)).thenReturn(incomeResults);

        PersonIncomeOutput mapperReturn = personMapper.toPersonIncomeOutput(incomeResults, person.getName());
//        Mockito.when(personMapper.toPersonIncomeOutput(incomeResults, person.getName())).thenReturn(mapperReturn);

        PersonIncomeOutput output = service.getCPFIncomeList(cpf, page, size);
        Assertions.assertThat(output).isEqualTo(mapperReturn);

    }

    @Test
    public void getCPFAssetsListt_shouldFindPerson() {
        String cpf = "12345";
        Long page = 1L;
        Long size = 1L;

        PersonEntity person = new PersonEntity();
        person.setName("Pink unicorn");
        person.setAddress("Pink unicorn Store");
        person.setCpf(cpf);

        Mockito.when(personRepository.getByCPF(cpf)).thenReturn(Optional.of(person));

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setPersonId(person.getCpf());
        Collection<AssetEntity> rows = Collections.singletonList(assetEntity);
        Long pageCount = 1L;

        PageResult<AssetEntity> assetsResult = new PageResult<AssetEntity>(rows, pageCount);
        Mockito.when(assetRepository.getAssets(cpf, page, size)).thenReturn(assetsResult);

        PersonAssetsOutput mapperReturn = personMapper.toPersonAssetsOutput(assetsResult, person.getName());
        PersonAssetsOutput output = service.getCPFAssetsList(cpf, page, size);
        Assertions.assertThat(output).isEqualTo(mapperReturn);

    }

    @Test
    public void getCPFPoints_shouldReturn_1000_points(){
        String cpf = "12345";
        PersonEntity person = new PersonEntity();
        person.setName("Pink unicorn");
        person.setAddress("Pink unicorn Store");
        person.setCpf(cpf);
        Integer personYear = LocalDate.now().getYear() - 29;
        person.setBirthYear(personYear);

        Mockito.when(personRepository.getByCPF(cpf)).thenReturn(Optional.of(person));

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setPersonId(person.getCpf());

        AssetEntity assetEntity2 = new AssetEntity();
        assetEntity2.setPersonId(person.getCpf());
        List<AssetEntity> assetEntityList = Arrays.asList(assetEntity, assetEntity2);
        Mockito.when(assetsService.getAllAssets(cpf)).thenReturn(assetEntityList);

        IncomeEntity incomeEntity = new IncomeEntity();
        incomeEntity.setPersonId(person.getCpf());

        IncomeEntity incomeEntity2 = new IncomeEntity();
        incomeEntity2.setPersonId(person.getCpf());

        List<IncomeEntity> incomeEntities = Arrays.asList(incomeEntity, incomeEntity2);
        Mockito.when(incomeService.getAllIncomes(cpf)).thenReturn(incomeEntities);

        GetCPFPointsOutput output = service.getCPFPoints(cpf);
        Assertions.assertThat(output.age).isEqualTo(29);
        Assertions.assertThat(output.average).isEqualTo(500);
        Assertions.assertThat(output.points).isEqualTo(1000);
    }

    @Test
    public void getCPFPoints_shouldReturn_285_points(){
        String cpf = "12345";
        PersonEntity person = new PersonEntity();
        person.setName("Pink unicorn");
        person.setAddress("Pink unicorn Store");
        person.setCpf(cpf);
        Integer personYear = LocalDate.now().getYear() - 16;
        person.setBirthYear(personYear);

        Mockito.when(personRepository.getByCPF(cpf)).thenReturn(Optional.of(person));

        List<AssetEntity> assetEntityList = Collections.EMPTY_LIST;
        Mockito.when(assetsService.getAllAssets(cpf)).thenReturn(assetEntityList);

        List<IncomeEntity> incomeEntities = Collections.EMPTY_LIST;
        Mockito.when(incomeService.getAllIncomes(cpf)).thenReturn(incomeEntities);

        GetCPFPointsOutput output = service.getCPFPoints(cpf);
        Assertions.assertThat(output.age).isEqualTo(16);
        Assertions.assertThat(output.average).isEqualTo(500);
        Assertions.assertThat(output.points).isEqualTo(285);
    }

    @Test
    public void getCPFPoints_shouldReturn_900_points(){
        String cpf = "12345";
        PersonEntity person = new PersonEntity();
        person.setName("Pink unicorn");
        person.setAddress("Pink unicorn Store");
        person.setCpf(cpf);
        Integer personYear = LocalDate.now().getYear() - 29;
        person.setBirthYear(personYear);

        Mockito.when(personRepository.getByCPF(cpf)).thenReturn(Optional.of(person));

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setPersonId(person.getCpf());

        List<AssetEntity> assetEntityList = Arrays.asList(assetEntity);
        Mockito.when(assetsService.getAllAssets(cpf)).thenReturn(assetEntityList);

        IncomeEntity incomeEntity = new IncomeEntity();
        incomeEntity.setPersonId(person.getCpf());

        List<IncomeEntity> incomeEntities = Arrays.asList(incomeEntity);
        Mockito.when(incomeService.getAllIncomes(cpf)).thenReturn(incomeEntities);

        GetCPFPointsOutput output = service.getCPFPoints(cpf);
        Assertions.assertThat(output.age).isEqualTo(29);
        Assertions.assertThat(output.average).isEqualTo(500);
        Assertions.assertThat(output.points).isEqualTo(900);
    }

}
