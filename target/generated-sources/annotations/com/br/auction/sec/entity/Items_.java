package com.br.auction.sec.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Items.class)
public abstract class Items_ {

	public static volatile SingularAttribute<Items, String> image;
	public static volatile SingularAttribute<Items, String> name;
	public static volatile SingularAttribute<Items, Long> id;
	public static volatile SingularAttribute<Items, String> value;

	public static final String IMAGE = "image";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VALUE = "value";

}

