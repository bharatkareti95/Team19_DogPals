package com.dogpals.forum.service.mapper;

import com.dogpals.forum.domain.Forum;
import com.dogpals.forum.service.dto.ForumDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T17:35:38+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class ForumMapperImpl implements ForumMapper {

    @Override
    public ForumDTO toDto(Forum entity) {
        if ( entity == null ) {
            return null;
        }

        ForumDTO forumDTO = new ForumDTO();

        forumDTO.setId( entity.getId() );
        forumDTO.setTopic( entity.getTopic() );

        return forumDTO;
    }

    @Override
    public List<Forum> toEntity(List<ForumDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Forum> list = new ArrayList<Forum>( dtoList.size() );
        for ( ForumDTO forumDTO : dtoList ) {
            list.add( toEntity( forumDTO ) );
        }

        return list;
    }

    @Override
    public List<ForumDTO> toDto(List<Forum> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ForumDTO> list = new ArrayList<ForumDTO>( entityList.size() );
        for ( Forum forum : entityList ) {
            list.add( toDto( forum ) );
        }

        return list;
    }

    @Override
    public Forum toEntity(ForumDTO forumDTO) {
        if ( forumDTO == null ) {
            return null;
        }

        Forum forum = new Forum();

        forum.setId( forumDTO.getId() );
        forum.setTopic( forumDTO.getTopic() );

        return forum;
    }
}
