package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.event.MessageDispatchedEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@PrimaryKeyJoinColumn(name = "mail_dispatcher_id")
public class Mail extends Dispatcher {

    private String emailSubject;
    private String emailContent;

    protected Mail() {}

    @Builder
    public Mail(String sender, String receiver, LocalDateTime scheduledDispatchTime, String emailSubject, String emailContent) {
        super(sender, receiver, scheduledDispatchTime);
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
    }

    @Override
    public void dispatchMessage() {
        System.out.println("MailMessageSender.send");
        registerEvent(new MessageDispatchedEvent(this));
    }
}