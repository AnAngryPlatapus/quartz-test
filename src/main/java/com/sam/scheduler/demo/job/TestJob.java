package com.sam.scheduler.demo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("Whaddup dawg");
        System.out.println(jobExecutionContext.getJobDetail().getDescription());
    }
}
