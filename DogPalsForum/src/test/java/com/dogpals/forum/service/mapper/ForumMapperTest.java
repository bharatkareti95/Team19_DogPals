package com.dogpals.forum.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ForumMapperTest {

    private ForumMapper forumMapper;

    @BeforeEach
    public void setUp() {
        forumMapper = new ForumMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(forumMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(forumMapper.fromId(null)).isNull();
    }
}
