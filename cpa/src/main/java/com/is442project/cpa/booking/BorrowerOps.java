package com.is442project.cpa.booking;

import java.util.*;

import com.is442project.cpa.admin.*;

interface BorrowerOps {
    public abstract BookingResponseDto bookPass(ArrayList<String> passNumberLists);

    public abstract BookingResponseDto cancelBooking(String bookingID);

    public abstract  List<BookingResponseDto> getAllBooking(String userID);

    public abstract CorporatePass reportLost(String corporatePassNumber);
}
