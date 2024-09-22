package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.strategy;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Alimtalk;
import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;
import org.springframework.stereotype.Component;

@Component
public class AlimtalkMessageSendStrategy implements MessageSendStrategy {
    @Override
    public Class<? extends Dispatcher> ofType() {
        return Alimtalk.class;
    }

    @Override
    public void send(Dispatcher dispatcher) {
        Alimtalk alimtalk = (Alimtalk) dispatcher;
        alimtalk.dispatchMessage();
    }

}
