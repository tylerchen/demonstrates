/*******************************************************************************
 * Copyright (c) 2013-3-5 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-5
 */
@SuppressWarnings("serial")
public class RulesVO implements Serializable {
	private String id;
	private String ruleSetKey;
	private Date effectiveDate;
	private Date expiresDate;
	private String rule;
	private String dsl;
	private Date updateTime;
	private long updateMark;
	private String description;

	public RulesVO() {
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

	@Override
	public String toString() {
		return "RulesVO [description=" + description + ", dsl=" + dsl
				+ ", effectiveDate=" + effectiveDate + ", expiresDate="
				+ expiresDate + ", id=" + id + ", rule=" + rule
				+ ", ruleSetKey=" + ruleSetKey + ", updateMark=" + updateMark
				+ ", updateTime=" + updateTime + "]";
	}

}
