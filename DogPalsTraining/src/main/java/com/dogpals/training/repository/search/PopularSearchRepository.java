package com.dogpals.training.repository.search;

import com.dogpals.training.domain.Popular;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Popular} entity.
 */
public interface PopularSearchRepository extends ElasticsearchRepository<Popular, Long> {
}
