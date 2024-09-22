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
@PrimaryKeyJoinColumn(name = "sms_dispatcher_id")
public class Sms extends Dispatcher {

    private String smsContent;


    @Override
    public void dispatchMessage() {
        System.out.println("SmsMessageSender.send");
        registerEvent(new MessageDispatchedEvent(this));
    }

    protected Sms() {}

    @Builder
    public Sms(String sender, String receiver, LocalDateTime scheduledDispatchTime, String smsContent) {
        super(sender, receiver, scheduledDispatchTime);  // 상위 클래스 생성자 호출
        this.smsContent = smsContent;
    }

    //toString
    @Override
    public String toString() {
        return "Sms{" +
                "smsContent='" + smsContent + '\'' +
                '}';
    }

}
