package com.aliniribeiro.mock.mockServices.controller.incomeandassets;

import com.aliniribeiro.mock.mockServices.controller.common.MockUtils;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.contracts.IncomeAndAssetsOutput;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.contracts.IncomeDTO;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.enums.AssetsType;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.enums.IncomeFrequency;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.enums.IncomeType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class IncomeAndAssetsService {

    protected IncomeAndAssetsOutput getIncomeAndAssets(String cpf) {
        IncomeAndAssetsOutput output = new IncomeAndAssetsOutput();
        output.id = MockUtils.getRandomId();
        output.cpf = cpf;
        output.address = "Imaginary road, number 444, cep 8887858 - Unicorn City";
        output.birthYear = MockUtils.getRandomBirthYear();
        output.lastUpdate = MockUtils.getRandomDate();
        output.incomes = getIncome();
        output.assets = getAssets();
        return  output;
    }

    private List<IncomeDTO> getIncome() {
        List<IncomeDTO> incomeList = new ArrayList<>();
        for (int i = 0; i < 7; i ++){
            IncomeDTO income = new IncomeDTO();
            income.id = MockUtils.getRandomId();
            income.type = IncomeType.REGULAR_WORK;
            income.frequency = IncomeFrequency.MOUNTHLY;
            income.locale = Locale.getDefault();
            income.value = new Float(8325.21);
            incomeList.add(income);
        }
        return incomeList;
    }

    private List<AssetsType> getAssets() {
        List<AssetsType> assets = new ArrayList<>();
        assets.add(AssetsType.CAR_MOVABLE_PROPERTY);
        assets.add(AssetsType.CAR_MOVABLE_PROPERTY);
        assets.add(AssetsType.MOTORCYCLE_MOVABLE_PROPERTY);
        assets.add(AssetsType.HOME_IMMOVABLE_PROPERTY);
        return assets;
    }
}
