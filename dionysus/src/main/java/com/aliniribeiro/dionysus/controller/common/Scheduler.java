package com.aliniribeiro.dionysus.controller.common;

import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.controller.person.PersonService;
import com.aliniribeiro.dionysus.util.Spring;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class Scheduler implements Job {

    @Autowired
    private PersonService personService;

    private final static Logger LOGGER = Logger.getLogger(Scheduler.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LOGGER.info("Scheduler was called!");
            Spring.bean(PersonService.class).loadServiceAPersonData();
            Spring.bean(PersonService.class).loadServicBPersonData();

        } catch (Exception e) {
            LOGGER.severe("Error calling the Scheduler.");
        }
    }

}
