package com.is442project.cpa.booking.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.service.AccountService;
import com.is442project.cpa.booking.exception.MembershipNotFoundException;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.BookingRepository;
import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.CorporatePassRepository;
import com.is442project.cpa.booking.model.Membership;
import com.is442project.cpa.booking.model.MembershipRepository;
import com.is442project.cpa.booking.model.Booking.BookingStatus;
import com.is442project.cpa.booking.model.CorporatePass.Status;
import com.is442project.cpa.booking.dto.BookerDetailsResponseDTO;
import com.is442project.cpa.booking.dto.BookingDTO;
import com.is442project.cpa.booking.dto.BookingEmailDTO;
import com.is442project.cpa.booking.dto.BookingResponseDTO;
import com.is442project.cpa.common.email.Attachment;
import com.is442project.cpa.common.email.EmailService;
import com.is442project.cpa.common.email.EmailHelper;
import com.is442project.cpa.common.pdf.AuthorizationLetter;
import com.is442project.cpa.common.pdf.ElectronicPass;
import com.is442project.cpa.common.pdf.PdfFactory;
import com.is442project.cpa.common.template.AuthorizationLetterTemplate;
import com.is442project.cpa.common.template.ElectronicPassTemplate;
import com.is442project.cpa.common.template.EmailTemplate;
import com.is442project.cpa.common.template.TemplateEngine;
import com.is442project.cpa.config.model.GlobalConfig;
import com.is442project.cpa.config.model.GlobalConfigRepository;


@Component
public class BookingService implements BorrowerOps, GopOps, AdminOps {

    Logger logger = LoggerFactory.getLogger(BookingService.class);

    private MembershipRepository membershipRepository;

    private final BookingRepository bookingRepository;

    private final CorporatePassRepository corporatePassRepository;

    private final AccountService accountService;

    private final EmailService emailService;

    private final GlobalConfigRepository globalConfigRepository;

    private GlobalConfig globalConfig;

    public BookingService(BookingRepository bookingRepository, AccountService accountService,
            CorporatePassRepository corporatePassRepository, MembershipRepository membershipRepository,
            EmailService emailService, GlobalConfigRepository globalConfigRepository) {
        this.bookingRepository = bookingRepository;
        this.corporatePassRepository = corporatePassRepository;
        this.accountService = accountService;
        this.membershipRepository = membershipRepository;
        this.emailService = emailService;
        this.globalConfigRepository = globalConfigRepository;

        this.globalConfig = globalConfigRepository.findFirstBy();
    }

    public List<Booking> bookPass(BookingDTO bookingDTO) throws RuntimeException {
        this.globalConfig = globalConfigRepository.findFirstBy();

        UserAccount borrowerObject = accountService.readUserByEmail(bookingDTO.getEmail());

        Membership membership = membershipRepository.findByMembershipName(bookingDTO.getMembershipName())
                .orElseThrow(() -> new MembershipNotFoundException(bookingDTO.getMembershipName()));

        // check if user exceed 2 loans a month
        if (checkExceedMonthlyLimit(bookingDTO)) {
            throw new RuntimeException("You have exceeded the maximum loans in a month");
        }

        // check if user exceed 2 bookings in the desired day
        if (checkExceedDailyLimit(bookingDTO)) {
            throw new RuntimeException("You have exceeded the maximum bookings in a day");
        }

        // check if user has any outstanding dues
        if (checkForDuesOwed(bookingDTO.getEmail())) {
            throw new RuntimeException("You have outstanding dues to be paid");
        }

        List<CorporatePass> availPasses = getAvailablePasses(bookingDTO.getDate(), bookingDTO.getMembershipName());
        // check if there is enough passes for the day
        if (availPasses.size() < bookingDTO.getQuantity()) {
            throw new RuntimeException("Insufficient Passes");
        }

        // add booking to db
        List<Booking> bookingResults = new ArrayList<>();
        for (int i = 0; i < bookingDTO.getQuantity(); i++) {
            CorporatePass assignedPass = availPasses.get(i);
            Booking newBooking = new Booking(bookingDTO.getDate(), borrowerObject, assignedPass);
            // if membership is epass then booking status is set to collected
            if (membership.getIsElectronicPass()) {
                newBooking.setBookingStatus(BookingStatus.COLLECTED);
            }
            Booking bookingResult = bookingRepository.save(newBooking);
            bookingResults.add(bookingResult);
        }

        return bookingResults;
    }

