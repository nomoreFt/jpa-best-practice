package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Alimtalk;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimtalkRepository extends DispatcherBaseRepository<Alimtalk> {
}
