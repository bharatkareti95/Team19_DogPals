package com.dogpals.forum.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, Instant> date;
	public static volatile SingularAttribute<Comment, Post> post;
	public static volatile SingularAttribute<Comment, Long> relatePostId;
	public static volatile SingularAttribute<Comment, Long> id;
	public static volatile SingularAttribute<Comment, String> title;
	public static volatile SingularAttribute<Comment, String> content;

	public static final String DATE = "date";
	public static final String POST = "post";
	public static final String RELATE_POST_ID = "relatePostId";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

}

