/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
@SuppressWarnings("serial")
public class Rules implements Serializable {

	private String id;
	private String ruleSetKey;
	private Date effectiveDate;
	private Date expiresDate;
	private String rule;
	private String dsl;
	private Date updateTime;
	private long updateMark;
	private String description;

	public Rules() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleSetKey() {
		return ruleSetKey;
	}

	public void setRuleSetKey(String ruleSetKey) {
		this.ruleSetKey = ruleSetKey;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(Date expiresDate) {
		this.expiresDate = expiresDate;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getDsl() {
		return dsl;
	}

	public void setDsl(String dsl) {
		this.dsl = dsl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getUpdateMark() {
		return updateMark;
	}

	public void setUpdateMark(long updateMark) {
		this.updateMark = updateMark;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//
	private static RulesRepository rulesRepository;

	private static RulesRepository getRulesRepository() {
		if (rulesRepository == null) {
			rulesRepository = InstanceFactory
					.getInstance(RulesRepository.class);
		}
		return rulesRepository;
	}

	public static Rules get(String id) {
		return getRulesRepository().getRules(id);
	}

	public static void remove(String id) {
		getRulesRepository().deleteRules(id);
	}

	public void save() {
		setUpdateTime(new Date());
		setUpdateMark(getMaxUpdateMark());
		if (getId() == null) {
			getRulesRepository().insertRules(this);
		} else {
			getRulesRepository().updateRules(this);
		}
	}

	public void remove() {
		getRulesRepository().deleteRules(getId());
	}

	public static List<Rules> findRulesByUpdateTime(Date updateTime) {
		return getRulesRepository().findRulesByUpdateTime(updateTime);
	}

	public static long getMaxUpdateMark() {
		Long updateMark = getRulesRepository().getMaxUpdateMark();
		if (updateMark == null) {
			return 1L;
		}
		return updateMark + 1;
	}

	public static List<Rules> findRulesByUpdateMark(long updateMark) {
		return getRulesRepository().findRulesByUpdateMark(updateMark);
	}

	public static Page pageFindRules(Page page, String rulesId,
			String ruleSetKey) {
		page.setRows(getRulesRepository().pageFindRules(page, rulesId,
				ruleSetKey));
		return page;
	}
}
