package com.dayatang.auth.domain;

import java.util.List;

import com.dayatang.domain.BaseEntityRepository;

public interface FunctionEntityRepository extends BaseEntityRepository<FunctionEntity, Long> {
	
	/**
	 * 查询同一父节点下所有功能实体
	 * @param parentId
	 * @return
	 */
	List<FunctionEntity> findByParentId(Long parentId);
	
	
	/**
	 * 根据功能实体类型查询功能实体列表
	 * @param type
	 * @return
	 */
	public List<FunctionEntity> getFunctionEntityByType(FunctionEntityType type);

}