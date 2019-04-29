package com.aliniribeiro.dionysus;

import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DionysusApplication {

	public static void main(String[] args) {
		SpringApplication.run(DionysusApplication.class, args);
		//callQuartzScheduller();
	}

	@Bean
	public CommandLineRunner commandLineRunner(MockServiceintegration BiIn) {
		return args -> {
			callQuartzScheduller();
		};
	}

	/**
	 * Agenda um novo JOB á cada 24 horas para buscar as informações dos Serviços de terceiros.
	 */
	private static void callQuartzScheduller() {
		System.out.println("Iniciando Scheduller ");
		SchedulerFactory sf = new StdSchedulerFactory();
		try {

			JobDetail job = JobBuilder.newJob(com.aliniribeiro.dionysus.controller.common.Scheduler.class)
					.withIdentity("job1", "group1")
					.build();

			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("dummyTriggerName", "group1")
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(86400).repeatForever())
					.build();

			Scheduler sched = sf.getScheduler();
			sched.start();
			sched.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
