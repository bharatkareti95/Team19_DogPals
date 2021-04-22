package com.dogpals.forum.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ {

	public static volatile SingularAttribute<Post, Instant> date;
	public static volatile SingularAttribute<Post, Forum> forum;
	public static volatile SetAttribute<Post, Comment> comments;
	public static volatile SetAttribute<Post, Rating> ratings;
	public static volatile SingularAttribute<Post, Long> id;
	public static volatile SingularAttribute<Post, String> title;
	public static volatile SingularAttribute<Post, String> content;

	public static final String DATE = "date";
	public static final String FORUM = "forum";
	public static final String COMMENTS = "comments";
	public static final String RATINGS = "ratings";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

}

