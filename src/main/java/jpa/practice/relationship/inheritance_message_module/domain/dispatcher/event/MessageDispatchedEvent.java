package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.event;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;

public class MessageDispatchedEvent {
    private Dispatcher dispatcher;

    public MessageDispatchedEvent(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }
}