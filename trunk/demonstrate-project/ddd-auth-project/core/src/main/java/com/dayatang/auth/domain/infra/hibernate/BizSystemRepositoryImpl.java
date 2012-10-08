package com.dayatang.auth.domain.infra.hibernate;

import org.springframework.stereotype.Repository;

import com.dayatang.auth.domain.BizSystem;
import com.dayatang.auth.domain.BizSystemRepository;
import com.dayatang.spring.repository.BaseEntityRepositoryHibernateSpring;

/**
 * 系统仓储实现
 * @author Justin
 *
 */
@Repository("bizSystemRepository")
public class BizSystemRepositoryImpl extends BaseEntityRepositoryHibernateSpring<BizSystem, Long> implements BizSystemRepository{

	
	public BizSystemRepositoryImpl() {
		super(BizSystem.class);
	}

}
