package com.sam.scheduler.demo.controller;

import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.scheduler.demo.job.TestJob;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final Scheduler scheduler;

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
