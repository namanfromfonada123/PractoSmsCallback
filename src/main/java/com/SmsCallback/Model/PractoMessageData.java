package com.SmsCallback.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "practo_message_data")
public class PractoMessageData {

    @Id
    @Column(name = "message_id", nullable = false)
    private Long messageId;

    @Column(name = "corelationid")
    private String corelationId;

    @Column(name = "execution_id")
    private String executionId;

    @Column(name = "campaign_id")
    private String campaignId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "user_code", length = 50)
    private String userCode;

    @Column(name = "senderID", length = 50)
    private String senderID;

    @Column(name = "description")
    private String description;

    @Column(name = "submission_date")
    private String submissionDate;

    @Column(name = "done_date")
    private String doneDate;

    @Column(name = "message_pdu")
    private String messagePdu;

    @Column(name = "message_state", length = 50)
    private String messageState;

    @Column(name = "message_text")
    private String messageText;

}
