package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.Template;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Membership {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long membershipId;

    @AttributeOverride(name="templateContent", column=@Column(name="EMAIL_TEMPLATE_CONTENT"))
    @Embedded
    private Template emailTemplate;

    @AttributeOverride(name="templateContent", column=@Column(name="ATTACHMENT_TEMPLATE_CONTENT"))
    @Embedded
    private Template attachmentTemplate;

    public Membership(Template emailTemplate, Template attachmentTemplate ) {
        this.emailTemplate = emailTemplate;
        this.attachmentTemplate = attachmentTemplate;
    }
}
