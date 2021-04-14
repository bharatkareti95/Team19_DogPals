package com.dogpals.forum.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dogpals.forum.web.rest.TestUtil;

public class ForumDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForumDTO.class);
        ForumDTO forumDTO1 = new ForumDTO();
        forumDTO1.setId(1L);
        ForumDTO forumDTO2 = new ForumDTO();
        assertThat(forumDTO1).isNotEqualTo(forumDTO2);
        forumDTO2.setId(forumDTO1.getId());
        assertThat(forumDTO1).isEqualTo(forumDTO2);
        forumDTO2.setId(2L);
        assertThat(forumDTO1).isNotEqualTo(forumDTO2);
        forumDTO1.setId(null);
        assertThat(forumDTO1).isNotEqualTo(forumDTO2);
    }
}
