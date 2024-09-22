package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Sms;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends DispatcherBaseRepository<Sms> {
}
