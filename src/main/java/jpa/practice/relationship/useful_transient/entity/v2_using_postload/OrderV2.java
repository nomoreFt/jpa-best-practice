package jpa.practice.relationship.useful_transient.entity.v2_using_postload;

import jakarta.persistence.*;
import jpa.practice.relationship.onetoone.entity.Member;

@Entity
public class OrderV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double unitPrice;

    // 총 금액은 DB에 저장되지 않고, 연산에 의해 계산됨
    @Transient
    private double totalAmount;

    protected OrderV2() {}

    public OrderV2(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @PostLoad
    public void initializeTotalAmount() {
        System.out.println("@PostLoad initializeTotalAmount() called");
        this.totalAmount = quantity * unitPrice;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if(!(obj instanceof Member other)) return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "OrderV2{" + "id=" + id + ", quantity=" + quantity + ", unitPrice=" + unitPrice + '}' + "totalAmount=" + totalAmount;
    }

}