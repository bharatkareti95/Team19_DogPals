package com.dogpals.training.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.dogpals.training.domain.enumeration.BookStatus;

/**
 * A DTO for the {@link com.dogpals.training.domain.Booking} entity.
 */
public class BookingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private BookStatus status;

    @NotNull
    private Integer userId;


    private Long trainingId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingDTO)) {
            return false;
        }

        return id != null && id.equals(((BookingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", userId=" + getUserId() +
            ", trainingId=" + getTrainingId() +
            "}";
    }
}
