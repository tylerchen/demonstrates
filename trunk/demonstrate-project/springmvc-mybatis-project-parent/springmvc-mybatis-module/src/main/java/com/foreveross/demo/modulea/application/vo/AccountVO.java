/*******************************************************************************
 * Copyright (c) 2012-12-10 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.modulea.application.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-10
 */
@SuppressWarnings("serial")
public class AccountVO implements Serializable {
	private String id;
	private String username;
	private String password;
	private String realName;
	private String email;
	private Date registryDate;
	private Boolean enabled;
	private Date passwordLastUpdateDate;
	private String description;

	public AccountVO() {
	}

	public AccountVO(String id, String username, String password,
			String realName, String email, Date registryDate, Boolean enabled,
			Date passwordLastUpdateDate, String description) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.email = email;
		this.registryDate = registryDate;
		this.enabled = enabled;
		this.passwordLastUpdateDate = passwordLastUpdateDate;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getPasswordLastUpdateDate() {
		return passwordLastUpdateDate;
	}

	public void setPasswordLastUpdateDate(Date passwordLastUpdateDate) {
		this.passwordLastUpdateDate = passwordLastUpdateDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE).toString();
	}
}
