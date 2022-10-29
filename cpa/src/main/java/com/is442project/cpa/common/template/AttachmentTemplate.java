package com.is442project.cpa.common.template;

import org.apache.velocity.VelocityContext;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

//todo: to be implemented later when Membership and Booking Class are implemented.
@Embeddable
public class AttachmentTemplate extends Template implements TemplateResources{

    public AttachmentTemplate() {
        Map<String, String> placeholderMaps = new HashMap<>();
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<membership_type>", "$membership_type");
        placeholderMaps.put("<current_date>", "$current_date");

        this.setPlaceHolders(placeholderMaps);
    }

    public AttachmentTemplate(String templateContent) {
        this();
        this.setTemplateContent(templateContent);
    }

    @Override
    public VelocityContext getTemplateContextMapper() {
        VelocityContext context = new VelocityContext();

        context.put("borrower_name", "NameToExtractFromObject");
        //todo: to insert the membership name when ready
        context.put("membership_type", "Singapore Wildlife Reserves Zoo");
        context.put("current_date", LocalDate.now().toString());


        return context;
    }
}
