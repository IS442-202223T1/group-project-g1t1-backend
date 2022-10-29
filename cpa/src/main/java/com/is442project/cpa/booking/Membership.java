package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.Template;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class Membership {

    @Id
    private String membershipType;

    @AttributeOverride(name="templateContent", column=@Column(name="EMAIL_TEMPLATE_CONTENT"))
    @Embedded
    private Template emailTemplate;

    @AttributeOverride(name="templateContent", column=@Column(name="ATTACHMENT_TEMPLATE_CONTENT"))
    @Embedded
    private Template attachmentTemplate;

    public Membership(){}

    public String getMembershipType(){
        return membershipType;
    }

    public void setMembershipType(String membershipType){
        this.membershipType = membershipType;
    }

    public Membership(String membershipType, Template emailTemplate, Template attachmentTemplate ) {
        this.membershipType = membershipType;
        this.emailTemplate = emailTemplate;
        this.attachmentTemplate = attachmentTemplate;
    }
}
