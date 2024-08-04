package jpa.practice.relationship.useful_transient.repository.v2;

import jpa.practice.relationship.useful_transient.entity.v2_using_postload.OrderV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface OrderV2Repository extends JpaRepository<OrderV2, Long> {
}
