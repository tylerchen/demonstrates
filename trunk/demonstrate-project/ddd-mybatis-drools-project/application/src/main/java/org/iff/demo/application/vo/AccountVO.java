package org.iff.demo.application.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountVO implements Serializable {

	private String accountId;
	private String username;
	private String password;

	public AccountVO() {
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
