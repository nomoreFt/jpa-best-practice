package jpa.practice.relationship.inheritance_message_module.domain.base;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class DomainEntity<T extends DomainEntity<T, TID>, TID> extends AbstractAggregateRoot<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    protected LocalDateTime created;

    @CreatedBy
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime lastModified;

    @LastModifiedBy
    protected String lastModifiedBy;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        return equals((T)other);
    }

    public boolean equals(T other) {

        if (other == null) {
            return false;
        }

        if (getId() == null) {
            return false;
        }

        if (other.getClass().equals(getClass())) {
            return getId().equals(other.getId());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }

    abstract public TID getId();
}
