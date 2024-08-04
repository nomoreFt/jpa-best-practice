package jpa.practice.relationship.useful_transient.service;

import jpa.practice.relationship.useful_transient.dto.v1.OrderV1Dto;
import jpa.practice.relationship.useful_transient.dto.v3.OrderV3Dto;
import jpa.practice.relationship.useful_transient.entity.v1_onmethod.OrderV1;
import jpa.practice.relationship.useful_transient.entity.v2_using_postload.OrderV2;
import jpa.practice.relationship.useful_transient.entity.v3_using_formula.OrderV3;
import jpa.practice.relationship.useful_transient.repository.v1.OrderV1Repository;
import jpa.practice.relationship.useful_transient.repository.v2.OrderV2Repository;
import jpa.practice.relationship.useful_transient.repository.v3.OrderV3Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class OrderService {
    private final OrderV1Repository orderV1Repository;
    private final OrderV2Repository orderV2Repository;
    private final OrderV3Repository orderV3Repository;

    public OrderService(OrderV1Repository orderV1Repository, OrderV2Repository orderV2Repository, OrderV3Repository orderV3Repository) {
        this.orderV1Repository = orderV1Repository;
        this.orderV2Repository = orderV2Repository;
        this.orderV3Repository = orderV3Repository;
    }

    @Transactional
    public void init() {
        Random random = new Random();

        OrderV1 v1_1 = new OrderV1(random.nextInt(10), 10_000);
        OrderV1 v1_2 = new OrderV1(random.nextInt(100), 20_000);

        OrderV2 v2_1 = new OrderV2(random.nextInt(10), 10_000);
        OrderV2 v2_2 = new OrderV2(random.nextInt(100), 20_000);

        OrderV3 v3_1 = new OrderV3(random.nextInt(10), 10_000);
        OrderV3 v3_2 = new OrderV3(random.nextInt(100), 20_000);

        orderV1Repository.saveAllAndFlush(List.of(v1_1, v1_2));
        orderV2Repository.saveAllAndFlush(List.of(v2_1, v2_2));
        orderV3Repository.saveAllAndFlush(List.of(v3_1, v3_2));

    }


    /**
     * 단순 @Transient 메서드를 사용한 방법
     *
     * @Transient 메서드를 호출할 때 '마다' 연산한다.
     * 연산식이 복잡하거나 속성을 여러번 호출해야 하는 경우 성능이 떨어질 수 있다.
     */
    @Transactional(readOnly = true)
    public void fetchV1Orders() {
        List<OrderV1> v1Orders = orderV1Repository.findAll();
        v1Orders.stream()
                .map(OrderV1::calculateTotalAmount)
                .forEach(System.out::println);

        //2회 호출
        v1Orders.stream()
                .map(OrderV1::calculateTotalAmount)
                .forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         ov1_0.id,
         *         ov1_0.quantity,
         *         ov1_0.unit_price
         *     from
         *         orderv1 ov1_0
         * calculateTotalAmount() called
         * 80000.0
         * calculateTotalAmount() called
         * 280000.0
         * calculateTotalAmount() called
         * 80000.0
         * calculateTotalAmount() called
         * 280000.0
         *
         * call 될 떄 마다 연산한다.
         */
    }

    /**
     * @Query에 계산식을 사용한 방법
     */
    @Transactional(readOnly = true)
    public void fetchV1OrdersDto() {
        List<OrderV1Dto> v1_Orders = orderV1Repository.findAllOrderV1DTOs();
        v1_Orders.forEach(System.out::println);
        /**
         * [Hibernate]
         *     select
         *         ov1_0.id,
         *         ov1_0.quantity,
         *         ov1_0.unit_price,
         *         (ov1_0.quantity*ov1_0.unit_price)
         *     from
         *         orderv1 ov1_0
         * OrderV1Dto[id=1, quantity=7, unitPrice=10000.0, totalAmount=70000.0]
         * OrderV1Dto[id=2, quantity=20, unitPrice=20000.0, totalAmount=400000.0]
         */

    }

    /**
     * @PostLoad를 사용한 방법
     *
     * 엔티티 로드 직후 콜백 메서드로 연산을 수행한다.
     * 산출을 반복하지 않는다.
     */
    @Transactional(readOnly = true)
    public void fetchV2Orders() {
        List<OrderV2> v2Orders = orderV2Repository.findAll();
        v2Orders.forEach(System.out::println);
        //2회 호출
        v2Orders.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         ov1_0.id,
         *         ov1_0.quantity,
         *         ov1_0.unit_price
         *     from
         *         orderv2 ov1_0
         * @PostLoad initializeTotalAmount() called
         * @PostLoad initializeTotalAmount() called
         * OrderV2{id=1, quantity=9, unitPrice=10000.0}totalAmount=90000.0
         * OrderV2{id=2, quantity=37, unitPrice=20000.0}totalAmount=740000.0
         * OrderV2{id=1, quantity=9, unitPrice=10000.0}totalAmount=90000.0
         * OrderV2{id=2, quantity=37, unitPrice=20000.0}totalAmount=740000.0
         */

    }

    /**
     * @Formula를 사용한 방법
     */
    @Transactional(readOnly = true)
    public void fetchV3Orders() {
        List<OrderV3> v3Orders = orderV3Repository.findAll();
        v3Orders.forEach(System.out::println);
        //2차 호출
        v3Orders.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         ov1_0.id,
         *         ov1_0.quantity,
         *         ov1_0.quantity * ov1_0.unit_price,
         *         ov1_0.unit_price
         *     from
         *         orderv3 ov1_0
         * OrderV3{id=1, quantity=7, unitPrice=10000.0}totalAmount=70000.0
         * OrderV3{id=2, quantity=42, unitPrice=20000.0}totalAmount=840000.0
         * OrderV3{id=1, quantity=7, unitPrice=10000.0}totalAmount=70000.0
         * OrderV3{id=2, quantity=42, unitPrice=20000.0}totalAmount=840000.0
         */
    }

    /**
     *
     */
    @Transactional(readOnly = true)
    public void fetchV3OrdersDto() {
        List<OrderV3Dto> v3_Orders = orderV3Repository.findAllOrderV3DTOs();
        v3_Orders.forEach(System.out::println);
        /**
         * [Hibernate]
         *     select
         *         ov1_0.id,
         *         ov1_0.quantity,
         *         ov1_0.unit_price,
         *         ov1_0.quantity * ov1_0.unit_price
         *     from
         *         orderv3 ov1_0
         * OrderV3Dto[id=1, quantity=7, unitPrice=10000.0, totalAmount=70000.0]
         * OrderV3Dto[id=2, quantity=42, unitPrice=20000.0, totalAmount=840000.0]
         */
    }

}