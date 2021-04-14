package com.dogpals.forum.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ForumSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ForumSearchRepositoryMockConfiguration {

    @MockBean
    private ForumSearchRepository mockForumSearchRepository;

}
