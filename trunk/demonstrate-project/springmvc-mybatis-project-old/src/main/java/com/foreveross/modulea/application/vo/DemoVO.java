package com.foreveross.modulea.application.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Demo")
@SuppressWarnings("serial")
public class DemoVO implements Serializable {

	private String accountId;
	private String username;
	private String password;

	public DemoVO() {
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	@Override
	public String toString() {
		return "AccountVO [accountId=" + accountId + ", password=" + password
				+ ", username=" + username + "]";
	}

}
