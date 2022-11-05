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
import java.util.List;

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
        List<CorporatePass> availPasses = corporatePassRepository.findAll(); //fake implementation, can remove once above is done.

        //todo implement bookpass, currently is a fake implementation.
        Booking newBooking = new Booking();
        List<Booking> bookedpasses = newBooking.bookPass(bookingDto.getDate(),  borrowerObject, availPasses, bookingDto.getQty(), bookingRepository);

        EmailTemplate emailTemplate = new EmailTemplate(membership.getEmailTemplate(), bookedpasses);
        TemplateEngine templateEngine = new TemplateEngine(emailTemplate);
        emailService.sendHtmlMessage(borrowerObject.getEmail(), "CPA - Booking Confirmation", templateEngine.getContent());

        if(!membership.isElectronicPass) {
            //todo attach authorisation form
        } else {
            //todo attach ePasses
        }

        return ResponseEntity.ok(new BookingResponseDto(bookedpasses.get(0)));
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

    public boolean returnCard(Long cardId){
        // update Card where id equal to card id, set is available to true
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("available");
        corporatePassRepository.save(corporatePass);
        return true;
    }

    public boolean markLost(Long cardId){
        CorporatePass corporatePass = corporatePassRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);;
        corporatePass.setStatus("lost");
        corporatePassRepository.save(corporatePass);
        return true;
    }


}
