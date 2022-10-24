package com.is442project.cpa.common.template;

import org.apache.velocity.VelocityContext;

import java.util.Map;

public interface TemplateResources {
    VelocityContext getTemplateContextMapper();

    String getTemplateContent();

    Map<String, String> getPlaceHolders();
}
