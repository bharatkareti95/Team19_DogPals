package com.dogpals.forum.repository;

import com.dogpals.forum.domain.Post;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data  repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
        List<Post> findByUserId ( Integer userId );
}
