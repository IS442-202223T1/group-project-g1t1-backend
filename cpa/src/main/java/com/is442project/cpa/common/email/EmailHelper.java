package com.is442project.cpa.common.email;

import com.is442project.cpa.booking.Booking;

public class EmailHelper {

    public static final String EMAIL_SUBJECT_ORIGIN = "CPA - ";
    public static final String EMAIL_SUBJECT_CANCELLED = EMAIL_SUBJECT_ORIGIN + "Booking Confirmation Cancelled";
    public static final String EMAIL_SUBJECT_RETURN_REMINDER = EMAIL_SUBJECT_ORIGIN + "Return Corporate Pass Reminder";
    public static final String EMAIL_SUBJECT_COLLECT_REMINDER = EMAIL_SUBJECT_ORIGIN + "Collect Corporate Pass Reminder";
    public static final String EMAIL_CONTENT_COLLECTED = EMAIL_SUBJECT_ORIGIN + "Corporate Pass Collected!";

    public static final String EMAIL_CONTENT_CANCELLED(Booking booking) {

        StringBuilder sb = new StringBuilder();

        sb.append("Dear " + booking.getBorrower().getName() + ",");
        sb.append("<br><br>");
        sb.append(String.format("Your Corporate Pass booking to %s for %s is cancelled because a borrower has lost the card.",
                booking.getCorporatePass().getMembership().getMembershipName(), booking.getBorrowDate().toString()));

        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }

    public static String EMAIL_CONTENT_RETURN_REMINDER(Booking booking) {

        StringBuilder sb = new StringBuilder();

        sb.append("Dear " + booking.getBorrower().getName() + ",");
        sb.append("<br><br>");
        sb.append(String.format("The Corporate Pass: %s under your booking to %s on %s is due to be returned",
                 booking.getCorporatePass().getPassID(),booking.getCorporatePass().getMembership().getMembershipName(), booking.getBorrowDate().toString()));

        sb.append("<br><br>");
        sb.append("Please return the above corporate pass to the General Pass Office.");

        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }


    public static String EMAIL_CONTENT_COLLECT_REMINDER(Booking booking) {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear " + booking.getBorrower().getName() + ",");
        sb.append("<br><br>");
        sb.append(String.format("The Corporate Pass: %s under your booking to %s on %s is due to be collected today",
                booking.getCorporatePass().getPassID(),booking.getCorporatePass().getMembership().getMembershipName(), booking.getBorrowDate().toString()));

        sb.append("<br><br>");
        sb.append("Please proceed to General Pass Office to collect your corporate pass.");

        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }

    public static String EMAIL_CONTENT_COLLECTED(Booking booking) {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear " + booking.getBorrower().getName() + ",");
        sb.append("<br><br>");
        sb.append(String.format("For record purpose, you have collected the corporate pass %s to %s",
                booking.getCorporatePass().getPassID(),booking.getCorporatePass().getMembership().getMembershipName()));

        sb.append("<br><br>");
        sb.append("Please be reminded to return the corporate pass to General Pass Office after your visit.");

        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }
}
