package com.dogpals.gateway.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Information.class)
public abstract class Information_ {

	public static volatile SingularAttribute<Information, Instant> date;
	public static volatile SingularAttribute<Information, Long> id;
	public static volatile SingularAttribute<Information, String> title;
	public static volatile SingularAttribute<Information, String> content;

	public static final String DATE = "date";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

}

