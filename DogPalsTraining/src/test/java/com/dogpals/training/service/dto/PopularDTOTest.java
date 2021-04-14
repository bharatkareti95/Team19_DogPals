package com.dogpals.training.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dogpals.training.web.rest.TestUtil;

public class PopularDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopularDTO.class);
        PopularDTO popularDTO1 = new PopularDTO();
        popularDTO1.setId(1L);
        PopularDTO popularDTO2 = new PopularDTO();
        assertThat(popularDTO1).isNotEqualTo(popularDTO2);
        popularDTO2.setId(popularDTO1.getId());
        assertThat(popularDTO1).isEqualTo(popularDTO2);
        popularDTO2.setId(2L);
        assertThat(popularDTO1).isNotEqualTo(popularDTO2);
        popularDTO1.setId(null);
        assertThat(popularDTO1).isNotEqualTo(popularDTO2);
    }
}
