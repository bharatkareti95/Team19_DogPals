package com.dogpals.gateway.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Information.
 */
@Entity
@Table(name = "information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "information")
public class Information implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @NotNull
    @Column(name = "catagory", nullable = false)
    private String catagory;

    @NotNull
    @Column(name = "dateposted", nullable = false)
    private LocalDate dateposted;

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

    public Information title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatagory() {
        return catagory;
    }

    public Information catagory(String catagory) {
        this.catagory = catagory;
        return this;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public LocalDate getDateposted() {
        return dateposted;
    }

    public Information dateposted(LocalDate dateposted) {
        this.dateposted = dateposted;
        return this;
    }

    public void setDateposted(LocalDate dateposted) {
        this.dateposted = dateposted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Information)) {
            return false;
        }
        return id != null && id.equals(((Information) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Information{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", dateposted='" + getDateposted() + "'" +
            "}";
    }
}
