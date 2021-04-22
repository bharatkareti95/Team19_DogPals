package com.dogpals.training.repository;

import com.dogpals.training.domain.Booking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId ( Integer userId );
}
