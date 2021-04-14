package com.dogpals.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.dogpals.training.domain.enumeration.LikeDisLike;

/**
 * A Popular.
 */
@Entity
@Table(name = "popular")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "popular")
public class Popular implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "like_or_dislike", nullable = false)
    private LikeDisLike likeOrDislike;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JsonIgnoreProperties(value = "populars", allowSetters = true)
    private Training training;

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

    public Popular likeOrDislike(LikeDisLike likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
        return this;
    }

    public void setLikeOrDislike(LikeDisLike likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
    }

    public Long getUserId() {
        return userId;
    }

    public Popular userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Training getTraining() {
        return training;
    }

    public Popular training(Training training) {
        this.training = training;
        return this;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Popular)) {
            return false;
        }
        return id != null && id.equals(((Popular) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Popular{" +
            "id=" + getId() +
            ", likeOrDislike='" + getLikeOrDislike() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
