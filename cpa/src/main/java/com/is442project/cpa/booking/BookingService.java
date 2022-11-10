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

    public boolean bookPass(BookingDTO bookingDto) throws RuntimeException {
        UserAccount borrowerObject = accountService.readUserByEmail(bookingDto.getEmail());

        Membership membership = membershipRepository.findByMembershipName(bookingDto.getMembershipName())
                .orElseThrow(() -> new MembershipNotFoundException(bookingDto.getMembershipName()));

        // check if user exceed 2 loans a month
        if (checkExceedMonthlyLimit(bookingDto)){
            throw new RuntimeException("Exceed 2 loans in a month");
        }
        
        // check if user exceed 2 bookings in the desired day
        if (checkExceedDailyLimit(bookingDto)){
            throw new RuntimeException("Exceed maximum bookings in a day");
        }

        List<CorporatePass> availPasses = getAvailablePasses(bookingDto.getDate(), bookingDto.getMembershipName());
        // check if there is enough passes for the day 
        if(availPasses.size() < bookingDto.getQuantity()){
            throw new RuntimeException("Insufficient Passes");
        }

        // add booking to db
        List<Booking> bookingResults = new ArrayList<>();
        for (int i = 0; i < bookingDto.getQuantity(); i++) {
            CorporatePass assignedPass = availPasses.get(i);
            Booking newBooking = new Booking(bookingDto.getDate(), borrowerObject, assignedPass);
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

        return true;
    }

    public boolean checkExceedMonthlyLimit(BookingDTO bookingDto){
        int year = bookingDto.getDate().getYear();
        int month = bookingDto.getDate().getMonthValue();
        String email = bookingDto.getEmail();
        List<Booking> userBookings = bookingRepository.findByBorrowerEmail(email);

        Set<String> userBookingsSet = new HashSet<>();

        for (Booking booking : userBookings) {
            if (checkBookingStatusEqualsConfirmedOrCollected(booking)){
                if (booking.getBorrowDate().getYear() == year && booking.getBorrowDate().getMonthValue() == month){
                    String key = booking.getBorrowDate().toString() + booking.getCorporatePass().getMembership().getMembershipName();
                    userBookingsSet.add(key);
                }
            }
        }

        userBookingsSet.add(bookingDto.getDate().toString() + bookingDto.getMembershipName());

        return userBookingsSet.size() > 2;
    }

    public boolean checkExceedDailyLimit(BookingDTO bookingDto){
        LocalDate date = bookingDto.getDate();
        String email = bookingDto.getEmail();
        int qty = bookingDto.getQuantity();
        List<Booking> userBookingsInDay = bookingRepository.findByBorrowDateAndBorrowerEmail(date, email);

        Set<String> distinctLocationSet = new HashSet<>();

        for (Booking booking:userBookingsInDay){
            distinctLocationSet.add(booking.getCorporatePass().getMembership().getMembershipName());
        }

        distinctLocationSet.add(bookingDto.getMembershipName());
        
        return (userBookingsInDay.size() + qty > 2) || (distinctLocationSet.size() > 1);
    }

    public boolean checkBookingStatusEqualsConfirmedOrCollected(Booking booking) {
        return booking.getBookingStatus() == BookingStatus.CONFIRMED || booking.getBookingStatus() == BookingStatus.COLLECTED;
    }

    public List<CorporatePass> getAvailablePasses(LocalDate date, String membershipName){
        Membership membership = membershipRepository.findByMembershipName(membershipName)
                .orElseThrow(() -> new MembershipNotFoundException(membershipName));

        List<CorporatePass> activeCorpPassList = corporatePassRepository.findByStatusNotAndMembership(Status.LOST, membership);
        List<Booking> bookingsOnDateAndMembership = getBookingsByDayAndMembership(date, membershipName);

        List<CorporatePass> availableCorpPassList = new ArrayList<>();

        List<Long> bookedCorpPassIdList = new ArrayList<>();
        for (Booking booking:bookingsOnDateAndMembership){
            bookedCorpPassIdList.add(booking.getCorporatePass().getId());
        }

        for (CorporatePass corpPass : activeCorpPassList) {
            if (!bookedCorpPassIdList.contains(corpPass.getId())){
                availableCorpPassList.add(corpPass);
            }
        }

        return availableCorpPassList;
    }

    public List<Booking> getBookingsByDayAndMembership(LocalDate date, String membershipName) {
        List<Booking> bookingsOnDate = bookingRepository.findByBorrowDate(date);
        List<Booking> bookingsOnDateAndMembership = new ArrayList<>();

        for (Booking booking : bookingsOnDate) {
            if (booking.getCorporatePass().getMembership().getMembershipName().equals(membershipName)) {
                if (checkBookingStatusEqualsConfirmedOrCollected(booking)){
                    bookingsOnDateAndMembership.add(booking);
                }
            }
        }

        return bookingsOnDateAndMembership;
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
