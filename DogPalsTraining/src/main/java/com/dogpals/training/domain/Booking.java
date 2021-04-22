package com.dogpals.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.dogpals.training.domain.enumeration.BookStatus;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private Training training;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Booking status(BookStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public Booking userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Training getTraining() {
        return training;
    }

    public Booking training(Training training) {
        this.training = training;
        return this;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
