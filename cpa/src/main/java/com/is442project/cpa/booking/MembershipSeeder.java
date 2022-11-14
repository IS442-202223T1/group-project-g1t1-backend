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
        physicalEmailTemplate.setTemplateContent(buildSamplePhysicalPassEmailTemplate());

        Template authorizationLetterAttachmentTemplate = new Template();
        authorizationLetterAttachmentTemplate.setTemplateContent(buildSamplePhysicalPassAuthLetterTemplate());

        Template ePassEmailTemplate = new Template();
        ePassEmailTemplate.setTemplateContent(buildSampleElectronicPassEmailTemplate());

        Template ePassAttachmentTemplate = new Template();
        ePassAttachmentTemplate.setTemplateContent(buildSampleEPassAttachmentTemplate());

        Membership membership = new Membership("Mandai Wildlife Reserve", physicalEmailTemplate, authorizationLetterAttachmentTemplate,
                53.50, false,
                "Visit Singapore's best wildlife parks at the Mandai Wildlife Reserve.",
                "https://images.unsplash.com/photo-1665006518423-b81a193b4100?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80");
        Membership membership2 = new Membership("Universal Studios", physicalEmailTemplate, authorizationLetterAttachmentTemplate, 87.00,
                false,
                "Universal Studios Singapore is a theme park located within the Resorts World Sentosa at Sentosa, Singapore.",
                "https://images.unsplash.com/photo-1601930113377-729966035f34?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80");
        Membership membership3 = new Membership("Zoo", physicalEmailTemplate, authorizationLetterAttachmentTemplate, 19.00, true, "");
        Membership membership4 = new Membership("SEA Aquarium", ePassEmailTemplate, ePassAttachmentTemplate, 20.50, true, "");

        membershipRepository.saveAllAndFlush(Arrays.asList(membership, membership2, membership3, membership4));

        System.out.println("======TEST MEMBERSHIP INSERTED======");
    }

    public String buildSamplePhysicalPassEmailTemplate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear {{borrower_name}},");
        sb.append("<br><br>");
        sb.append(" We are pleased to inform that your booking to {{attraction name}} is confirmed as follows:");
        sb.append("<br><br>");
        sb.append("Date of Visit : {{visit_date}} (1day only)");
        sb.append("<br>");
        sb.append("Membership ID : {{corp_pass_number}}");
        sb.append("<br><br>");
        sb.append("For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.");
        sb.append("<br><br>");
        sb.append("Attached is the authorisation letter to {{attraction_name}}. Please check that the details are accurate.");
        sb.append("<br><br>");
        sb.append("Please take note of the following for your visit to {{attraction_name}}:");
        sb.append("<br><br>");
        sb.append("<li>Present this email confirmation to the General Office to collect the membership card(s). </li>");
        sb.append("<li>Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit.</li>");
        sb.append("<li>Present the membership card(s), the authorisation letter and your staff pass at the entrance of {{attraction_name}}.</li>");
        sb.append("<li>Entry is strictly based on the membership card(s) and the authorisation letter.</li>");
        sb.append("<li>Your presence is required on the day of visit. Entry will be denied without staff’s presence.</li>");
        sb.append("<li>Your booking is non-transferable.</li>");
        sb.append("<li>Visit date is strictly based on the date stated in this email and the authorisation letter.</li>");
        sb.append("<li>Staff found abusing the membership(s) will be subject to disciplinary actions.</li>");
        sb.append("<br><br>");
        sb.append("Enjoy your visit to {{attraction_name}}!");
        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }

    public String buildSampleElectronicPassEmailTemplate() {
        StringBuilder sb = new StringBuilder();

        sb.append("Dear {{borrower_name}},");
        sb.append("<br><br>");
        sb.append(" We are pleased to inform that your booking to {{attraction_name}} is confirmed as follows:");
        sb.append("<br><br>");
        sb.append("Date of Visit : {{visit_date}} (1day only)");
        sb.append("<br>");
        sb.append("Membership ID : {{corp_pass_number}}");
        sb.append("<br><br>");
        sb.append("For any change in visit date, you are required to cancel your booking (at least 1 day before) and to submit a new booking in the system.");
        sb.append("<br><br>");
        sb.append("Attached is the authorisation letter to {{attraction_name}}. Please check that the details are accurate.");
        sb.append("<br><br>");
        sb.append("Please take note of the following for your visit to {{attraction_name}}:");
        sb.append("<br><br>");
        sb.append("<li>Present this email confirmation to the General Office to collect the membership card(s). </li>");
        sb.append("<li>Collect the membership card(s) from the General Office one day before your visit date and return the membership card(s) by 9am the next working day after your visit.</li>");
        sb.append("<li>Present the membership card(s), the authorisation letter and your staff pass at the entrance of {{attraction_name}}.</li>");
        sb.append("<li>Entry is strictly based on the membership card(s) and the authorisation letter.</li>");
        sb.append("<li>Your presence is required on the day of visit. Entry will be denied without staff’s presence.</li>");
        sb.append("<li>Your booking is non-transferable.</li>");
        sb.append("<li>Visit date is strictly based on the date stated in this email and the authorisation letter.</li>");
        sb.append("<li>Staff found abusing the membership(s) will be subject to disciplinary actions.</li>");
        sb.append("<br><br>");
        sb.append("Enjoy your visit to {{attraction_name}}!");
        sb.append("<br><br><br>");
        sb.append("Warm regards");
        sb.append("<br>");
        sb.append("HR Department");

        return sb.toString();
    }

    public String buildSamplePhysicalPassAuthLetterTemplate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: {{current_date}}");
        sb.append("<br><br>");
        sb.append("{{membership_address}}");
        sb.append("<br><br><br>");
        sb.append("Dear Sir/Madam,");
        sb.append("<br><br>");
        sb.append("AUTHORISATION LETTER - {{attraction_name}}");
        sb.append("<br><br>");
        sb.append("Singapore Sports School hereby authorise our employee identified below, to utilise our {{corp_pass_number}} pass(s) on the date as indicated.");
        sb.append("<br><br><br>");
        sb.append("Date of Visit: {{visit_date}}");
        sb.append("<br><br>");
        sb.append("Name of Employee: {{borrower_name}}");
        sb.append("<br><br><br>");
        sb.append("Thank you.");
        sb.append("<br><br><br>");
        sb.append("Human Resource Department");
        sb.append("<br>");
        sb.append("(This is a system generated letter.)");

        return sb.toString();
    }

    public String buildSampleEPassAttachmentTemplate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date: {{ballot_date}}");
        sb.append("<br><br>");
        sb.append("CORPORATE LETTER");
        sb.append("<br>");
        sb.append("{{attraction_name}}");
        sb.append("<br><br><br><br><br><br><br>");
        sb.append("This is to certify that the following employee is authorised to visit Singapore Zoo & River Wonders under your Company's Corporate Membership number {{corp_pass_number}}");
        sb.append("<br><br>");
        sb.append("Employee: {{borrower_name}}");
        sb.append("<br><br>");
        sb.append("Date of Visit: {{visit_date}}");
        sb.append("<br><br>");
        sb.append("Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.");
        sb.append("<br><br>");
        sb.append("Terms and Conditions");
        sb.append("<br><br>");
        sb.append("<ul>");
        sb.append("<li>");
        sb.append("All corporate members must secure a time slot via https://managebooking.mandai.com/ and comply " +
                "to MWR Safe Management Measures for your safety and well-being, otherwise entry is not allowed." +
                "Time slot bookings are subject to availability, on a first come, first served basis.");
        sb.append("</li>");
        sb.append("<li>");
        sb.append("Each letter allows complimentary admission to Singapore Zoo & River Wonders for up to four (4) " +
                "persons, one of whom must be an employee of the corporate member named above. The employee " +
                "must be present and produce valid staff pass or NRIC along with the signed letter for benefit to apply.");
        sb.append("</li>");
        sb.append("<li>");
        sb.append("Each letter can only be used ONCE a day.");
        sb.append("</li>");
        sb.append("<li>");
        sb.append("In the event of unauthorized use or copy of the letter, Singapore Zoo & River Wonders will deny entry " +
                "and verification will be done with the company which will take appropriate action for any wilful " +
                "violation.");
        sb.append("</li>");
        sb.append("<li>");
        sb.append("In the event that the letter is detected to be presented more than once on the same day; the employee " +
                "will be liable for the additional entry at Singapore Zoo & River Wonders prevailing walk-in rate " +
                "accordingly.");
        sb.append("</li>");
        sb.append("<li>");
        sb.append("Your existing Corporate Membership will expire once the validity period is over.");
        sb.append("</li>");
        sb.append("</li>");
        sb.append("</ul>");
        sb.append("In addition, you shall adhere to the Membership terms and conditions stated " +
                "https://www.mandai.com/en/memberships/corporate-membership.html during your visit.");
        sb.append("<br><br>");
        sb.append("Best Regards,");
        sb.append("<br><br><br>");
        sb.append("Manager, Human Resource");

        return sb.toString();
    }
}