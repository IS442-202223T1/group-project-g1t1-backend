package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingService implements BorrowerOps, GopOps{

    private final BookingRepository bookingRepository;
    private final AccountService accountService;

    public BookingService(BookingRepository bookingRepository, AccountService accountService) {
        this.bookingRepository = bookingRepository;
        this.accountService = accountService;
    }

    public ArrayList<Booking> bookPass(BookingDto bookingDto) throws RuntimeException{
        // need to include corp pass 

        UserAccount borrowerObject = accountService.readUserByEmail(bookingDto.email);
        int userBookings = bookingDto.requestedBookings;
        ArrayList<Booking> bookingResults = new ArrayList<>();

        if (checkExceedBookingLimit(bookingDto)){
            throw new RuntimeException("Exceed Booking Limit");
        }
        if (checkInsufficientPass(bookingDto)){
            throw new RuntimeException("Insufficient Pass");
        }

        for (int i=0 ; i<userBookings ; i++){
            Booking newBooking = new Booking(bookingDto.date, borrowerObject);
            Booking bookingResult = bookingRepository.save(newBooking);
            bookingResults.add(bookingResult);
        }

        return bookingResults;

    }

    public boolean checkExceedBookingLimit(BookingDto bookingDto){
        int Year = bookingDto.date.getYear();
        int Month = bookingDto.date.getMonthValue();
        String Email = bookingDto.email;
        int userBookings = bookingDto.requestedBookings;
        int numUserBookingsInMonth = bookingRepository.countForMonthByUser(Year, Month, Email);
        
        return numUserBookingsInMonth + userBookings > 2;
    }
    
    public boolean checkInsufficientPass(BookingDto bookingDto){
        // need to receive corp pass
        int maxNumberOfCards = 2;

        int numBookingsOnDate = bookingRepository.countPassBookingsForDate(bookingDto.date);

        return numBookingsOnDate + bookingDto.requestedBookings > maxNumberOfCards;
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
