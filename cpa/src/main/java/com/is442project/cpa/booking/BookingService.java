package com.is442project.cpa.booking;

import java.util.*;
public class BookingService implements BorrowerOps, GopOps{
    public BookingResponseDto bookPass(ArrayList<String> passNumberLists){

    }

    public BookingResponseDto cancelBooking(String bookingID){

    }

    public List<BookingResponseDto> getAllBooking(String userID){

    }

    public CorporatePass reportLost(String corporatePassNumber){

    }

    public List<BookingResponseDto> getCurrentBooking(){

    };

    public List<BookingResponseDto> getPastBooking(){

    };

    public boolean collectCard(String cardId){
        // update Card where id equal to card id, set is available to false
        return false;
    };

    public boolean returnCard(String cardId){
        // update Card where id equal to card id, set is available to true
        return false;
    }

    public boolean markLost(String cardId){
        return false;
    }


}
