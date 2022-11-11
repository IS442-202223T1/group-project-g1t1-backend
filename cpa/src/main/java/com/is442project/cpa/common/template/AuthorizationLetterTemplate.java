package com.is442project.cpa.common.template;

import com.is442project.cpa.booking.Booking;
import org.apache.velocity.VelocityContext;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
public class AuthorizationLetterTemplate extends Template implements TemplateResources{

    private List<Booking> bookings;

    private AuthorizationLetterTemplate() {
        Map<String, String> placeholderMaps = new HashMap<>();
        placeholderMaps.put("<current_date>", "$current_date");
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");
        placeholderMaps.put("<membership_address>", "$membership_address");
        placeholderMaps.put("<corp_pass_number>", "$corp_pass_number");
        placeholderMaps.put("<visit_date>", "$visit_date");

        this.setPlaceHolders(placeholderMaps);
    }

    public AuthorizationLetterTemplate(Template template, List<Booking> bookings) {
        this();
        this.bookings = bookings;
        this.setTemplateContent(template.getTemplateContent());
    }
    public AuthorizationLetterTemplate(String templateContent, List<Booking> bookings) {
        this();
        this.bookings = bookings;
        this.setTemplateContent(templateContent);
    }

    @Override
    public VelocityContext getTemplateContextMapper() {
        VelocityContext context = new VelocityContext();

        Booking booking = bookings.get(0);

        context.put("current_date", LocalDate.now());
        context.put("visit_date", LocalDate.now().toString());
        context.put("borrower_name", booking.getBorrower().getName());
        context.put("attraction_name", booking.getCorporatePass().getMembership().getMembershipName().toUpperCase());
        context.put("membership_address", booking.getCorporatePass().getMembership().getMembershipAddress());
        context.put("corp_pass_number", bookings.stream().map(b -> b.getCorporatePass().getPassID())
                .reduce("", (p1, p2)-> p1 + " , " + p2).substring(2));

        return context;
    }
}