package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.Template;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class MembershipSeeder {

    public final MembershipRepository membershipRepository;

    public MembershipSeeder(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;

        insertTestData();
    }

    public void insertTestData() {
        Template physicalEmailTemplate = new Template();
        Template attachmentTemplate = new Template();
        physicalEmailTemplate.setTemplateContent(buildSamplePhysicalPassEmailTemplate());
        attachmentTemplate.setTemplateContent("test");
        Membership membership = new Membership("Jalan Besar Stadium", physicalEmailTemplate, attachmentTemplate);
        Membership membership2 = new Membership("Bedok Stadium", physicalEmailTemplate, attachmentTemplate);
        Membership membership3 = new Membership("Temasek Junior College", physicalEmailTemplate, attachmentTemplate);
        Membership membership4 = new Membership("S.E.A Aquarium", new Template(), attachmentTemplate);
        membership4.setElectronicPass(true);

        membershipRepository.saveAllAndFlush(Arrays.asList(membership, membership2, membership3, membership4));

        System.out.println("======TEST MEMBERSHIP INSERTED======");
    }

    public String buildSamplePhysicalPassEmailTemplate() {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear <borrower_name>,");
        sb.append("<br><br>");
        sb.append(" We are pleased to inform that your booking to <attraction name> is confirmed as follows:");
        sb.append("<br><br>");
        sb.append("Date of Visit : <visit_date> (1day only)");
        sb.append("<br>");
        sb.append("Membership ID : <corp_pass_number>");
        sb.append("<br><br>");
        sb.append("For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.");
        sb.append("<br><br>");
        sb.append("Attached is the authorisation letter to <attraction_name>. Please check that the details are accurate.");
        sb.append("<br><br>");
        sb.append("Please take note of the following for your visit to <attraction_name>:");
        sb.append("<br><br>");
        sb.append("<li>Present this email confirmation to the General Office to collect the membership card(s). </li>");
        sb.append("<li>Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit.</li>");
        sb.append("<li>Present the membership card(s), the authorisation letter and your staff pass at the entrance of <attraction_name>.</li>");
        sb.append("<li>Entry is strictly based on the membership card(s) and the authorisation letter.</li>");
        sb.append("<li>Your presence is required on the day of visit. Entry will be denied without staff’s presence.</li>");
        sb.append("<li>Your booking is non-transferable.</li>");
        sb.append("<li>Visit date is strictly based on the date stated in this email and the authorisation letter.</li>");
        sb.append("<li>Staff found abusing the membership(s) will be subject to disciplinary actions.</li>");
        sb.append("<br><br>");
        sb.append("Enjoy your visit to <attraction_name>!");
        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }

    public String buildSampleElectronicPassEmailTemplate() {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear <borrower_name>,");
        sb.append("<br><br>");
        sb.append(" We are pleased to inform that your booking to <attraction name> is confirmed as follows:");
        sb.append("<br><br>");
        sb.append("Date of Visit : <visit_date> (1day only)");
        sb.append("<br>");
        sb.append("Membership ID : <corp_pass_number>");
        sb.append("<br><br>");
        sb.append("For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.");
        sb.append("<br><br>");
        sb.append("Attached is the authorisation letter to <attraction_name>. Please check that the details are accurate.");
        sb.append("<br><br>");
        sb.append("Please take note of the following for your visit to <attraction_name>:");
        sb.append("<br><br>");
        sb.append("<li>Present this email confirmation to the General Office to collect the membership card(s). </li>");
        sb.append("<li>Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit.</li>");
        sb.append("<li>Present the membership card(s), the authorisation letter and your staff pass at the entrance of <attraction_name>.</li>");
        sb.append("<li>Entry is strictly based on the membership card(s) and the authorisation letter.</li>");
        sb.append("<li>Your presence is required on the day of visit. Entry will be denied without staff’s presence.</li>");
        sb.append("<li>Your booking is non-transferable.</li>");
        sb.append("<li>Visit date is strictly based on the date stated in this email and the authorisation letter.</li>");
        sb.append("<li>Staff found abusing the membership(s) will be subject to disciplinary actions.</li>");
        sb.append("<br><br>");
        sb.append("Enjoy your visit to <attraction_name>!");
        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }
}