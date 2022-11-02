package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Component
public class BookingService implements BorrowerOps, GopOps{

    private final BookingRepository bookingRepository;
    private final CorporatePassRepository corporatePassRepository;
    private final AccountService accountService;

    public BookingService(BookingRepository bookingRepository, AccountService accountService, CorporatePassRepository corporatePassRepository) {
        this.bookingRepository = bookingRepository;
        this.corporatePassRepository = corporatePassRepository;
        this.accountService = accountService;
    }

    public ResponseEntity<Booking> bookPass(BookingDto bookingDto){
        UserAccount borrowerObject = accountService.readUserByEmail(bookingDto.getEmail());
        Booking newBooking = new Booking(bookingDto.getDate(), borrowerObject);
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

    public List<CorporatePass> getAllPasses(){
        return corporatePassRepository.findAll();
    }

    public boolean collectCard(String cardId){
        // update Card where id equal to card id, set is available to false
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("collected");
        corporatePassRepository.save(corporatePass);
        return true;
    };

    public boolean returnCard(String cardId){
        // update Card where id equal to card id, set is available to true
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("available");
        corporatePassRepository.save(corporatePass);
        return true;
    }

    public boolean markLost(String cardId){
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("lost");
        corporatePassRepository.save(corporatePass);
        return true;
    }


}