    public boolean sendEmail(BookingEmailDTO bookingEmailDTO) {

        UserAccount borrowerObject = accountService.readUserByEmail(bookingEmailDTO.getEmail());

        Membership membership = membershipRepository.findByMembershipName(bookingEmailDTO.getMembershipName())
                .orElseThrow(() -> new MembershipNotFoundException(bookingEmailDTO.getMembershipName()));

        EmailTemplate emailTemplate = new EmailTemplate(globalConfig, membership.getEmailTemplate(), bookingEmailDTO.getBookingResults());
        TemplateEngine templateEngine = new TemplateEngine(emailTemplate);

        if (membership.getIsElectronicPass()) {
            List<Attachment> ePassAttachmentList = new ArrayList<>();
            for (int i = 0; i < bookingEmailDTO.getBookingResults().size(); i++) {
                ElectronicPassTemplate ePassTemplate = new ElectronicPassTemplate(globalConfig, membership.getAttachmentTemplate(), bookingEmailDTO.getBookingResults().get(i));
                ElectronicPass ePass = new ElectronicPass(globalConfig, ePassTemplate, bookingEmailDTO.getBookingResults().get(i), i+1);
                PdfFactory pdfFactory = new PdfFactory(ePass);
                ePassAttachmentList.add(new Attachment("ePass" + (i+1) +".pdf", pdfFactory.generatePdfFile()));

            }

            emailService.sendHtmlMessageWithAttachments(borrowerObject.getEmail(), "CPA - Booking Confirmation",
                    templateEngine.getContent(), ePassAttachmentList);

        } else {
            AuthorizationLetterTemplate attachmentTemplate = new AuthorizationLetterTemplate(globalConfig, membership.getAttachmentTemplate(), bookingEmailDTO.getBookingResults());
            AuthorizationLetter authorizationLetter = new AuthorizationLetter(globalConfig,attachmentTemplate);
            PdfFactory pdfFactory = new PdfFactory(authorizationLetter);

            emailService.sendHtmlMessageWithAttachments(borrowerObject.getEmail(), "CPA - Booking Confirmation",
                    templateEngine.getContent(), Arrays.asList(new Attachment("Authorization Letter.pdf", pdfFactory.generatePdfFile())));
        }

        return true;
    }

    public boolean checkExceedMonthlyLimit(BookingDTO bookingDto) {
        int year = bookingDto.getDate().getYear();
        int month = bookingDto.getDate().getMonthValue();
        String email = bookingDto.getEmail();
        List<Booking> userBookings = bookingRepository.findByBorrowerEmail(email);

        Set<String> userBookingsSet = new HashSet<>();

        for (Booking booking : userBookings) {
            if (booking.getBookingStatus() != BookingStatus.CANCELLED) {
                if (booking.getBorrowDate().getYear() == year && booking.getBorrowDate().getMonthValue() == month) {
                    String key = booking.getBorrowDate().toString()
                            + booking.getCorporatePass().getMembership().getMembershipName();
                    userBookingsSet.add(key);
                }
            }
        }

        userBookingsSet.add(bookingDto.getDate().toString() + bookingDto.getMembershipName());

        int monthlyLimit = globalConfig.getLoanLimitPerMonth();

        return userBookingsSet.size() > monthlyLimit;
    }

