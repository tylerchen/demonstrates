package com.dayatang.auth.domain;

import com.dayatang.domain.BaseEntityRepository;

public interface FunctionEntityTypeRepository extends BaseEntityRepository<FunctionEntityType, Long>{

	/**
	 * 根据实例查询
	 * @param fType
	 * @return
	 */
	public FunctionEntityType findbyExample(FunctionEntityType fType);
}
