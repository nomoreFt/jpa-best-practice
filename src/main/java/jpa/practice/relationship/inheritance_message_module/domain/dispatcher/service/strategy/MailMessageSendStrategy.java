package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.strategy;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Mail;
import org.springframework.stereotype.Component;

@Component
public class MailMessageSendStrategy implements MessageSendStrategy {
    @Override
    public Class<? extends Dispatcher> ofType() {
        return Mail.class;
    }

    @Override
    public void send(Dispatcher dispatcher) {
        Mail mail = (Mail) dispatcher;
        mail.dispatchMessage();
    }
}
