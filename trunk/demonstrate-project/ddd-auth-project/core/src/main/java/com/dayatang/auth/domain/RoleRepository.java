package com.dayatang.auth.domain;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.dayatang.domain.BaseEntityRepository;

public interface RoleRepository extends BaseEntityRepository<Role, Long> {

	public List<Role> queryRoleByCriteria(DetachedCriteria criteria);
	
	/**
	 * 根据功能权限查询与之关联的所有角色集合
	 * @param fe
	 * @return
	 */
	public List<Role> queryRoleByFunctionEntity(FunctionEntity fe);
}
