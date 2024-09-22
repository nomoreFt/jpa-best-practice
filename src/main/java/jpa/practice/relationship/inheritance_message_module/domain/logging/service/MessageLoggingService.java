package jpa.practice.relationship.inheritance_message_module.domain.logging.service;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.event.MessageDispatchedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class MessageLoggingService {

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onMessageDispatched(MessageDispatchedEvent event) {
        // 이벤트가 발생하면 메시지 정보를 로그로 남김
        System.out.println("Message dispatched: " + event.getDispatcher().getClass().getSimpleName());
    }
}