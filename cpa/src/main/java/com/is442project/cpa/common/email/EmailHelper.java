package com.is442project.cpa.common.email;

import com.is442project.cpa.booking.Booking;

public class EmailHelper {

    public static final String EMAIL_SUBJECT_ORIGIN = "CPA - ";
    public static final String EMAIL_SUBJECT_CANCELLED = EMAIL_SUBJECT_ORIGIN + "Booking Confirmation Cancelled";

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
}
