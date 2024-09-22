package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.event.MessageDispatchedEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@PrimaryKeyJoinColumn(name = "alimtalk_dispatcher_id")
public class Alimtalk extends Dispatcher {

    private String kakaoMessage;
    private String kakaoTemplateId;

    protected Alimtalk() {}

    // 명시적인 생성자
    @Builder
    public Alimtalk(String sender, String receiver, LocalDateTime scheduledDispatchTime, String kakaoMessage, String kakaoTemplateId) {
        super(sender, receiver, scheduledDispatchTime);  // 상위 클래스 생성자 호출
        this.kakaoMessage = kakaoMessage;
        this.kakaoTemplateId = kakaoTemplateId;
    }

    @Override
    public void dispatchMessage() {
        System.out.println("AlimtalkMessageSender.send");
        registerEvent(new MessageDispatchedEvent(this));
    }
}