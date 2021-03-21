package com.dogpals.gateway.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Information.class)
public abstract class Information_ {

	public static volatile SingularAttribute<Information, String> catagory;
	public static volatile SingularAttribute<Information, LocalDate> dateposted;
	public static volatile SingularAttribute<Information, Long> id;
	public static volatile SingularAttribute<Information, String> title;

	public static final String CATAGORY = "catagory";
	public static final String DATEPOSTED = "dateposted";
	public static final String ID = "id";
	public static final String TITLE = "title";

}

