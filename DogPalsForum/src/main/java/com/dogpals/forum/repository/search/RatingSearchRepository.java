package com.dogpals.forum.repository.search;

import com.dogpals.forum.domain.Rating;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Rating} entity.
 */
public interface RatingSearchRepository extends ElasticsearchRepository<Rating, Long> {
}
