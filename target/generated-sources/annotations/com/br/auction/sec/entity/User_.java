package com.br.auction.sec.entity;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> privateKey;
	public static volatile SingularAttribute<User, String> cpf;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> publicKey;

	public static final String PRIVATE_KEY = "privateKey";
	public static final String CPF = "cpf";
	public static final String ID = "id";
	public static final String PUBLIC_KEY = "publicKey";

}

