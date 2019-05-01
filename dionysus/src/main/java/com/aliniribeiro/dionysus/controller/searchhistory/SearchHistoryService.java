package com.aliniribeiro.dionysus.controller.searchhistory;

import com.aliniribeiro.dionysus.controller.searchhistory.contracts.SearchHistoryOutput;
import com.aliniribeiro.dionysus.model.searchhistory.SearchHistoryRepository;
import com.aliniribeiro.dionysus.model.searchhistory.SearchHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchHistoryService {

    @Autowired
    SearchHistoryRepository repository;

    public void saveSearchHistory(String cpf){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        System.out.println(userDetails.getUsername());

        SearchHistoryEntity history = new SearchHistoryEntity();
        history.setPersonCPF(cpf);
        history.setSearchDate(LocalDate.now());
        history.setUsername(userDetails.getUsername());
        repository.save(history);
    }

    public List<SearchHistoryOutput> searchHistory(String cpf){
        List<SearchHistoryEntity> history = repository.getByCPF(cpf);
        List<SearchHistoryOutput> output = new ArrayList<>();
        history.forEach(h -> {
            SearchHistoryOutput historyDTO = new SearchHistoryOutput();
            historyDTO.searchDate = h.getSearchDate();
            historyDTO.cpf = h.getPersonCPF();
            historyDTO.username = h.getUsername();
            output.add(historyDTO);
        });
        return output;
    }
}
