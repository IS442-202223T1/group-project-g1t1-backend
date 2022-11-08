package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
import com.is442project.cpa.booking.exception.MembershipNotFoundException;
import com.is442project.cpa.booking.CorporatePass.Status;
import com.is442project.cpa.common.email.EmailService;
import com.is442project.cpa.common.template.EmailTemplate;
import com.is442project.cpa.common.template.TemplateEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class BookingService implements BorrowerOps, GopOps, AdminOps {

    private MembershipRepository membershipRepository;
    private final BookingRepository bookingRepository;
    private final CorporatePassRepository corporatePassRepository;
    private final AccountService accountService;
    private final EmailService emailService;

    public BookingService(BookingRepository bookingRepository, AccountService accountService,
            CorporatePassRepository corporatePassRepository, MembershipRepository membershipRepository,
            EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.corporatePassRepository = corporatePassRepository;
        this.accountService = accountService;
        this.membershipRepository = membershipRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<BookingResponseDTO> bookPass(BookingDTO bookingDTO) {
        UserAccount borrowerObject = accountService.readUserByEmail(bookingDTO.getEmail());

        Membership membership = membershipRepository.findById(bookingDTO.getMembershipId())
                .orElseThrow(() -> new MembershipNotFoundException(bookingDTO.getMembershipId()));

        // todo implement code to check availability of passes, to write some query on
        // the repo
        // List<CorporatePass> availPasses =
        // corporatePassRepository.findAvailblePasses(bookingDto.getMembershipId(),
        // bookingDto.getDate());
        List<CorporatePass> availPasses = corporatePassRepository.findAll(); // fake implementation, can remove once
                                                                             // above is done.

        // todo implement bookpass, currently is a fake implementation.
        Booking newBooking = new Booking();
        List<Booking> bookedpasses = newBooking.bookPass(bookingDTO.getDate(), borrowerObject, availPasses,
                bookingDTO.getQty(), bookingRepository);

        EmailTemplate emailTemplate = new EmailTemplate(membership.getEmailTemplate(), bookedpasses);
        TemplateEngine templateEngine = new TemplateEngine(emailTemplate);
        emailService.sendHtmlMessage(borrowerObject.getEmail(), "CPA - Booking Confirmation",
                templateEngine.getContent());

        if (!membership.isElectronicPass) {
            // todo attach authorisation form
        } else {
            // todo attach ePasses
        }

        return ResponseEntity.ok(new BookingResponseDTO(bookedpasses.get(0)));
    }

    public BookingResponseDTO cancelBooking(String bookingID) {
        return null;
    }

    public List<BookingResponseDTO> getAllBooking(String userID) {
        return null;
    }

    public CorporatePass reportLost(String corporatePassID) {
        return null;
    }

    public List<BookingResponseDTO> getCurrentBooking() {
        return null;
    }

    public List<BookingResponseDTO> getPastBooking() {
        return null;
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public Membership getMembershipByName(String membershipName) {
        return membershipRepository.findByMembershipName(membershipName);
    }

    public List<CorporatePass> getAllPasses() {
        return corporatePassRepository.findAll();
    }

    public List<CorporatePass> getAllPassesByMembership(Membership membership) {
        return corporatePassRepository.findByMembership(membership);
    }

    public Membership createMembership(Membership membership) {
        return membershipRepository.saveAndFlush(membership);
    }

    public boolean collectCard(Long cardId) {
        // update Card where id equal to card id, set is available to false
        CorporatePass corporatePass = corporatePassRepository.findById(cardId)
                .orElseThrow(EntityNotFoundException::new);
        corporatePass.setStatus(Status.LOANED);
        corporatePassRepository.save(corporatePass);
        return true;
    };

    public boolean returnCard(Long cardId) {
        // update Card where id equal to card id, set is available to true
        CorporatePass corporatePass = corporatePassRepository.findById(cardId)
                .orElseThrow(EntityNotFoundException::new);
        ;
        corporatePass.setStatus(Status.AVAILABLE);
        corporatePassRepository.save(corporatePass);
        return true;
    }

    public boolean markLost(Long cardId) {
        CorporatePass corporatePass = corporatePassRepository.findById(cardId)
                .orElseThrow(EntityNotFoundException::new);
        ;
        corporatePass.setStatus(Status.LOST);
        corporatePassRepository.save(corporatePass);
        return true;
    }
}
