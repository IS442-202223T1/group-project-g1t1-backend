package com.is442project.cpa.common.template;

import com.is442project.cpa.booking.Booking;
import org.apache.velocity.VelocityContext;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

//todo: partial implementation to set the framework to use template engine, to implement when Membership and Booking class is completed.
@Embeddable
public class EmailTemplate extends Template implements TemplateResources {

    @Transient
    private Booking booking;

    public EmailTemplate() {
        Map<String, String>placeholderMaps = new HashMap<>();
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");

        this.setPlaceHolders(placeholderMaps);
    }

    public EmailTemplate(String templateContent) {
        this();
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
        //todo: to insert the membership name when ready
        context.put("attraction_name", "Singapore Wildlife Reserves Zoo");


        return context;
    }


}
