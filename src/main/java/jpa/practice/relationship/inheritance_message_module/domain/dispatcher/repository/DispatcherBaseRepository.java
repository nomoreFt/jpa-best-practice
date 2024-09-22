package jpa.practice.relationship.inheritance_message_module.domain.dispatcher.repository;

import jpa.practice.relationship.inheritance_message_module.domain.dispatcher.entity.Dispatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
@Transactional(readOnly = true)
public interface DispatcherBaseRepository<T extends Dispatcher> extends JpaRepository<T, Long> {
    T findBySender(String sender);
    T findByReceiver(String receiver);
}
