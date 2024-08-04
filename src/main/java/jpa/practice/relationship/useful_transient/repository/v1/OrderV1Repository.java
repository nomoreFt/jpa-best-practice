package jpa.practice.relationship.useful_transient.repository.v1;

import jpa.practice.relationship.useful_transient.dto.v1.OrderV1Dto;
import jpa.practice.relationship.useful_transient.entity.v1_onmethod.OrderV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface OrderV1Repository extends JpaRepository<OrderV1, Long> {
    @Query("""
            SELECT new jpa.practice.relationship.useful_transient.dto.v1.OrderV1Dto
            (o.id, o.quantity, o.unitPrice, o.quantity * o.unitPrice)
             FROM OrderV1 o
            """)
    List<OrderV1Dto> findAllOrderV1DTOs();
}
