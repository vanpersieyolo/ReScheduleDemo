package com.example.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@Component
public class SimpleJob implements Job {
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println(cron);
    System.out.println(new Date());
  }

  public String cron = "0/1 * * * * ?";

  @Bean
  public CronTrigger createJobPayAllowance(){
    try {
      SimpleJob simpleJob = new SimpleJob();
      String cron  = simpleJob.cron;
      SchedulerFactory sf = new StdSchedulerFactory();
      Scheduler scheduler = sf.getScheduler();
      JobDetail job = JobBuilder.newJob(SimpleJob.class)
        .withIdentity("Job", "name").build();
//      Date startTime = DateBuilder.nextGivenSecondDate(null,1);
      CronTrigger crontrigger = TriggerBuilder
        .newTrigger()
        .withIdentity("Pay Allowance ", "Job")
//        .startAt(startTime)
         .startNow()
        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
        .build();
      scheduler.start();
      scheduler.scheduleJob(job, crontrigger);
      // scheduler.shutdown();
      return crontrigger;
    } catch (SchedulerException se) {
      se.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  public void updateJobPayAllowance(CronTrigger oldTrigger, String time){
    try {
      SchedulerFactory sf = new StdSchedulerFactory();
      Scheduler scheduler = sf.getScheduler();

      CronTrigger crontrigger = TriggerBuilder
        .newTrigger()
        .withIdentity("Pay Allowance ", "Job")
//        .startAt(startTime)
        .startNow()
        .withSchedule(CronScheduleBuilder.cronSchedule(time))
        .build();
      scheduler.rescheduleJob(oldTrigger.getKey(),crontrigger);

    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
