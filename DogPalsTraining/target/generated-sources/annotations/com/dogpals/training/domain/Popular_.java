package com.dogpals.training.domain;

import com.dogpals.training.domain.enumeration.LikeDisLike;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Popular.class)
public abstract class Popular_ {

	public static volatile SingularAttribute<Popular, LikeDisLike> likeOrDislike;
	public static volatile SingularAttribute<Popular, Training> training;
	public static volatile SingularAttribute<Popular, Long> id;
	public static volatile SingularAttribute<Popular, Long> userId;

	public static final String LIKE_OR_DISLIKE = "likeOrDislike";
	public static final String TRAINING = "training";
	public static final String ID = "id";
	public static final String USER_ID = "userId";

}

