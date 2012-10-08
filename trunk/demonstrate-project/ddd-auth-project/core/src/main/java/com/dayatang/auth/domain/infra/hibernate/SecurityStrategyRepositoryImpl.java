package com.dayatang.auth.domain.infra.hibernate;

import org.springframework.stereotype.Repository;

import com.dayatang.auth.domain.SecurityStrategy;
import com.dayatang.auth.domain.SecurityStrategyRepository;
import com.dayatang.spring.repository.BaseEntityRepositoryHibernateSpring;

@Repository
public class SecurityStrategyRepositoryImpl extends
		BaseEntityRepositoryHibernateSpring<SecurityStrategy, Long> implements
		SecurityStrategyRepository {

	public SecurityStrategyRepositoryImpl() {
		super(SecurityStrategy.class);
	}

}
