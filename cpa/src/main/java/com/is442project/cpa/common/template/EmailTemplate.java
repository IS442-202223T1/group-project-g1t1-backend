package com.is442project.cpa.common.template;

import com.is442project.cpa.booking.Booking;
import org.apache.velocity.VelocityContext;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Embeddable
public class EmailTemplate extends Template implements TemplateResources {

    @Transient
    private List<Booking> bookings;

    private EmailTemplate() {
        Map<String, String>placeholderMaps = new HashMap<>();
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");
        placeholderMaps.put("<visit_date>", "$visit_date");
        placeholderMaps.put("<corp_pass_number>", "$corp_pass_number");

        this.setPlaceHolders(placeholderMaps);
    }

    public EmailTemplate(Template template, List<Booking> bookings){
        this();
        this.bookings = bookings;
        setTemplateContent(template.getTemplateContent());
    }

    public EmailTemplate(String templateContent, List<Booking> bookings) {
        this();
        this.bookings = bookings;
        setTemplateContent(templateContent);
    }

    @Override
    public VelocityContext getTemplateContextMapper() {
        VelocityContext context = new VelocityContext();

        context.put("borrower_name", bookings.get(0).getBorrower().getName());
        context.put("attraction_name", bookings.get(0).getCorporatePass().getMembershipType().getMembershipType());
        context.put("visit_date", bookings.get(0).getBorrowDate());
        context.put("corp_pass_number", bookings.stream().map(booking -> booking.getCorporatePass().getNumber())
                .reduce("", (p1, p2)-> p1 + " , " + p2).substring(2));

        return context;
    }


}
