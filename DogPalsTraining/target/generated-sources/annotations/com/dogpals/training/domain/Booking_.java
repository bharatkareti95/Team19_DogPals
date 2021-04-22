package com.dogpals.training.domain;

import com.dogpals.training.domain.enumeration.BookStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ {

	public static volatile SingularAttribute<Booking, Training> training;
	public static volatile SingularAttribute<Booking, Long> id;
	public static volatile SingularAttribute<Booking, Integer> userId;
	public static volatile SingularAttribute<Booking, BookStatus> status;

	public static final String TRAINING = "training";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String STATUS = "status";

}

