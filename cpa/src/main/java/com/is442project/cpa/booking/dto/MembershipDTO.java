package com.is442project.cpa.booking.dto;

import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.common.template.Template;

import lombok.Data;

import java.util.List;

@Data
public class MembershipDTO {

    private String membershipName;

    private String membershipAddress;

    private Template emailTemplate;

    private Template attachmentTemplate;

    private double replacementFee;

    private boolean isElectronicPass;

    private String description;

    private String imageUrl;

    private String membershipGrade;

    private String logoUrl;

    private List<CorporatePass> corporatePasses;

    private boolean isActive;

}
