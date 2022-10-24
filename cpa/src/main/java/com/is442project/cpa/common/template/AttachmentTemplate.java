package com.is442project.cpa.common.template;

import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.Map;

//todo: to be implemented later when Membership and Booking Class are implemented.
@Embeddable
public class AttachmentTemplate extends Template{

    public AttachmentTemplate() {
        Map<String, String> placeholderMaps = new HashMap<>();
        placeholderMaps.put("<borrower_name>", "$borrower_name");
        placeholderMaps.put("<attraction_name>", "$attraction_name");

        this.setPlaceHolders(placeholderMaps);
    }

    public AttachmentTemplate(String templateContent) {
        this();
        this.setTemplateContent(templateContent);
    }
}
