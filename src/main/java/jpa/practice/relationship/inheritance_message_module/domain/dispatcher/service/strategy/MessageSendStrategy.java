package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.service.strategy;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;

public interface MessageSendStrategy {
    Class<? extends Dispatcher> ofType();
    void send(Dispatcher dispatcher);
}
