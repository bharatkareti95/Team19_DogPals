package com.dogpals.training.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "training")
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @Size(max = 2000)
    @Column(name = "details", length = 2000)
    private String details;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    @NotNull
    @Column(name = "agency", nullable = false)
    private String agency;

    @NotNull
    @Column(name = "booking_status", nullable = false)
    private String bookingStatus;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "slot")
    private Integer slot;

    @NotNull
    @Column(name = "popularity", nullable = false)
    private Float popularity;

    @OneToMany(mappedBy = "training")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Popular> populars = new HashSet<>();

    @OneToMany(mappedBy = "training")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Booking> bookings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Training title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getDate() {
        return date;
    }

    public Training date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public Training details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public Training location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getPrice() {
        return price;
    }

    public Training price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getAgency() {
        return agency;
    }

    public Training agency(String agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public Training bookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
        return this;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Training startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Training endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getSlot() {
        return slot;
    }

    public Training slot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Float getPopularity() {
        return popularity;
    }

    public Training popularity(Float popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public Set<Popular> getPopulars() {
        return populars;
    }

    public Training populars(Set<Popular> populars) {
        this.populars = populars;
        return this;
    }

    public Training addPopular(Popular popular) {
        this.populars.add(popular);
        popular.setTraining(this);
        return this;
    }

    public Training removePopular(Popular popular) {
        this.populars.remove(popular);
        popular.setTraining(null);
        return this;
    }

    public void setPopulars(Set<Popular> populars) {
        this.populars = populars;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Training bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Training addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setTraining(this);
        return this;
    }

    public Training removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setTraining(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", details='" + getDetails() + "'" +
            ", location='" + getLocation() + "'" +
            ", price=" + getPrice() +
            ", agency='" + getAgency() + "'" +
            ", bookingStatus='" + getBookingStatus() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", slot=" + getSlot() +
            ", popularity=" + getPopularity() +
            "}";
    }
}
