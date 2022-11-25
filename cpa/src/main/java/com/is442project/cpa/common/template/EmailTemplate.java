package com.is442project.cpa.common.template;

import com.is442project.cpa.config.model.GlobalConfig;
import org.apache.velocity.VelocityContext;

import com.is442project.cpa.booking.model.Booking;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Embeddable
public class EmailTemplate extends Template implements TemplateResources {

    @Transient
    private List<Booking> bookings;

    @Transient
    private GlobalConfig globalConfig;

    private EmailTemplate() {
        Map<String, String>placeholderMaps = new HashMap<>();
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");
        placeholderMaps.put("<visit_date>", "$visit_date");
        placeholderMaps.put("<corp_pass_number>", "$corp_pass_number");

        this.setPlaceHolders(placeholderMaps);
    }

    public EmailTemplate(GlobalConfig globalConfig, Template template, List<Booking> bookings){
        this();
        this.bookings = bookings;
        this.globalConfig = globalConfig;
        setTemplateContent(template.getTemplateContent());
    }

    public EmailTemplate(GlobalConfig globalConfig, String templateContent, List<Booking> bookings) {
        this();
        this.bookings = bookings;
        this.globalConfig = globalConfig;
        setTemplateContent(templateContent);
    }

    @Override
    public VelocityContext getTemplateContextMapper() {
        VelocityContext context = new VelocityContext();

        context.put("borrower_name", bookings.get(0).getBorrower().getName());
        context.put("attraction_name", bookings.get(0).getCorporatePass().getMembership().getMembershipName());
        context.put("visit_date", bookings.get(0).getBorrowDate().format(DateTimeFormatter.ofPattern(globalConfig.getDateFormat())));
        context.put("corp_pass_number", bookings.stream().map(booking -> booking.getCorporatePass().getPassID())
                .reduce("", (p1, p2)-> p1 + " , " + p2).substring(2));

        return context;
    }

}
