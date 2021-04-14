package com.dogpals.training.repository.search;

import com.dogpals.training.domain.Booking;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Booking} entity.
 */
public interface BookingSearchRepository extends ElasticsearchRepository<Booking, Long> {
}
