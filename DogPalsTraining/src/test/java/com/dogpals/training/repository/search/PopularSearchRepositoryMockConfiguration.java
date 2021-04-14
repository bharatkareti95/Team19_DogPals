package com.dogpals.training.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PopularSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PopularSearchRepositoryMockConfiguration {

    @MockBean
    private PopularSearchRepository mockPopularSearchRepository;

}
