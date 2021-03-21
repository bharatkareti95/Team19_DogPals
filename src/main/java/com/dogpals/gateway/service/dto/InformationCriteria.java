package com.dogpals.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.dogpals.gateway.domain.Information} entity. This class is used
 * in {@link com.dogpals.gateway.web.rest.InformationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /information?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InformationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter catagory;

    private LocalDateFilter dateposted;

    public InformationCriteria() {
    }

    public InformationCriteria(InformationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.catagory = other.catagory == null ? null : other.catagory.copy();
        this.dateposted = other.dateposted == null ? null : other.dateposted.copy();
    }

    @Override
    public InformationCriteria copy() {
        return new InformationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getCatagory() {
        return catagory;
    }

    public void setCatagory(StringFilter catagory) {
        this.catagory = catagory;
    }

    public LocalDateFilter getDateposted() {
        return dateposted;
    }

    public void setDateposted(LocalDateFilter dateposted) {
        this.dateposted = dateposted;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InformationCriteria that = (InformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(catagory, that.catagory) &&
            Objects.equals(dateposted, that.dateposted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        catagory,
        dateposted
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (catagory != null ? "catagory=" + catagory + ", " : "") +
                (dateposted != null ? "dateposted=" + dateposted + ", " : "") +
            "}";
    }

}
