package com.dogpals.forum.service.mapper;


import com.dogpals.forum.domain.*;
import com.dogpals.forum.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {

    @Mapping(source = "post.id", target = "postId")
    RatingDTO toDto(Rating rating);

    @Mapping(source = "postId", target = "post")
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
