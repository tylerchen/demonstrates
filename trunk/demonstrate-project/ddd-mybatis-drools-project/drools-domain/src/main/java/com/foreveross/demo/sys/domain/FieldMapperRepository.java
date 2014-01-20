/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
public interface FieldMapperRepository {
	FieldMapper getFieldMapper(String id);

	void insertFieldMapper(FieldMapper fieldMapper);

	void updateFieldMapper(FieldMapper fieldMapper);

	void deleteFieldMapper(String id);

	void deleteFieldMapperByFieldName(String fieldName);

	List<FieldMapper> pageFindFieldMapper(@Param(value = "page") Page page,
			@Param(value = "fieldName") String fieldName);

	List<FieldMapper> pageFindFieldMapper2(Map<String, Object> params);

	List<FieldMapper> findAllFieldMapper();
	
	void removeFieldMapperByFieldName(String fieldName);
}
