package com.dogpals.training.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Training.class)
public abstract class Training_ {

	public static volatile SingularAttribute<Training, Instant> date;
	public static volatile SetAttribute<Training, Popular> populars;
	public static volatile SingularAttribute<Training, String> agency;
	public static volatile SingularAttribute<Training, String> title;
	public static volatile SingularAttribute<Training, Integer> capacity;
	public static volatile SingularAttribute<Training, Long> price;
	public static volatile SingularAttribute<Training, Float> popularity;
	public static volatile SingularAttribute<Training, String> details;
	public static volatile SingularAttribute<Training, String> location;
	public static volatile SingularAttribute<Training, Instant> startTime;
	public static volatile SingularAttribute<Training, Long> id;
	public static volatile SingularAttribute<Training, Instant> endTime;
	public static volatile SetAttribute<Training, Booking> bookings;

	public static final String DATE = "date";
	public static final String POPULARS = "populars";
	public static final String AGENCY = "agency";
	public static final String TITLE = "title";
	public static final String CAPACITY = "capacity";
	public static final String PRICE = "price";
	public static final String POPULARITY = "popularity";
	public static final String DETAILS = "details";
	public static final String LOCATION = "location";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String BOOKINGS = "bookings";

}

