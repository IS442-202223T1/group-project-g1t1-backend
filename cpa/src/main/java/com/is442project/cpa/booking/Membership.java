package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.Template;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String membershipName;

    @NotNull
    private String membershipAddress;

    @AttributeOverride(name="templateContent", column=@Column(name="EMAIL_TEMPLATE_CONTENT"))
    @Embedded
    private Template emailTemplate;

    @AttributeOverride(name = "templateContent", column = @Column(name = "ATTACHMENT_TEMPLATE_CONTENT"))
    @Embedded
    private Template attachmentTemplate;

    @NotNull
    private double replacementFee;

    @NotNull
    private boolean isElectronicPass;

    private String description;

    private String imageUrl;

    public Membership() {
    }

    public Membership(String membershipName, String membershipAddress, Template emailTemplate, Template attachmentTemplate, double replacementFee,
            boolean isElectronicPass, String description) {
        this(membershipName, membershipAddress, emailTemplate, attachmentTemplate, replacementFee, isElectronicPass, description, null);
    }

    public Membership(String membershipName, String membershipAddress, Template emailTemplate, Template attachmentTemplate, double replacementFee,
            boolean isElectronicPass, String description, String imageUrl) {
        this.membershipName = membershipName;
        this.membershipAddress = membershipAddress;
        this.emailTemplate = emailTemplate;
        this.attachmentTemplate = attachmentTemplate;
        this.replacementFee = replacementFee;
        this.isElectronicPass = isElectronicPass;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public long getMembershipId() {
        return id;
    }

    public void setMembershipId(long membershipId) {
        this.id = membershipId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getMembershipAddress() {
        return membershipAddress;
    }

    public void setMembershipAddress(String membershipAddress) {
        this.membershipAddress = membershipAddress;
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
}
