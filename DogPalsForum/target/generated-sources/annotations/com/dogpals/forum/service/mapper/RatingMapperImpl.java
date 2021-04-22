package com.dogpals.forum.service.mapper;

import com.dogpals.forum.domain.Post;
import com.dogpals.forum.domain.Rating;
import com.dogpals.forum.service.dto.RatingDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T17:35:37+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class RatingMapperImpl implements RatingMapper {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Rating> toEntity(List<RatingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Rating> list = new ArrayList<Rating>( dtoList.size() );
        for ( RatingDTO ratingDTO : dtoList ) {
            list.add( toEntity( ratingDTO ) );
        }

        return list;
    }

    @Override
    public List<RatingDTO> toDto(List<Rating> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RatingDTO> list = new ArrayList<RatingDTO>( entityList.size() );
        for ( Rating rating : entityList ) {
            list.add( toDto( rating ) );
        }

        return list;
    }

    @Override
    public RatingDTO toDto(Rating rating) {
        if ( rating == null ) {
            return null;
        }

        RatingDTO ratingDTO = new RatingDTO();

        ratingDTO.setPostId( ratingPostId( rating ) );
        ratingDTO.setId( rating.getId() );
        ratingDTO.setLikeOrDislike( rating.getLikeOrDislike() );
        ratingDTO.setRelatedPostId( rating.getRelatedPostId() );

        return ratingDTO;
    }

    @Override
    public Rating toEntity(RatingDTO ratingDTO) {
        if ( ratingDTO == null ) {
            return null;
        }

        Rating rating = new Rating();

        rating.setPost( postMapper.fromId( ratingDTO.getPostId() ) );
        rating.setId( ratingDTO.getId() );
        rating.setLikeOrDislike( ratingDTO.getLikeOrDislike() );
        rating.setRelatedPostId( ratingDTO.getRelatedPostId() );

        return rating;
    }

    private Long ratingPostId(Rating rating) {
        if ( rating == null ) {
            return null;
        }
        Post post = rating.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
