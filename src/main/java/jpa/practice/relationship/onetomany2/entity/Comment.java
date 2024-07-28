package jpa.practice.relationship.onetomany2.entity;

import jakarta.persistence.*;

import java.io.Serializable;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Comment() {}

    public Comment(String contents) {
        this.contents = contents;
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

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if(!(obj instanceof Comment other)) return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                '}';
    }

}