    public boolean checkExceedDailyLimit(BookingDTO bookingDto) {
        LocalDate date = bookingDto.getDate();
        String email = bookingDto.getEmail();
        int qty = bookingDto.getQuantity();
        List<Booking> userBookingsInDay = bookingRepository.findByBorrowDateAndBorrowerEmailAndBookingStatusNot(date,
                email, BookingStatus.CANCELLED);

        Set<String> distinctLocationSet = new HashSet<>();

        for (Booking booking : userBookingsInDay) {
            distinctLocationSet.add(booking.getCorporatePass().getMembership().getMembershipName());
        }

        distinctLocationSet.add(bookingDto.getMembershipName());

        int dailyLimit = globalConfig.getPassLimitPerLoan();

        return (userBookingsInDay.size() + qty > dailyLimit) || (distinctLocationSet.size() > 1);
    }

    public boolean checkForDuesOwed(String email) {

        List<Booking> userBookingsWithDuesOwed = bookingRepository.findByBorrowerEmailAndBookingStatus(email,
                BookingStatus.DUESOWED);

        return userBookingsWithDuesOwed.size() > 0;
    }

    public List<CorporatePass> getAvailablePasses(LocalDate date, String membershipName) {
        Membership membership = membershipRepository.findByMembershipName(membershipName)
                .orElseThrow(() -> new MembershipNotFoundException(membershipName));

        List<CorporatePass> activeCorpPassList = corporatePassRepository.findByStatusNotAndMembership(Status.LOST,
                membership);
        List<Booking> bookingsOnDateAndMembership = getBookingsByDayAndMembership(date, membershipName);

        List<CorporatePass> availableCorpPassList = new ArrayList<>();

        List<Long> bookedCorpPassIdList = new ArrayList<>();
        for (Booking booking : bookingsOnDateAndMembership) {
            bookedCorpPassIdList.add(booking.getCorporatePass().getId());
        }

        for (CorporatePass corpPass : activeCorpPassList) {
            if (!bookedCorpPassIdList.contains(corpPass.getId())) {
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
                if (booking.getBookingStatus() != BookingStatus.CANCELLED) {
                    bookingsOnDateAndMembership.add(booking);
                }
            }
        }

        return bookingsOnDateAndMembership;
    }

