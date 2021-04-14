package com.dogpals.training.repository;

import com.dogpals.training.domain.Popular;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Popular entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PopularRepository extends JpaRepository<Popular, Long> {
}
