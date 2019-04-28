package com.aliniribeiro.mock.mockServices.controller.debts;

import com.aliniribeiro.mock.mockServices.controller.common.MockUtils;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtsInput;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DebtsService {

    @Autowired
    private DebtsMapper debtsMapper;

    protected List<DebtsOutput> getAlLDebts(DebtsInput input) {
        if (input.initialDate != null && input.finalDate != null){
            return  getDebtsByDate(input.initialDate, input.finalDate);
        }
        List<DebtsOutput> debts = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            DebtsOutput data = new DebtsOutput();
            data.id = MockUtils.getRandomId();
            data.cpf = MockUtils.getRandomCPF();
            data.address = "Imaginary road, number 444, cep 8887858 - Unicorn City";
            data.fullname = "Pink Unicorn with id:" + data.cpf;
            data.debts = debtsMapper.getNormalDebts();
            debts.add(data);
        }
        return debts;
    }

    protected DebtsOutput getCPFDebts(String cpf) {
        DebtsOutput data = new DebtsOutput();
        data.id = MockUtils.getRandomId();
        data.cpf = cpf;
        data.address = "Imaginary road, number 444, cep 8887858 - Unicorn City";
        data.fullname = "Pink Unicorn with id:" + cpf;
        data.debts = cpf.equalsIgnoreCase("666.666.666") ? debtsMapper.getExtraDataDebts() : debtsMapper.getNormalDebts();
        return data;
    }

    protected List<DebtsOutput> getDebtsByDate(LocalDate startDate, LocalDate endDate) {
        List<DebtsOutput> debts = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            DebtsOutput data = new DebtsOutput();
            data.id = MockUtils.getRandomId();
            data.cpf = MockUtils.getRandomCPF();
            data.address = "Imaginary road, number 444, cep 8887858 - Unicorn City";
            data.fullname = "Pink Unicorn with id:" + data.cpf;
            data.debts = debtsMapper.getDebtsByDate(startDate, endDate);
            debts.add(data);
        }
        return debts;
    }
}
