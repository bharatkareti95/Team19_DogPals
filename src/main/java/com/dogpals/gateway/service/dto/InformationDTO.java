package com.dogpals.gateway.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.dogpals.gateway.domain.Information} entity.
 */
public class InformationDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 50)
    private String title;

    @NotNull
    private String catagory;

    @NotNull
    private LocalDate dateposted;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public LocalDate getDateposted() {
        return dateposted;
    }

    public void setDateposted(LocalDate dateposted) {
        this.dateposted = dateposted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformationDTO)) {
            return false;
        }

        return id != null && id.equals(((InformationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformationDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", dateposted='" + getDateposted() + "'" +
            "}";
    }
}
