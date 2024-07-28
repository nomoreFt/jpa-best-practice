package jpa.practice.relationship.onetoone.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 자동으로 id가 MemberId로 설정되기 때문에 생성전략 필요 없다.
 * 단방향으로만 사용 가능
 */
@Entity
public class MemberDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    private Long id;
    private String address;

    @MapsId
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected MemberDetails() {}

    public MemberDetails(String address) {
        this.address = address;
    }

    //helper
    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if(!(obj instanceof MemberDetails other)) return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "MemerDetails{" + "id=" + id + ", address='" + address + '\'' + '}';
    }

}
