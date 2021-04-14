package com.dogpals.forum.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.dogpals.forum.domain.enumeration.ListTopic;

/**
 * A DTO for the {@link com.dogpals.forum.domain.Forum} entity.
 */
public class ForumDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ListTopic topic;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListTopic getTopic() {
        return topic;
    }

    public void setTopic(ListTopic topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ForumDTO)) {
            return false;
        }

        return id != null && id.equals(((ForumDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ForumDTO{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            "}";
    }
}
