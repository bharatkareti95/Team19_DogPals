package com.dogpals.forum.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.dogpals.forum.domain.enumeration.LikeDisLike;

/**
 * A DTO for the {@link com.dogpals.forum.domain.Rating} entity.
 */
public class RatingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LikeDisLike likeOrDislike;

    @NotNull
    private Long relatedPostId;


    private Long postId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LikeDisLike getLikeOrDislike() {
        return likeOrDislike;
    }

    public void setLikeOrDislike(LikeDisLike likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
    }

    public Long getRelatedPostId() {
        return relatedPostId;
    }

    public void setRelatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingDTO)) {
            return false;
        }

        return id != null && id.equals(((RatingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", likeOrDislike='" + getLikeOrDislike() + "'" +
            ", relatedPostId=" + getRelatedPostId() +
            ", postId=" + getPostId() +
            "}";
    }
}
