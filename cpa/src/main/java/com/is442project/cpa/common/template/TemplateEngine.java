package com.is442project.cpa.common.template;

import org.apache.velocity.app.Velocity;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

public class TemplateEngine {
    
    StringWriter writer = new StringWriter();

    public TemplateEngine(TemplateResources templateResources) {
        Map<String, String>placeholders = templateResources.getPlaceHolders();

        Iterator<String> userfriendlyKeys = placeholders.keySet().iterator();

        String vtlFormatTemplateContent = templateResources.getTemplateContent();

        while(userfriendlyKeys.hasNext()) {
            String key = userfriendlyKeys.next();
            vtlFormatTemplateContent=vtlFormatTemplateContent.replaceAll("\\}\\}", ">");
            vtlFormatTemplateContent=vtlFormatTemplateContent.replaceAll("\\{\\{", "<");
           vtlFormatTemplateContent = vtlFormatTemplateContent.replaceAll(key, Matcher.quoteReplacement(placeholders.get(key)));
        }

        Velocity.evaluate(templateResources.getTemplateContextMapper(), writer, "", vtlFormatTemplateContent);
    }

    public String getContent() {
        return writer.toString();
    }

}
