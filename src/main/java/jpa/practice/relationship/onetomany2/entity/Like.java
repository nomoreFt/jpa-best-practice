package jpa.practice.relationship.onetomany2.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "likes")
public class Like implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private int count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Like() {}

    public Like(int count) {
        this.count = count;
    }

    //helper
    public void setPost(Post post) {
        this.post = post;
    }

    public void removePost() {
        this.post = null;
    }

    public Long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if(!(obj instanceof Like other)) return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }

}
