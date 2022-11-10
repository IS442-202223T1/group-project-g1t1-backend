package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.Template;

import java.util.List;

public class MembershipDTO {
    private String membershipName;

    private Template emailTemplate;

    private Template attachmentTemplate;

    private double replacementFee;

    private boolean isElectronicPass;

    private String description;

    private String imageUrl;

    private List<CorporatePass> corporatePasses;

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public double getReplacementFee() {
        return replacementFee;
    }

    public void setReplacementFee(double replacementFee) {
        this.replacementFee = replacementFee;
    }

    public boolean getIsElectronicPass() {
        return isElectronicPass;
    }

    public void setIsElectronicPass(boolean isElectronicPass) {
        this.isElectronicPass = isElectronicPass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<CorporatePass> getCorporatePasses() {
        return corporatePasses;
    }

    public void setCorporatePasses(List<CorporatePass> corporatePasses) {
        this.corporatePasses = corporatePasses;
    }

    public Template getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(Template emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public Template getAttachmentTemplate() {
        return attachmentTemplate;
    }

    public void setAttachmentTemplate(Template attachmentTemplate) {
        this.attachmentTemplate = attachmentTemplate;
    }

}
