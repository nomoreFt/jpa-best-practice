package jpa.practice.relationship.inheritance_message_module.domain.base;



public abstract class AggregateRoot<T extends DomainEntity<T, TID>, TID> extends DomainEntity<T, TID> {
}