package com.dogpals.training.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PopularMapperTest {

    private PopularMapper popularMapper;

    @BeforeEach
    public void setUp() {
        popularMapper = new PopularMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(popularMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(popularMapper.fromId(null)).isNull();
    }
}
