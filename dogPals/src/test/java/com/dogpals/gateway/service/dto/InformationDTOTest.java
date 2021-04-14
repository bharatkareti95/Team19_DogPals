package com.dogpals.gateway.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dogpals.gateway.web.rest.TestUtil;

public class InformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformationDTO.class);
        InformationDTO informationDTO1 = new InformationDTO();
        informationDTO1.setId(1L);
        InformationDTO informationDTO2 = new InformationDTO();
        assertThat(informationDTO1).isNotEqualTo(informationDTO2);
        informationDTO2.setId(informationDTO1.getId());
        assertThat(informationDTO1).isEqualTo(informationDTO2);
        informationDTO2.setId(2L);
        assertThat(informationDTO1).isNotEqualTo(informationDTO2);
        informationDTO1.setId(null);
        assertThat(informationDTO1).isNotEqualTo(informationDTO2);
    }
}
