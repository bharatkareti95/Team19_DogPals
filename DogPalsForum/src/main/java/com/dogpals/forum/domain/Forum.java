package com.dogpals.forum.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.dogpals.forum.domain.enumeration.ListTopic;

/**
 * A Forum.
 */
@Entity
@Table(name = "forum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "forum")
public class Forum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "topic", nullable = false)
    private ListTopic topic;

    @OneToMany(mappedBy = "forum")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListTopic getTopic() {
        return topic;
    }

    public Forum topic(ListTopic topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(ListTopic topic) {
        this.topic = topic;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Forum posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Forum addPost(Post post) {
        this.posts.add(post);
        post.setForum(this);
        return this;
    }

    public Forum removePost(Post post) {
        this.posts.remove(post);
        post.setForum(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Forum)) {
            return false;
        }
        return id != null && id.equals(((Forum) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Forum{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            "}";
    }
}
