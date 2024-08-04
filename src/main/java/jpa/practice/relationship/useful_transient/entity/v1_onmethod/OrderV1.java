package jpa.practice.relationship.useful_transient.entity.v1_onmethod;

import jakarta.persistence.*;
import jpa.practice.relationship.onetoone.entity.Member;
import org.aspectj.weaver.Lint;

@Entity
public class OrderV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double unitPrice;

    protected OrderV1() {}

    public OrderV1(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // 이 메서드는 DB와 관련이 없으므로 Transient로 선언
    @Transient
    public double calculateTotalAmount() {
        System.out.println("calculateTotalAmount() called");
        return quantity * unitPrice;
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
        return "OrderV1{" + "id=" + id + ", quantity=" + quantity + ", unitPrice=" + unitPrice + '}' + "totalAmount=" + calculateTotalAmount() + "\n";
    }


}