package com.is442project.cpa.config;

import com.is442project.cpa.booking.BookingService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledJobs {

    private final BookingService bookingService;

    public ScheduledJobs(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //sec min hr dayOfMth(1-31) mth(1-12) dayOfWeek(0-6) year(optional)
    //0 0 1 * * ? --- everyday 1am
    //0 0 0/60 ? * * -- every hour
    //0 * * ? * * --- every min
    //0/5 * * ? * * --- every 5sec

    //0 0 1 * * MON,TUE,WED,THU,FRI - Only send reminder emails on Business Working Days at 1am
    @Scheduled(cron = "0/5 * * ? * *")
    public void scheduledJobToSendReminderEmails(){

        bookingService.sendCollectReminderEmails();
        bookingService.sendReturnCardReminderEmails();

        System.out.println("Email Sent!");
    }
}
