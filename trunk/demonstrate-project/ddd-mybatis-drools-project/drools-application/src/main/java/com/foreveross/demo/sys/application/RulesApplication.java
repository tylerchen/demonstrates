/*******************************************************************************
 * Copyright (c) 2013-3-3 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application;

import java.util.List;

import com.foreveross.demo.sys.application.vo.FieldMapperVO;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-3
 */
@SuppressWarnings("unchecked")
public interface RulesApplication {

	void testRules(String ruleJson);

	Object testExecuteRules(String ruleJson, List data);

	void saveRules(String ruleJson);

	void removeRules(String rulesId);

	Object executeRules(List data);

	Page pageFindRules(Page page, String rulesId, String ruleSetKey);

	FieldMapperVO saveFieldFieldMapper(FieldMapperVO vo);

	void removeFieldMapper(String fieldMapperId);

	void removeFieldMapperByFieldName(String fieldName);

	Page pageFindFieldMapper(Page page, String fieldName);
}
