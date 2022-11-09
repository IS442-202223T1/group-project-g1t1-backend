package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
import com.is442project.cpa.booking.exception.MembershipNotFoundException;
import com.is442project.cpa.booking.Booking.BookingStatus;
import com.is442project.cpa.booking.CorporatePass.Status;
import com.is442project.cpa.common.email.EmailService;
import com.is442project.cpa.common.template.EmailTemplate;
import com.is442project.cpa.common.template.TemplateEngine;

import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<Booking> bookPass(BookingDTO bookingDTO) throws RuntimeException {
        UserAccount borrowerObject = accountService.readUserByEmail(bookingDTO.getEmail());

        Membership membership = membershipRepository.findByMembershipName(bookingDTO.getMembershipName())
                .orElseThrow(() -> new MembershipNotFoundException(bookingDTO.getMembershipName()));

        // check if user exceed 2 loans a month
        if (checkExceedMonthlyLimit(bookingDTO)){
            throw new RuntimeException("Exceed 2 Loans in a month");
        }
        // check if user exceeds 2 bookings in the desired day
        if (checkExceedDailyLimit(bookingDTO)){
            throw new RuntimeException("Exceed 2 Bookings in a day");
        }

        List<CorporatePass> availPasses =corporatePassRepository.findAvailablePassesForDay(bookingDTO.getMembershipName(),bookingDTO.getDate());
        // check if there is enough passes for the day 
        if(availPasses.size() < bookingDTO.getQuantity()){
            throw new RuntimeException("Insufficient Passes");
        }

        // add booking to db
        List<Booking> bookingResults = new ArrayList<>();
        for (int i = 0; i < bookingDTO.getQuantity(); i++) {
            CorporatePass assignedPass = availPasses.get(i);
            Booking newBooking = new Booking(bookingDTO.getDate(), borrowerObject, assignedPass);
            // if membership is epass then booking status is set to collected
            if (membership.getIsElectronicPass()){
                newBooking.setBookingStatus(BookingStatus.COLLECTED);
            }
            Booking bookingResult = bookingRepository.save(newBooking);
            bookingResults.add(bookingResult);
        }

        EmailTemplate emailTemplate = new EmailTemplate(membership.getEmailTemplate(), bookingResults);
        TemplateEngine templateEngine = new TemplateEngine(emailTemplate);
        emailService.sendHtmlMessage(borrowerObject.getEmail(), "CPA - Booking Confirmation",
                templateEngine.getContent());

        if (!membership.getIsElectronicPass()) {
            // todo attach authorisation form
        } else {
            // todo attach ePasses
        }

        return bookingResults;
    }

    public boolean checkExceedMonthlyLimit(BookingDTO bookingDto){
        int Year = bookingDto.getDate().getYear();
        int Month = bookingDto.getDate().getMonthValue();
        String Email = bookingDto.getEmail();
        List<Booking> userBookings = bookingRepository.getConfirmedAndCollectedLoansForMonthByUser(Year, Month, Email); 

        Set<String> userBookingsSet = new HashSet<>();
        for(Booking booking : userBookings){
            String key = booking.getBorrowDate().toString() + booking.getCorporatePass().getMembership().getMembershipName();
            userBookingsSet.add(key);
        }

        userBookingsSet.add(bookingDto.getDate().toString() + bookingDto.getMembershipName());

        return userBookingsSet.size() > 2;
    }

    public boolean checkExceedDailyLimit(BookingDTO bookingDto){
        LocalDate date = bookingDto.getDate();
        String Email = bookingDto.getEmail();
        int qty = bookingDto.getQuantity();
        int numUserBookingsInDay = bookingRepository.countBookingsForDayByUser(date, Email);
        
        return numUserBookingsInDay + qty > 2;
    }

    public List<Booking> getBookingsByDayAndMembership(BookingDTO bookingDTO) {
        return bookingRepository.getBookingsByDayAndMembership(bookingDTO.getDate(), bookingDTO.getMembershipName());
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
        Membership membership = membershipRepository.findByMembershipName(membershipName)
            .orElseThrow(() -> new MembershipNotFoundException(membershipName));
        return membership;
    }

    public List<CorporatePass> getAllPasses() {
        return corporatePassRepository.findAll();
    }

    public List<CorporatePass> getAllPassesByMembership(Membership newMembership) {
        return corporatePassRepository.findByMembership(newMembership);
    }

    public Membership createMembership(Membership membership) {
        return membershipRepository.saveAndFlush(membership);
    }

    public Membership updateMembership(String membershipName, Membership updatedMembership) {
        Membership currentMembership = this.getMembershipByName(membershipName);

        if (updatedMembership.getMembershipName() != null) {
            currentMembership.setMembershipName(updatedMembership.getMembershipName());
        }

        if (updatedMembership.getDescription() != null) {
            currentMembership.setDescription(updatedMembership.getDescription());
        }

        if (updatedMembership.getReplacementFee() != 0.0) {
            currentMembership.setReplacementFee(updatedMembership.getReplacementFee());
        }
        
        currentMembership.setIsElectronicPass(updatedMembership.getIsElectronicPass());

        return membershipRepository.saveAndFlush(currentMembership);
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
