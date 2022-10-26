package com.is442project.cpa.common.template;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Map;

@Embeddable
@Data
public class Template {
    private String templateContent;

    @Transient
    private Map<String, String> placeHolders;
    public Template() {
    }
}
