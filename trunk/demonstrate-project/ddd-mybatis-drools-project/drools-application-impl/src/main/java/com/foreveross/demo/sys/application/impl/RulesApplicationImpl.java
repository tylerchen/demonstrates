/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.foreveross.demo.sys.application.RulesApplication;
import com.foreveross.demo.sys.application.vo.FieldMapperVO;
import com.foreveross.demo.sys.application.vo.RulesVO;
import com.foreveross.demo.sys.domain.FieldMapper;
import com.foreveross.demo.sys.domain.Rules;
import com.foreveross.util.BeanHelper;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
@SuppressWarnings("unchecked")
@Named("rulesApplication")
public class RulesApplicationImpl implements RulesApplication {

	@Inject
	private RuleBaseCache ruleBaseCache;

	public RulesApplicationImpl() {
	}

	public void testRules(String ruleJson) {
		DrlTestHelper.testRule(ruleJson);
	}

	public Object testExecuteRules(String ruleJson, List data) {
		return DrlTestHelper.testExecuteRules(ruleJson, data);
	}

	public void saveRules(String ruleJson) {
		Rules rules = saveRules2(ruleJson);
		ruleBaseCache.addRules(rules);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected Rules saveRules2(String ruleJson) {
		Rules rules = DrlParserHelper.parse(ruleJson);
		rules.save();
		return rules;
	}

	public void removeRules(String rulesId) {
		Rules rules = removeRules2(rulesId);
		ruleBaseCache.removeRules(rules);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected Rules removeRules2(String rulesId) {
		Rules rules = Rules.get(rulesId);
		rules.remove();
		return rules;
	}

	@Transactional(readOnly = true)
	public Object executeRules(List data) {
		return ruleBaseCache.executeRules(data);
	}

	@Override
	public Page pageFindRules(Page page, String rulesId, String ruleSetKey) {
		Page rules = Rules.pageFindRules(page, rulesId, ruleSetKey);
		return rules.toPage(RulesVO.class);
	}

	@Override
	public Page pageFindFieldMapper(Page page, String fieldName) {
		return FieldMapper.pageFindFieldMapper(page, fieldName);
	}

	@Override
	public void removeFieldMapper(String fieldMapperId) {
		FieldMapper.remove(fieldMapperId);
	}

	@Override
	public void removeFieldMapperByFieldName(String fieldName) {
		FieldMapper.removeFieldMapperByFieldName(fieldName);
	}

	@Override
	public FieldMapperVO saveFieldFieldMapper(FieldMapperVO vo) {
		FieldMapper fm = new FieldMapper();
		BeanHelper.copyProperties(fm, vo);
		fm.save();
		return (FieldMapperVO) BeanHelper.copyProperties(vo, fm);
	}

}
