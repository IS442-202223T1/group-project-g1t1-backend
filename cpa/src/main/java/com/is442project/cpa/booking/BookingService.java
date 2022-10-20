package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingService implements BorrowerOps, GopOps{

    private final BookingRepository bookingRepository;
    private final AccountService accountService;

    public BookingService(BookingRepository bookingRepository, AccountService accountService) {
        this.bookingRepository = bookingRepository;
        this.accountService = accountService;
    }

    public ResponseEntity<Booking> bookPass(BookingDto bookingDto){
        UserAccount borrowerObject = accountService.readUserByEmail(bookingDto.email);
        Booking newBooking = new Booking(bookingDto.Date, borrowerObject);
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
