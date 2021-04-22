package com.dogpals.forum.domain;

import com.dogpals.forum.domain.enumeration.ListTopic;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Forum.class)
public abstract class Forum_ {

	public static volatile SingularAttribute<Forum, ListTopic> topic;
	public static volatile SingularAttribute<Forum, Long> id;
	public static volatile SetAttribute<Forum, Post> posts;

	public static final String TOPIC = "topic";
	public static final String ID = "id";
	public static final String POSTS = "posts";

}

