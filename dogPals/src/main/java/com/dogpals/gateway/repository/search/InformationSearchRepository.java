package com.dogpals.gateway.repository.search;

import com.dogpals.gateway.domain.Information;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Information} entity.
 */
public interface InformationSearchRepository extends ElasticsearchRepository<Information, Long> {
}
