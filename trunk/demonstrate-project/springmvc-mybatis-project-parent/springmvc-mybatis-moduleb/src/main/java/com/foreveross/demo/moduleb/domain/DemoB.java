package com.foreveross.demo.moduleb.domain;

import java.io.Serializable;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.moduleb.repository.DemoBRepository;

@SuppressWarnings("serial")
public class DemoB implements Serializable {

	private String accountId;
	private String username;
	private String password;

	public DemoB() {
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

	//
	private static DemoBRepository accountRepository;

	private static DemoBRepository getAccountRepository() {
		if (accountRepository == null) {
			accountRepository = InstanceFactory
					.getInstance(DemoBRepository.class);
		}
		return accountRepository;
	}

	public static DemoB get(String accountId) {
		return getAccountRepository().getAccount(accountId);
	}

	public static DemoB remove(String accountId) {
		return getAccountRepository().getAccount(accountId);
	}

	public void save() {
		if (getAccountId() == null) {
			getAccountRepository().insertAccount(this);
		} else {
			getAccountRepository().updateAccount(this);
		}
	}

	public void remove() {
		getAccountRepository().deleteAccount(getAccountId());
	}
}
