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

    @AttributeOverride(name = "templateContent", column = @Column(name = "EMAIL_TEMPLATE_CONTENT"))
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

    public Membership(String membershipName, Template emailTemplate, Template attachmentTemplate, double replacementFee,
            boolean isElectronicPass, String description) {
        this(membershipName, emailTemplate, attachmentTemplate, replacementFee, isElectronicPass, description, null);
    }

    public Membership(String membershipName, Template emailTemplate, Template attachmentTemplate, double replacementFee,
            boolean isElectronicPass, String description, String imageUrl) {
        this.membershipName = membershipName;
        this.emailTemplate = emailTemplate;
        this.attachmentTemplate = attachmentTemplate;
        this.replacementFee = replacementFee;
        this.isElectronicPass = isElectronicPass;
        this.description = description;
        this.imageUrl = imageUrl;
        this.membershipAddress = membershipName + "Address";
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
