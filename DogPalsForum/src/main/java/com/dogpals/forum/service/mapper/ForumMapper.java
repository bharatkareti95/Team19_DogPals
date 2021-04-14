package com.dogpals.forum.service.mapper;


import com.dogpals.forum.domain.*;
import com.dogpals.forum.service.dto.ForumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Forum} and its DTO {@link ForumDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ForumMapper extends EntityMapper<ForumDTO, Forum> {


    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "removePost", ignore = true)
    Forum toEntity(ForumDTO forumDTO);

    default Forum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Forum forum = new Forum();
        forum.setId(id);
        return forum;
    }
}
