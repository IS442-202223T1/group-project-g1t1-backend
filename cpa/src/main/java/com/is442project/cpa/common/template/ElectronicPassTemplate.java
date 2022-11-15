package com.is442project.cpa.common.template;

import com.is442project.cpa.booking.Booking;
import com.is442project.cpa.common.pdf.PdfTemplate;
import org.apache.velocity.VelocityContext;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
public class ElectronicPassTemplate extends Template implements TemplateResources{

    private Booking booking;

    private ElectronicPassTemplate() {
        Map<String, String> placeholderMaps = new HashMap<>();
        placeholderMaps.put("<ballot_date>", "$ballot_date");
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");
        placeholderMaps.put("<membership_address>", "$membership_address");
        placeholderMaps.put("<corp_pass_number>", "$corp_pass_number");
        placeholderMaps.put("<visit_date>", "$visit_date");
        placeholderMaps.put("<corporate_member_name", "$corporate_member_name");

        this.setPlaceHolders(placeholderMaps);
    }

    public ElectronicPassTemplate(Template template, Booking booking) {
        this();
        this.booking = booking;
        this.setTemplateContent(template.getTemplateContent());
    }
    public ElectronicPassTemplate(String templateContent, Booking booking) {
        this();
        this.booking = booking;
        this.setTemplateContent(templateContent);
    }

    @Override
    public VelocityContext getTemplateContextMapper() {
        VelocityContext context = new VelocityContext();

        context.put("corporate_member_name", PdfTemplate.CORPORATE_MEMBER_NAME);
        context.put("ballot_date", booking.getBookingDate());
        context.put("visit_date", booking.getBorrowDate());
        context.put("borrower_name", booking.getBorrower().getName());
        context.put("attraction_name", booking.getCorporatePass().getMembership().getMembershipName().toUpperCase());
        context.put("membership_address", booking.getCorporatePass().getMembership().getMembershipAddress());
        context.put("corp_pass_number", booking.getCorporatePass().getPassID());

        return context;
    }
}
