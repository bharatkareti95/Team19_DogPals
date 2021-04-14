package com.dogpals.forum.service.mapper;


import com.dogpals.forum.domain.*;
import com.dogpals.forum.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {ForumMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "forum.id", target = "forumId")
    PostDTO toDto(Post post);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "removeRating", ignore = true)
    @Mapping(source = "forumId", target = "forum")
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
