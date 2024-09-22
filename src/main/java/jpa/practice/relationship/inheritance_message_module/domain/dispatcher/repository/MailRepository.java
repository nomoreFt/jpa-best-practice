package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Mail;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends DispatcherBaseRepository<Mail> {
}
