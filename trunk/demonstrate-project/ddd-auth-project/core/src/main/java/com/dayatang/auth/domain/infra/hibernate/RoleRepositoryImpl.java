package com.dayatang.auth.domain.infra.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dayatang.auth.domain.FunctionEntity;
import com.dayatang.auth.domain.Role;
import com.dayatang.auth.domain.RoleRepository;
import com.dayatang.spring.repository.BaseEntityRepositoryHibernateSpring;

@Repository(value = "roleRepository")
public class RoleRepositoryImpl extends
		BaseEntityRepositoryHibernateSpring<Role, Long> implements
		RoleRepository {

	public RoleRepositoryImpl() {
		super(Role.class);
	}

	@SuppressWarnings("unchecked")
	public List<Role> queryRoleByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<Role> queryRoleByFunctionEntity(final FunctionEntity fe) {

		//	    DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		//	    criteria.createAlias("functionEntities", "fs");
		//	    criteria.add(Restrictions.eq("fs.id", fe.getId()));

		final String hql = "select r from Role r join r.functionEntities f where f.id=:feId";
		List<Role> roles = getHibernateTemplate().executeFind(
				new HibernateCallback<Object>() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createQuery(hql).setLong("feId",
								fe.getId()).list();
					}

				});
		return roles;
	}
}
