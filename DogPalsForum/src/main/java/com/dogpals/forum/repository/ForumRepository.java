package com.dogpals.forum.repository;

import com.dogpals.forum.domain.Forum;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Forum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
}
