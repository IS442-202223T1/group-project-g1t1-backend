package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
import com.is442project.cpa.booking.exception.MembershipNotFoundException;
import com.is442project.cpa.common.email.EmailService;
import com.is442project.cpa.common.template.EmailTemplate;
import com.is442project.cpa.common.template.TemplateEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

@Component
public class BookingService implements BorrowerOps, GopOps{

    private MembershipRepository membershipRepository;
    private final BookingRepository bookingRepository;
    private final CorporatePassRepository corporatePassRepository;
    private final AccountService accountService;
    private final EmailService emailService;

    public BookingService(BookingRepository bookingRepository, AccountService accountService, CorporatePassRepository corporatePassRepository, MembershipRepository membershipRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.corporatePassRepository = corporatePassRepository;
        this.accountService = accountService;
        this.membershipRepository = membershipRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<BookingResponseDto> bookPass(BookingDto bookingDto){
        UserAccount borrowerObject = accountService.readUserByEmail(bookingDto.getEmail());

        Membership membership = membershipRepository.findById(bookingDto.getMembershipId())
                .orElseThrow(() -> new MembershipNotFoundException(bookingDto.getMembershipId()));

        //todo implement code to check availability of passes, to write some query on the repo
        //List<CorporatePass> availPasses = corporatePassRepository.findAvailblePasses(bookingDto.getMembershipId(), bookingDto.getDate());
        List<Booking> bookingsInMonth = bookingRepository.getAllBookingsByUserInAMonth(bookingDto.getDate().getYear(), bookingDto.getDate().getMonthValue(), bookingDto.getEmail());
        if(bookingsInMonth.size()>2){
            // throw error
        }

        Map<String, Booking> bookingMap = new HashMap<>();
        for(Booking booking : bookingsInMonth){
            String hashKey = booking.getBorrowDate().toString() + booking.getCorporatePass().getMembershipType().getMembershipType();
            bookingMap.put(hashKey, booking);
        }

        Set<Map.Entry<String,Booking>> set = bookingMap.entrySet();
        // user has hit loan quota in the month alr
        if(set.size()>1){
            // throw error;
        }

        List<CorporatePass> availPasses = corporatePassRepository.getAvailablePassesForBooking(bookingDto.getDate().getMonth(), bookingDto.getMembershipType()); //fake implementation, can remove once above is done.
        if(availPasses.size() < bookingDto.getQty()){
            // throw error
        }

        //todo implement bookpass, currently is a fake implementation.
        // create booking and save to db
        // update the impacted corporate passes
        List<Booking> bookedPasses = new ArrayList<>();
        for(int i = 0; i<bookingDto.getQty(); i++){
            CorporatePass assignedPass = availPasses.get(i);
            collectCard(assignedPass.getId());
            Booking newBooking = new Booking(LocalDate.now(), borrowerObject, assignedPass);
            bookedPasses.add(newBooking);
            bookingRepository.save(newBooking);
        }

        EmailTemplate emailTemplate = new EmailTemplate(membership.getEmailTemplate(), bookedPasses);
        TemplateEngine templateEngine = new TemplateEngine(emailTemplate);
        emailService.sendHtmlMessage(borrowerObject.getEmail(), "CPA - Booking Confirmation", templateEngine.getContent());

        if(!membership.isElectronicPass) {
            //todo attach authorisation form
        } else {
            //todo attach ePasses
        }

        return ResponseEntity.ok(new BookingResponseDto(bookedPasses.get(0)));
    }

    public BookingResponseDto cancelBooking(String bookingID){
        return null;
    }

    public List<BookingResponseDto> getAllBooking(String userID){
        return null;
    }

    public List<Membership> getAllAttractions() {
        return membershipRepository.findAll();
    }

    public CorporatePass reportLost(String corporatePassNumber){
        return null;
    }

    public List<BookingResponseDto> getCurrentBooking(){
        return null;
    }

    public List<BookingResponseDto> getPastBooking(){
        return null;
    }

    public List<CorporatePass> getAllPasses(){
        return corporatePassRepository.findAll();
    }

    public boolean collectCard(Long cardId){
        // update Card where id equal to card id, set is available to false
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("collected");
        corporatePassRepository.save(corporatePass);
        return true;
    };

    public double returnCard(Long cardId){
        // update Card where id equal to card id, set is available to true
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("available");
        corporatePassRepository.save(corporatePass);
        return 0.0;
    }

    public double markLost(Long cardId){
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("lost");
        corporatePassRepository.save(corporatePass);
        // return corporatePass.getMembershipType().getFees();
        return 0.0;
    }


}
