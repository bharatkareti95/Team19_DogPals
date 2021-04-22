package com.dogpals.forum.domain;

import com.dogpals.forum.domain.enumeration.LikeDisLike;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rating.class)
public abstract class Rating_ {

	public static volatile SingularAttribute<Rating, Post> post;
	public static volatile SingularAttribute<Rating, LikeDisLike> likeOrDislike;
	public static volatile SingularAttribute<Rating, Long> relatedPostId;
	public static volatile SingularAttribute<Rating, Long> id;

	public static final String POST = "post";
	public static final String LIKE_OR_DISLIKE = "likeOrDislike";
	public static final String RELATED_POST_ID = "relatedPostId";
	public static final String ID = "id";

}

