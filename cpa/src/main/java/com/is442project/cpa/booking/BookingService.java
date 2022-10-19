package com.is442project.cpa.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingService implements BorrowerOps, GopOps{

    public final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public ResponseEntity<Booking> bookPass(BookingDto bookingDto){
        Booking newBooking = new Booking(bookingDto.Date, bookingDto.Borrower);
        return ResponseEntity.ok(bookingRepository.save(newBooking));
    }


    public BookingResponseDto cancelBooking(String bookingID){
        return null;
    }

    public List<BookingResponseDto> getAllBooking(String userID){
        return null;
    }

    public CorporatePass reportLost(String corporatePassNumber){
        return null;
    }

    public List<BookingResponseDto> getCurrentBooking(){
        return null;
    };

    public List<BookingResponseDto> getPastBooking(){
        return null;
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
