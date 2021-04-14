package com.dogpals.forum.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.dogpals.forum.domain.enumeration.LikeDisLike;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rating")
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "like_or_dislike", nullable = false)
    private LikeDisLike likeOrDislike;

    @NotNull
    @Column(name = "related_post_id", nullable = false)
    private Long relatedPostId;

    @ManyToOne
    @JsonIgnoreProperties(value = "ratings", allowSetters = true)
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LikeDisLike getLikeOrDislike() {
        return likeOrDislike;
    }

    public Rating likeOrDislike(LikeDisLike likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
        return this;
    }

    public void setLikeOrDislike(LikeDisLike likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
    }

    public Long getRelatedPostId() {
        return relatedPostId;
    }

    public Rating relatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
        return this;
    }

    public void setRelatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
    }

    public Post getPost() {
        return post;
    }

    public Rating post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rating)) {
            return false;
        }
        return id != null && id.equals(((Rating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", likeOrDislike='" + getLikeOrDislike() + "'" +
            ", relatedPostId=" + getRelatedPostId() +
            "}";
    }
}
