package com.foreveross.demo.modulea.domain;

import java.io.Serializable;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.modulea.repository.DemoRepository;

@SuppressWarnings("serial")
public class Demo implements Serializable {

	private String accountId;
	private String username;
	private String password;

	public Demo() {
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
	private static DemoRepository accountRepository;

	private static DemoRepository getAccountRepository() {
		if (accountRepository == null) {
			accountRepository = InstanceFactory
					.getInstance(DemoRepository.class);
		}
		return accountRepository;
	}

	public static Demo get(String accountId) {
		return getAccountRepository().getAccount(accountId);
	}

	public static Demo remove(String accountId) {
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
