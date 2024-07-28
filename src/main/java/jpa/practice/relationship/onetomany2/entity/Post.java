package jpa.practice.relationship.onetomany2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jpa.practice.relationship.manytomany.entity.Author;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
public class Post implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "post"
    ,cascade = {PERSIST, MERGE}
    ,fetch = LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post"
    ,cascade = {PERSIST, MERGE}
    ,fetch = LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post"
    ,cascade = {PERSIST, MERGE}
    ,fetch = LAZY)
    private List<Like> likes = new ArrayList<>();

    protected Post() {}

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //helper
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.removePost();
    }

    public void removeComments() {
        comments.forEach(Comment::removePost);
        comments.clear();
    }

    public void addImage(Image image) {
        images.add(image);
        image.setPost(this);
    }

    public void removeImage(Image image) {
        images.remove(image);
        image.removePost();
    }

    public void removeImages() {
        images.forEach(Image::removePost);
        images.clear();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Image> getImages() {
        return images;
    }

    public void addLike(Like like1) {
        likes.add(like1);
        like1.setPost(this);
    }

    public void removeLike(Like like1) {
        likes.remove(like1);
        like1.removePost();
    }

    public List<Like> getLikes() {
        return likes;
    }



    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if(!(obj instanceof Post other)) return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comments=" + comments +
                ", images=" + images +
                '}';
    }


}
