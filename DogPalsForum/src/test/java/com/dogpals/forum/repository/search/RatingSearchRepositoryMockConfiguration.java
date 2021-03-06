package com.dogpals.forum.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RatingSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RatingSearchRepositoryMockConfiguration {

    @MockBean
    private RatingSearchRepository mockRatingSearchRepository;

}
