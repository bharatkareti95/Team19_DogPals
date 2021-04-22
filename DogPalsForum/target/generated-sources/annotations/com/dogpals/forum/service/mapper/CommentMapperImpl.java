package com.dogpals.forum.service.mapper;

import com.dogpals.forum.domain.Comment;
import com.dogpals.forum.domain.Post;
import com.dogpals.forum.service.dto.CommentDTO;
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
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Comment> toEntity(List<CommentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Comment> list = new ArrayList<Comment>( dtoList.size() );
        for ( CommentDTO commentDTO : dtoList ) {
            list.add( toEntity( commentDTO ) );
        }

        return list;
    }

    @Override
    public List<CommentDTO> toDto(List<Comment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( entityList.size() );
        for ( Comment comment : entityList ) {
            list.add( toDto( comment ) );
        }

        return list;
    }

    @Override
    public CommentDTO toDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setPostId( commentPostId( comment ) );
        commentDTO.setId( comment.getId() );
        commentDTO.setTitle( comment.getTitle() );
        commentDTO.setContent( comment.getContent() );
        commentDTO.setDate( comment.getDate() );
        commentDTO.setRelatePostId( comment.getRelatePostId() );

        return commentDTO;
    }

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setPost( postMapper.fromId( commentDTO.getPostId() ) );
        comment.setId( commentDTO.getId() );
        comment.setTitle( commentDTO.getTitle() );
        comment.setContent( commentDTO.getContent() );
        comment.setDate( commentDTO.getDate() );
        comment.setRelatePostId( commentDTO.getRelatePostId() );

        return comment;
    }

    private Long commentPostId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Post post = comment.getPost();
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
