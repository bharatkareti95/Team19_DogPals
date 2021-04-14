package com.dogpals.training.repository.search;

import com.dogpals.training.domain.Training;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Training} entity.
 */
public interface TrainingSearchRepository extends ElasticsearchRepository<Training, Long> {
}
