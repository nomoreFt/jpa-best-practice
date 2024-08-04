package jpa.practice.relationship.useful_transient.repository.v3;

import jpa.practice.relationship.useful_transient.dto.v1.OrderV1Dto;
import jpa.practice.relationship.useful_transient.dto.v3.OrderV3Dto;
import jpa.practice.relationship.useful_transient.entity.v3_using_formula.OrderV3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface OrderV3Repository  extends JpaRepository<OrderV3, Long> {
    @Query("""
            SELECT new jpa.practice.relationship.useful_transient.dto.v3.OrderV3Dto
            (o.id, o.quantity, o.unitPrice, o.totalAmount)
             FROM OrderV3 o
            """)
    List<OrderV3Dto> findAllOrderV3DTOs();
}
