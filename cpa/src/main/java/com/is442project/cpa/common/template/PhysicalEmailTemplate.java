package com.is442project.cpa.common.template;

import com.is442project.cpa.booking.Booking;
import org.apache.velocity.VelocityContext;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;
@Embeddable
public class PhysicalEmailTemplate extends Template implements TemplateResources {

    @Transient
    private Booking booking;

    private PhysicalEmailTemplate() {
        Map<String, String>placeholderMaps = new HashMap<>();
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");
        placeholderMaps.put("<visit_date>", "$visit_date");
        placeholderMaps.put("<corp_pass_number>", "$corp_pass_number");

        this.setPlaceHolders(placeholderMaps);
    }

    public PhysicalEmailTemplate(Template template, Booking booking){
        this();
        this.booking = booking;
        setTemplateContent(template.getTemplateContent());
    }

    public PhysicalEmailTemplate(String templateContent, Booking booking) {
        this();
        this.booking = booking;
        setTemplateContent(templateContent);
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public VelocityContext getTemplateContextMapper() {
        VelocityContext context = new VelocityContext();

        context.put("borrower_name", booking.getBorrower().getName());
        context.put("attraction_name", booking.getCorporatePass().getMembershipType().getMembershipType());
        context.put("visit_date", booking.getBorrowDate());
        context.put("corp_pass_number", booking.getCorporatePass().getNumber());

        return context;
    }


}
