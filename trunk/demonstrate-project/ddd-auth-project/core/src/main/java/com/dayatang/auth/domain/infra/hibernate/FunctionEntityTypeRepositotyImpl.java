package com.dayatang.auth.domain.infra.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dayatang.auth.domain.FunctionEntityType;
import com.dayatang.auth.domain.FunctionEntityTypeRepository;
import com.dayatang.spring.repository.BaseEntityRepositoryHibernateSpring;
@Repository(value="functionEntityTypeRepositoty")
public class FunctionEntityTypeRepositotyImpl extends BaseEntityRepositoryHibernateSpring<FunctionEntityType, Long> implements
		FunctionEntityTypeRepository {

	public FunctionEntityTypeRepositotyImpl() {
		super(FunctionEntityType.class);
	}
	
	

	@SuppressWarnings("unchecked")
	public FunctionEntityType findbyExample(FunctionEntityType fType) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(FunctionEntityType.class);
		
		if(!fType.isNew())
			criteria.add(Restrictions.ne("id", fType.getId()));
		criteria.add(Restrictions.eq("name", fType.getName()));
		
		List list = getHibernateTemplate().findByCriteria(criteria);
		return (FunctionEntityType) ((list!=null && list.size()>0)?list.iterator().next():null);
	}

}
