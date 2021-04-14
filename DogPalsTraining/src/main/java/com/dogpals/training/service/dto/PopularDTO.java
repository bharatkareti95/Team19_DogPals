package com.dogpals.training.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.dogpals.training.domain.enumeration.LikeDisLike;

/**
 * A DTO for the {@link com.dogpals.training.domain.Popular} entity.
 */
public class PopularDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LikeDisLike likeOrDislike;

    @NotNull
    private Long userId;


    private Long trainingId;
    
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PopularDTO)) {
            return false;
        }

        return id != null && id.equals(((PopularDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PopularDTO{" +
            "id=" + getId() +
            ", likeOrDislike='" + getLikeOrDislike() + "'" +
            ", userId=" + getUserId() +
            ", trainingId=" + getTrainingId() +
            "}";
    }
}
