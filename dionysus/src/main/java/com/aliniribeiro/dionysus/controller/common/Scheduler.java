package com.aliniribeiro.dionysus.controller.common;

import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.util.Spring;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class Scheduler implements Job {

    @Autowired
    private MockServiceintegration mockIntegration;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Spring.bean(MockServiceintegration.class).loadData();
        } catch (Exception e) {
          //  System.out.println("ALERT: >>>" + ExceptionType.BI_ERROR.getDescription());
        }
    }

}