    public boolean cancelBooking(int bookingID) {
        Optional<Booking> response = bookingRepository.findById(bookingID);
        if (response.isPresent()) {
            LocalDate today = LocalDate.now();
            Booking booking = response.get();

            // if pass is an epass, booking can be cancelled as long as it is 1 day before
            // booking date
            if (booking.getCorporatePass().getMembership().getIsElectronicPass()) {
                // check if cancellation is made one day before
                if (booking.getBorrowDate().isAfter(today)) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking);
                    return true;
                }
            }
            // else, booking can be cancelled only if it is 1 day before booking date and
            // borrower have not collected pass
            else {
                if (booking.getBorrowDate().isAfter(today) && booking.getBookingStatus() != BookingStatus.COLLECTED) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking);
                    return true;
                }
            }
        }
        return false;
    }

    public List<BookingResponseDTO> getUpcomingBookings(String email) {
        List<Booking> bookings = bookingRepository.findByBorrowerEmail(email);
        List<BookingResponseDTO> upcomingBookings = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        for (Booking booking : bookings) {
            if (booking.getBorrowDate().isAfter(yesterday) && booking.getBookingStatus() != BookingStatus.CANCELLED) {
                BookingResponseDTO bookingResponseDTO = convertToBookingResponseDTO(booking);

                if (!booking.getCorporatePass().getMembership().getIsElectronicPass()) {
                    BookerDetailsResponseDTO bookerDetailsResponseDTO = getPreviousDayBoookingDetails(booking.getBorrowDate(), booking.getCorporatePass().getId());

                    if (bookerDetailsResponseDTO != null) {
                        bookingResponseDTO.setPreviousBookingDate(booking.getBorrowDate().minusDays(1));
                        bookingResponseDTO.setPreviousBookerName(bookerDetailsResponseDTO.getBookerName());
                        bookingResponseDTO.setPreviousBookerContactNumber(bookerDetailsResponseDTO.getContactNumber());
                    }
                }
                upcomingBookings.add(bookingResponseDTO);
            }
        }

        return upcomingBookings;
    }

    public List<BookingResponseDTO> getPastBookings(String email) {

        List<Booking> bookings = bookingRepository.findByBorrowerEmail(email);
        List<BookingResponseDTO> pastBookings = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Booking booking : bookings) {
            if (booking.getBorrowDate().isBefore(today) && booking.getBookingStatus() != BookingStatus.CANCELLED) {
                BookingResponseDTO bookingResponseDTO = convertToBookingResponseDTO(booking);
                pastBookings.add(bookingResponseDTO);
            }
        }

        return pastBookings;
    }

    public BookerDetailsResponseDTO getPreviousDayBoookingDetails(LocalDate date, Long cpid) {
        LocalDate previousDate = date.minusDays(1);
        List<Booking> previousDayBookings = bookingRepository.findByBorrowDate(previousDate);

        for (Booking booking : previousDayBookings) {
            if (booking.getCorporatePass().getId().equals(cpid)) {
                BookerDetailsResponseDTO bookerDetailsResponseDTO = new BookerDetailsResponseDTO(
                    booking.getBorrower().getName(), booking.getBorrower().getContactNumber(),
                    booking.getCorporatePass().getPassID()
                );
                return bookerDetailsResponseDTO;
            }
        }

        return null;
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public List<Membership> getAllActiveMemberships() {
        return membershipRepository.findByIsActive(true);
    }

    public Membership getMembershipByName(String membershipName) {
        Membership membership = membershipRepository.findByMembershipName(membershipName)
            .orElseThrow(
                () -> new MembershipNotFoundException(membershipName)
            );
        return membership;
    }

    public List<CorporatePass> getAllPasses() {
        return corporatePassRepository.findAll();
    }

    public List<CorporatePass> getActivePassesByMembership(Membership newMembership) {
        return corporatePassRepository.findByMembershipAndIsActive(newMembership, true);
    }

    public Membership createMembership(Membership membership) {
        return membershipRepository.saveAndFlush(membership);
    }

    public Membership updateMembership(String membershipName, Membership updatedMembership) {
        Membership currentMembership = this.getMembershipByName(membershipName);

        if (updatedMembership.getMembershipName() != null) {
            currentMembership.setMembershipName(updatedMembership.getMembershipName());
        }

        if (updatedMembership.getMembershipAddress() != null) {
            currentMembership.setMembershipAddress(updatedMembership.getMembershipAddress());
        }

        if (updatedMembership.getDescription() != null) {
            currentMembership.setDescription(updatedMembership.getDescription());
        }

        if (updatedMembership.getReplacementFee() != 0.0) {
            currentMembership.setReplacementFee(updatedMembership.getReplacementFee());
        }

        if (updatedMembership.getMembershipGrade() != null) {
            currentMembership.setMembershipGrade(updatedMembership.getMembershipGrade());
        }

        if (updatedMembership.getLogoUrl() != null) {
            currentMembership.setLogoUrl(updatedMembership.getLogoUrl());
        }

        if (updatedMembership.getEmailTemplate() != null) {
            currentMembership.setEmailTemplate(updatedMembership.getEmailTemplate());
        }

        if (updatedMembership.getAttachmentTemplate() != null) {
            currentMembership.setAttachmentTemplate(updatedMembership.getAttachmentTemplate());
        }

        if (updatedMembership.getImageUrl() != null) {
            currentMembership.setImageUrl(updatedMembership.getImageUrl());
        }

        currentMembership.setIsElectronicPass(updatedMembership.getIsElectronicPass());

        return membershipRepository.saveAndFlush(currentMembership);
    }

    public void enableMembership(String membershipName) {
        Membership membership = this.getMembershipByName(membershipName);
        membership.setIsActive(true);
        membershipRepository.saveAndFlush(membership);
    }

    public void deleteMembership(String membershipName) {
        Membership membership = this.getMembershipByName(membershipName);
        membership.setIsActive(false);
        membershipRepository.saveAndFlush(membership);
    }

    public CorporatePass createPass(Membership membership, CorporatePass pass) {
        pass.setMembership(membership);
        pass.setIsActive(true);
        return corporatePassRepository.saveAndFlush(pass);
    }

    public List<CorporatePass> createPasses(Membership membership, List<CorporatePass> newPasses) {
        for (CorporatePass newPass : newPasses) {
            this.createPass(membership, newPass);
        }

        return corporatePassRepository.findByMembershipAndIsActive(membership, true);
    }

    public List<CorporatePass> updatePasses(String membershipName, List<CorporatePass> updatedPasses) {
        Membership membership = this.getMembershipByName(membershipName);
        List<CorporatePass> currentPasses = corporatePassRepository.findByMembershipAndIsActive(membership, true);

        for (CorporatePass currentPass : currentPasses) {
            boolean isPassPresent = false;

            for (CorporatePass updatedPass : updatedPasses) {
                if (currentPass.getId() == updatedPass.getId()) {
                    if (updatedPass.getPassID() != null) {
                        currentPass.setPassID(updatedPass.getPassID());
                    }

                    if (updatedPass.getMaxPersonsAdmitted() != 0) {
                        currentPass.setMaxPersonsAdmitted(updatedPass.getMaxPersonsAdmitted());
                    }

                    if (updatedPass.getExpiryDate() != null) {
                        currentPass.setExpiryDate(updatedPass.getExpiryDate());
                    }

                    corporatePassRepository.saveAndFlush(currentPass);
                    isPassPresent = true;
                    continue;
                }
            }

            if (!isPassPresent) {
                currentPass.setIsActive(false);
                corporatePassRepository.saveAndFlush(currentPass);
            }
        }

        for (CorporatePass updatedPass : updatedPasses) {
            if (updatedPass.getId() == null) {
                this.createPass(membership, updatedPass);
            }
        }

        return corporatePassRepository.findByMembershipAndIsActive(membership, true);
    }
    
    public List<Booking> getBookingsByEmail(String email) {
        List<Booking> bookings;
        List<Booking> openBookingsByEmail = new ArrayList<>();

        if(email == null || email.equals("")){
            bookings = bookingRepository.findAll();
        }
        else{
            bookings = bookingRepository.findByBorrowerEmail(email);
        }

        for (Booking booking : bookings) {
            if((booking.getBookingStatus() == BookingStatus.CONFIRMED ||booking.getBookingStatus() == BookingStatus.COLLECTED || booking.getBookingStatus() == BookingStatus.DUESOWED) && !booking.getCorporatePass().getMembership().getIsElectronicPass()){
                openBookingsByEmail.add(booking);
            }
        }

        return openBookingsByEmail;
    }

    public List<Booking> getBookingsToday() {
        List<Booking> bookings;
        List<Booking> openBookingsByEmail = new ArrayList<>();
        LocalDate localDate = LocalDate.now();

        bookings = bookingRepository.findByBorrowDate(localDate);

        for (Booking booking : bookings) {
            if((booking.getBookingStatus() == BookingStatus.CONFIRMED ||booking.getBookingStatus() == BookingStatus.COLLECTED || booking.getBookingStatus() == BookingStatus.DUESOWED) && !booking.getCorporatePass().getMembership().getIsElectronicPass()){
                openBookingsByEmail.add(booking);
            }
        }

        return openBookingsByEmail;
    }

    public List<Booking> getBookingsContainingEmail(String email) {
        List<Booking> bookings;
        List<Booking> openBookingsByEmail = new ArrayList<>();

        logger.debug(email);
        if(email == null || email.equals("")){
            bookings = bookingRepository.findAll();
        }
        else{
            logger.debug(email);
            bookings = bookingRepository.findByBorrowerEmailContainingIgnoreCase(email);
            logger.debug(bookings.toString());

        }

        for (Booking booking : bookings) {
            if((booking.getBookingStatus() == BookingStatus.CONFIRMED ||booking.getBookingStatus() == BookingStatus.COLLECTED || booking.getBookingStatus() == BookingStatus.DUESOWED) && !booking.getCorporatePass().getMembership().getIsElectronicPass()){

                openBookingsByEmail.add(booking);
            }
        }

        return openBookingsByEmail;

    }

    public boolean updateBookingStatus(int bookingID, String actionToPerform) {
        Map<String, Status> nextCorporatePassStatus = new HashMap<>();
        nextCorporatePassStatus.put("collect", Status.LOANED);
        nextCorporatePassStatus.put("return", Status.AVAILABLE);
        nextCorporatePassStatus.put("markLost", Status.LOST);
        nextCorporatePassStatus.put("clearDues", Status.LOST);

        Optional<Booking> bookingResult = bookingRepository.findById(bookingID);
        if (bookingResult.isPresent()) {
            Booking booking = bookingResult.get();
            commitBookingToDatabase(booking, actionToPerform);

            CorporatePass corporatePass = booking.getCorporatePass();
            corporatePass.setStatus(nextCorporatePassStatus.get(actionToPerform));
        }
        return false;
    }

    public void enablePasses(String membershipName) {
        Membership membership = this.getMembershipByName(membershipName);
        List<CorporatePass> passes = corporatePassRepository.findByMembershipAndIsActive(membership, false);

        for (CorporatePass pass : passes) {
            pass.setIsActive(true);
            corporatePassRepository.saveAndFlush(pass);
        }
    }

    public void deletePasses(String membershipName) {
        Membership membership = this.getMembershipByName(membershipName);
        List<CorporatePass> passes = corporatePassRepository.findByMembershipAndIsActive(membership, true);

        for (CorporatePass pass : passes) {
            pass.setIsActive(false);
            corporatePassRepository.saveAndFlush(pass);
        }
    }

    public void commitBookingToDatabase(Booking currentBooking, String actionToPerform) {
        Map<String, BookingStatus> nextBookingStatus = new HashMap<>();
        nextBookingStatus.put("collect", BookingStatus.COLLECTED);
        nextBookingStatus.put("return", BookingStatus.RETURNED);
        nextBookingStatus.put("markLost", BookingStatus.DUESOWED);
        nextBookingStatus.put("clearDues", BookingStatus.DUESPAID);

        currentBooking.setBookingStatus(nextBookingStatus.get(actionToPerform));
        // if return, mark all those bookings with the same corporate pass that happened
        // before this date as returned
        if (actionToPerform.equals("return")) {
            List<Booking> bookings = bookingRepository.findAll();
            for (Booking booking : bookings) {
                if (booking.getCorporatePass().equals(currentBooking.getCorporatePass()) && booking.getBorrowDate().isBefore(currentBooking.getBorrowDate()) && booking.getBookingStatus().equals(BookingStatus.COLLECTED)) {
                    booking.setBookingStatus(BookingStatus.RETURNED);
                    bookingRepository.save(booking);
                }
            }
        }
        // if lost, mark all subsequent bookings as cancelled
        // and add fees to this booking
        else if (actionToPerform.equals("markLost")) {
            List<Booking> bookings = bookingRepository.findAll();
            for (Booking booking : bookings) {
                if (booking.getCorporatePass().equals(currentBooking.getCorporatePass()) && booking.getBorrowDate().isAfter(currentBooking.getBorrowDate()) && booking.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
                    booking.setBookingStatus(BookingStatus.CANCELLED);
                    bookingRepository.save(booking);

                    emailService.sendHtmlMessage(
                        booking.getBorrower().getEmail(),
                        EmailHelper.EMAIL_SUBJECT_CANCELLED, EmailHelper.EMAIL_CONTENT_CANCELLED(booking)
                    );
                }
            }

            List<UserAccount> admins = accountService.getAllAdmins();
            admins.stream().forEach(admin -> {
                emailService.sendHtmlMessage(admin.getEmail(),
                    EmailHelper.EMAIL_SUBJECT_LOST_CARD, EmailHelper.EMAIL_CONTENT_LOST_CARD(currentBooking));

                logger.info("Report Lost Email Sent to: " + admin.getEmail());
            });

            currentBooking.setFeesOwed(currentBooking.getCorporatePass().getMembership().getReplacementFee());

        } else if (actionToPerform.equals("clearDues")) {
            currentBooking.setFeesOwed(0);

        } else if (actionToPerform.equals("collect")) {
            emailService.sendHtmlMessage(currentBooking.getBorrower().getEmail(),
                    EmailHelper.EMAIL_SUBJECT_COLLECTED, EmailHelper.EMAIL_CONTENT_COLLECTED(currentBooking));

        }

        bookingRepository.save(currentBooking);

    }

    private BookingResponseDTO convertToBookingResponseDTO(Booking booking) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        BookingResponseDTO bookingResponseDTO = mapper.map(booking, BookingResponseDTO.class);

        bookingResponseDTO.setPreviousBookingDate(null);
        bookingResponseDTO.setPreviousBookerName(null);
        bookingResponseDTO.setPreviousBookerContactNumber(null);

        return bookingResponseDTO;
    }

    public void deleteBookingsByBorrower(String email) {
        List<Booking> bookings = bookingRepository.findByBorrowDateAfterAndBorrowerEmailAndBookingStatusNot(LocalDate.now(), email, BookingStatus.COLLECTED);
        for (Booking booking : bookings) {
            bookingRepository.delete(booking);
        }
        bookingRepository.flush();
    }

    public void sendReturnCardReminderEmails() {
        LocalDate today = LocalDate.now();
        List<Booking> allBookings = bookingRepository.findAll();

        for (Booking booking : allBookings) {
            if (booking.getBookingStatus() == BookingStatus.COLLECTED && booking.getBorrowDate().isBefore(today) && !booking.getCorporatePass().getMembership().getIsElectronicPass()) {
                emailService.sendHtmlMessage(
                    booking.getBorrower().getEmail(),
                    EmailHelper.EMAIL_SUBJECT_RETURN_REMINDER, EmailHelper.EMAIL_CONTENT_RETURN_REMINDER(booking)
                );

                logger.info("RETURN PASS reminder Email sent to: " + booking.getBorrower().getEmail());
            }
        }
    }

    public void sendCollectReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> bookings = bookingRepository.findAll();
        ArrayList<String> emails = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getBookingStatus() == BookingStatus.CONFIRMED && booking.getBorrowDate().equals(tomorrow) && !booking.getCorporatePass().getMembership().getIsElectronicPass()) {
                emailService.sendHtmlMessage(
                    booking.getBorrower().getEmail(),
                    EmailHelper.EMAIL_SUBJECT_COLLECT_REMINDER,
                    EmailHelper.EMAIL_CONTENT_COLLECT_REMINDER(booking)
                );

                logger.info("COLLECT PASS reminder email sent to: " + booking.getBorrower().getEmail());
            }
        }
    }

    public HashMap<LocalDate, HashSet<CorporatePass>> getAvailableBooking(LocalDate startDate, LocalDate endDate) {
        HashMap<LocalDate, HashSet<CorporatePass>> availableBookings = new HashMap<>();
        List<CorporatePass> corporatePasses = corporatePassRepository.findAll();
        HashSet<CorporatePass> corporatePassesSet = corporatePasses.stream().collect(Collectors.toCollection(HashSet::new));
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            HashSet<CorporatePass> corporatePassesCopy = new HashSet<>();
            corporatePassesCopy.addAll(corporatePassesSet);
            availableBookings.put(date, corporatePassesCopy);
            List<Booking> bookings = bookingRepository.findByBorrowDate(date);
            for (Booking booking : bookings) {
                if (booking.getBookingStatus() != BookingStatus.CANCELLED && booking.getBookingStatus() != BookingStatus.RETURNED) {
                    availableBookings.get(date).remove(booking.getCorporatePass());
                }
            }
        }
        return availableBookings;
    }

}
