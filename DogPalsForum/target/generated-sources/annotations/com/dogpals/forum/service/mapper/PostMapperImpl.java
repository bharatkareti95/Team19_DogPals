package com.dogpals.forum.service.mapper;

import com.dogpals.forum.domain.Forum;
import com.dogpals.forum.domain.Post;
import com.dogpals.forum.service.dto.PostDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T17:35:38+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Autowired
    private ForumMapper forumMapper;

    @Override
    public List<Post> toEntity(List<PostDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Post> list = new ArrayList<Post>( dtoList.size() );
        for ( PostDTO postDTO : dtoList ) {
            list.add( toEntity( postDTO ) );
        }

        return list;
    }

    @Override
    public List<PostDTO> toDto(List<Post> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( entityList.size() );
        for ( Post post : entityList ) {
            list.add( toDto( post ) );
        }

        return list;
    }

    @Override
    public PostDTO toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setForumId( postForumId( post ) );
        postDTO.setId( post.getId() );
        postDTO.setTitle( post.getTitle() );
        postDTO.setContent( post.getContent() );
        postDTO.setDate( post.getDate() );

        return postDTO;
    }

    @Override
    public Post toEntity(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setForum( forumMapper.fromId( postDTO.getForumId() ) );
        post.setId( postDTO.getId() );
        post.setTitle( postDTO.getTitle() );
        post.setContent( postDTO.getContent() );
        post.setDate( postDTO.getDate() );

        return post;
    }

    private Long postForumId(Post post) {
        if ( post == null ) {
            return null;
        }
        Forum forum = post.getForum();
        if ( forum == null ) {
            return null;
        }
        Long id = forum.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
