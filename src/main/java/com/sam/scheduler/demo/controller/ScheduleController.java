package com.sam.scheduler.demo.controller;

import com.sam.scheduler.demo.job.TestJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping(value = "/schedule/{detail}/{desc}")
    public String scheduleJob(@PathVariable(value = "detail") String detail, @PathVariable(value = "desc") String desc) throws SchedulerException {
        JobDetail job = newJob(detail, desc);
        return scheduler.scheduleJob(job, trigger(job)).toString();
    }

    private JobDetail newJob(String identity, String description) {
        return JobBuilder.newJob().ofType(TestJob.class).storeDurably()
                .withIdentity(JobKey.jobKey(identity))
                .withDescription(description)
                .build();
    }

    private SimpleTrigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(3))
                .build();
    }
}
