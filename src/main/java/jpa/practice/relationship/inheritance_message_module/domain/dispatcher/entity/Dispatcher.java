package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity;

import jakarta.persistence.*;
import jpa.practice.relationship.inheritance_message_module.domain.base.AggregateRoot;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.event.MessageDispatchedEvent;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Dispatcher extends AggregateRoot<Dispatcher, Long> {

    private String sender;
    private String receiver;

    // 메시지가 최종 발송된 시간 (LocalDateTime으로 저장)
    private LocalDateTime sentAt;

    // 예약 발송 시간
    private LocalDateTime scheduledDispatchTime;

    // 메시지 발송 시 이벤트 등록
    abstract public void dispatchMessage();

    @Override
    public Long getId() {
        return id;
    }

    protected Dispatcher() {}

    public Dispatcher(String sender, String receiver, LocalDateTime scheduledDispatchTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.scheduledDispatchTime = scheduledDispatchTime;
    }

    // Getters and Setters
}
