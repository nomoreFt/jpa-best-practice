package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.strategy;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Sms;
import org.springframework.stereotype.Component;

@Component
public class SmsMessageSendStrategy implements MessageSendStrategy {
    @Override
    public Class<? extends Dispatcher> ofType() {
        return Sms.class;
    }

    @Override
    public void send(Dispatcher dispatcher) {
        Sms sms = (Sms) dispatcher;
        sms.dispatchMessage();
    }

}
