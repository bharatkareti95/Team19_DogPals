package com.dogpals.forum.repository.search;

import com.dogpals.forum.domain.Forum;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Forum} entity.
 */
public interface ForumSearchRepository extends ElasticsearchRepository<Forum, Long> {
}
