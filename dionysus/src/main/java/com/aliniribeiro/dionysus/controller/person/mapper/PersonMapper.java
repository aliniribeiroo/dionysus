package com.aliniribeiro.dionysus.controller.person.mapper;

import com.aliniribeiro.dionysus.controller.assets.mapper.AssetsMapper;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.controller.debts.mapper.DebtsMapper;
import com.aliniribeiro.dionysus.controller.income.contracts.IncomeDTO;
import com.aliniribeiro.dionysus.controller.income.contracts.PersonIncomeOutput;
import com.aliniribeiro.dionysus.controller.assets.contracts.AssetsDTO;
import com.aliniribeiro.dionysus.controller.assets.contracts.PersonAssetsOutput;
import com.aliniribeiro.dionysus.controller.income.mapper.IncomeMapper;
import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PersonMapper {

    @Autowired
    DebtsMapper debtsMapper;

    @Autowired
    AssetsMapper assetsMapper;

    @Autowired
    IncomeMapper incomeMapper;


    /**
     * Mapeia a os dados paginados da entidade AssetEntity para um PersonAssetsOutput.
     * @param assetResult  entidade que será mapeada com os dados paginados, uma pessoa pode possuir muitos bens.
     * @param personName nome da pessoa que possui os bens.
     * @returnObjeto PersonAssetsOutput com os dados da entidade.
     */
    public PersonAssetsOutput toPersonAssetsOutput(PageResult<AssetEntity> assetResult, String personName) {
        PersonAssetsOutput output = new PersonAssetsOutput();
        output.personName =personName;
        output.registerFound = assetResult.getTotalCount();
        output.assets = new ArrayList<>();
        assetResult.getRows().stream().forEach(c -> output.assets.add(assetsMapper.toAssetsDTO(c)));

        return output;
    }

    /**
     * Mapeia a entidade IncomeEntity para um PersonAssetsOutput.
     * @param incomeResult  entidade que será mapeada com os dados paginados, uma pessoa pode possuir muitas rendas.
     * @param personName nome da pessoa que possui os bens.
     * @returnObjeto PersonAssetsOutput com os dados da entidade.
     */
    public PersonIncomeOutput toPersonIncomeOutput(PageResult<IncomeEntity> incomeResult, String personName) {
        PersonIncomeOutput output = new PersonIncomeOutput();
        output.personName = personName;
        output.registerFound = incomeResult.getTotalCount();
        output.incomes = new ArrayList<>();
        incomeResult.getRows().stream().forEach(c -> output.incomes.add(incomeMapper.toIncomeDTO(c)));
        return output;
    }

    /**
     * Mapeia a os dados paginados da entidade DebtEntity para um GetDebtsOutput.
     * @param debts  entidade que será mapeada com os dados paginados, uma pessoa pode possuir muitas rendas.
     * @param personName nome da pessoa que possui os bens.
     * @param address endereço da pessoa que possui os bens.
     * @returnObjeto GetDebtsOutput com os dados da entidade.
     */
    public GetDebtsOutput toGetDebtsOutput(PageResult<DebtEntity> debts, String personName,  String address) {
        GetDebtsOutput output = new GetDebtsOutput();
        output.personName = personName;
        output.address = address;
        output.registerFound = debts.getTotalCount();
        output.debts = new ArrayList<>();
        debts.getRows().stream().forEach(c -> output.debts.add(debtsMapper.toDebtsDTO(c)));

        return output;
    }
}
