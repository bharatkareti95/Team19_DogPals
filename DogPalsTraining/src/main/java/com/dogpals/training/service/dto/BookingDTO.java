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
    private Long price;

    @NotNull
    private BookStatus status;


    private Long trainingId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
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
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", trainingId=" + getTrainingId() +
            "}";
    }
}
