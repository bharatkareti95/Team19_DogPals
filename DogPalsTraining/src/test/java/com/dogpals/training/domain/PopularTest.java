package com.dogpals.training.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dogpals.training.web.rest.TestUtil;

public class PopularTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Popular.class);
        Popular popular1 = new Popular();
        popular1.setId(1L);
        Popular popular2 = new Popular();
        popular2.setId(popular1.getId());
        assertThat(popular1).isEqualTo(popular2);
        popular2.setId(2L);
        assertThat(popular1).isNotEqualTo(popular2);
        popular1.setId(null);
        assertThat(popular1).isNotEqualTo(popular2);
    }
}
