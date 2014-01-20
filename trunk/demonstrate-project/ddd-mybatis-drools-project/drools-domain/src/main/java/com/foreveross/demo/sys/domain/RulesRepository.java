/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
public interface RulesRepository {

	Rules getRules(String id);

	void insertRules(Rules rules);

	void updateRules(Rules rules);

	void deleteRules(String id);

	void deleteRulesByUniqueKey(String uniqueKey);

	List<Rules> pageFindRules(@Param(value = "page") Page page,
			@Param(value = "rulesId") String rulesId,
			@Param(value = "ruleSetKey") String ruleSetKey);

	List<Rules> pageFindRules2(Map<String, Object> params);

	List<Rules> findRulesByUpdateTime(Date updateTime);

	Long getMaxUpdateMark();

	List<Rules> findRulesByUpdateMark(long updateMark);
}
