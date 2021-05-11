package com.example.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @Autowired
  private SimpleJob simpleJob;

  @GetMapping("/test")
  public void testScheduler(@RequestParam("time") String time){
    simpleJob.cron = time;
    try {
      simpleJob.updateJobPayAllowance(simpleJob.createJobPayAllowance(),time);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
