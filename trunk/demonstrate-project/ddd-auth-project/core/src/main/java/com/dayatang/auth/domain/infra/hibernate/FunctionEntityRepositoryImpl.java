package com.dayatang.auth.domain.infra.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dayatang.auth.domain.FunctionEntity;
import com.dayatang.auth.domain.FunctionEntityRepository;
import com.dayatang.auth.domain.FunctionEntityType;
import com.dayatang.spring.repository.BaseEntityRepositoryHibernateSpring;

/**
 * 功能实体管理仓储实现
 *<br />
 * @author vakin.Jiang
 *2010-4-19
 */

@Repository
public class FunctionEntityRepositoryImpl extends
		BaseEntityRepositoryHibernateSpring<FunctionEntity, Long> implements
		FunctionEntityRepository {

	public FunctionEntityRepositoryImpl() {
		super(FunctionEntity.class);
	}

	public List<FunctionEntity> findByParentId(Long parentId) {
		return findByNamedQuery("FunctionEntity.findbyParentId",
				new Object[] { parentId });
	}

	@SuppressWarnings("unchecked")
	public List<FunctionEntity> getFunctionEntityByType(FunctionEntityType type) {
		return getSession().createCriteria(FunctionEntity.class).createAlias(
				"functionEntityType", "type").add(
				Restrictions.eq("type.id", type.getId())).list();
	}
}
