package com.is442project.cpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledJobs {


    //sec min hr dayOfMth(1-31) mth(1-12) dayOfWeek(0-6) year(optional)
    //0 0 1 * * ? --- everyday 1am
    //0 0 0/60 ? * * -- every hour
    //0 * * ? * * --- every min
    //0/5 0 0 ? * * *--- every 5sec
    @Scheduled(cron = "0 0 1 * * ?")
    public void reminderEmails(){

        System.out.println("Email Sent!");

    }
}
