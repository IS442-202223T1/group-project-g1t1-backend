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
import java.time.LocalDate;
import java.util.*;

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

        //todo implement code to check availability of passes, to write some query on the repo
        //List<CorporatePass> availPasses = corporatePassRepository.findAvailblePasses(bookingDto.getMembershipId(), bookingDto.getDate());
        List<Booking> bookingsInMonth = bookingRepository.findByStatus("active");
        //  bookingRepository.getAllBookingsByUserInAMonth(bookingDTO.getDate().getYear(), bookingDTO.getDate().getMonthValue(), bookingDTO.getEmail());
        if(bookingsInMonth.size()>2){
            // throw error
        }

        Map<String, Booking> bookingMap = new HashMap<>();
        for(Booking booking : bookingsInMonth){
            String hashKey = booking.getBorrowDate().toString() + booking.getCorporatePass().getMembership().getMembershipName();
            bookingMap.put(hashKey, booking);
        }

        Set<Map.Entry<String,Booking>> set = bookingMap.entrySet();
        // user has hit loan quota in the month alr
        if(set.size()>1){
            // throw error;
        }

        // List<CorporatePass> availPasses = corporatePassRepository.getAvailablePassesForBooking(bookingDTO.getDate().getMonth(), bookingDTO.getMembershipType()); //fake implementation, can remove once above is done.
        List<CorporatePass> availPasses = corporatePassRepository.findAll();
        if(availPasses.size() < bookingDTO.getQty()){
            // throw error
        }

        //todo implement bookpass, currently is a fake implementation.
        // create booking and save to db
        // update the impacted corporate passes
        List<Booking> bookedPasses = new ArrayList<>();
        for(int i = 0; i<bookingDTO.getQty(); i++){
            CorporatePass assignedPass = availPasses.get(i);
            Booking newBooking = new Booking(LocalDate.now(), borrowerObject, assignedPass);
            assignedPass.setStatus(Status.LOANED);
            corporatePassRepository.save(assignedPass);
            bookedPasses.add(newBooking);
            bookingRepository.save(newBooking);
        }

        EmailTemplate emailTemplate = new EmailTemplate(membership.getEmailTemplate(), bookedPasses);
        TemplateEngine templateEngine = new TemplateEngine(emailTemplate);
        emailService.sendHtmlMessage(borrowerObject.getEmail(), "CPA - Booking Confirmation",
                templateEngine.getContent());

        if (!membership.isElectronicPass) {
            // todo attach authorisation form
        } else {
            // todo attach ePasses
        }

        return ResponseEntity.ok(new BookingResponseDTO(bookedPasses.get(0)));
    }

    public List<Booking> getAvailableBookingByUser(String email){
        return bookingRepository.findByEmailAndStatus(email, "confirmed");
    }

    public BookingResponseDTO cancelBooking(BookingIDDTO bookingIDDTO){
        Optional<Booking> response = bookingRepository.findById(bookingIDDTO.getBookingID());
        if(response.isPresent()){
            Booking booking  = response.get();
            booking.setStatus("cancelled");
            bookingRepository.save(booking);
            CorporatePass corporatePass = booking.getCorporatePass();
            corporatePass.setStatus(Status.AVAILABLE);
            corporatePassRepository.save(corporatePass);
            return new BookingResponseDTO(booking);
        }
        return null;
    }

    public List<BookingResponseDTO> getAllBooking(String userID){
        List<BookingResponseDTO> response = new ArrayList<>();
        List<Booking> currentBooking = bookingRepository.findAll();
        for(Booking booking : currentBooking){
            response.add( new BookingResponseDTO(booking));
        }
        return response;
    }

    public List<Booking> getAllBookings(){
        // return bookingRepository.findByStatus("active");
        return bookingRepository.findAll();
    }

    public List<Membership> getAllAttractions() {
        return membershipRepository.findAll();
    }

    public CorporatePass reportLost(String corporatePassID) {
        return null;
    }

    public List<BookingResponseDTO> getCurrentBooking(String email){
        List<BookingResponseDTO> response = new ArrayList<>();
        // List<Booking> currentBooking = bookingRepository.findByEmailAndStatus(email, "confirmed");
        List<Booking> currentBooking = bookingRepository.findByStatus("confirmed");
        for(Booking booking : currentBooking){
            response.add( new BookingResponseDTO(booking));
        }
        return response;
    }

    public List<BookingResponseDTO> getPastBooking(String email){
        List<BookingResponseDTO> response = new ArrayList<>();
        // List<Booking> currentBooking = bookingRepository.findByEmailAndStatus(email, "returned");
        List<Booking> currentBooking = bookingRepository.findByStatus("confirmed");
        for(Booking booking : currentBooking){
            response.add( new BookingResponseDTO(booking));
        }
        return response;
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

    public boolean collectCard(int bookingID) {
        // update Card where id equal to card id, set is available to false
        Optional<Booking> response = bookingRepository.findById(bookingID);
        if(response.isPresent()){
            Booking booking  = response.get();
            booking.setStatus("collected");
            bookingRepository.save(booking);
            CorporatePass corporatePass = booking.getCorporatePass();
            corporatePass.setStatus(Status.LOANED);
            corporatePassRepository.save(corporatePass);
            return true;
        }
        return false;
    };

    public double returnCard(int bookingID){
        // update Card where id equal to card id, set is available to true
        Optional<Booking> response = bookingRepository.findById(bookingID);
        if(response.isPresent()){
            Booking booking  = response.get();
            CorporatePass corporatePass = booking.getCorporatePass();
            List<Booking> earlierBookings = bookingRepository.findBookingsWithCorporatePassIDBeforeDate(corporatePass.getId(), booking.getBorrowDate());
            for(int i = 0; i<earlierBookings.size(); i++){
                Booking bookingToSave = earlierBookings.get(i);
                bookingToSave.setStatus("returned");
                bookingRepository.save(bookingToSave);
            }
            corporatePass.setStatus(Status.AVAILABLE);
            corporatePassRepository.save(corporatePass);
            return 0.0;
        }
        return 0.0;
    }

    public double markLost(int bookingID){
        Optional<Booking> response = bookingRepository.findById(bookingID);
        if(response.isPresent()){
            Booking booking  = response.get();
            booking.setStatus("duesOwed");
            CorporatePass corporatePass = booking.getCorporatePass();
            booking.setFeesDue(corporatePass.getLostFees());
            corporatePass.setStatus(Status.LOST);
            corporatePassRepository.save(corporatePass);
            bookingRepository.save(booking);
            return corporatePass.getLostFees();
        }
        return 0.0;
        // return corporatePass.getMembershipType().getFees();
    }
}
