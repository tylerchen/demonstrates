package org.iff.demo.core.domain;

import java.io.Serializable;

import com.dayatang.domain.InstanceFactory;
import org.iff.demo.core.domain.infra.mybatis.AccountRepository;

@SuppressWarnings("serial")
public class Account implements Serializable {

	private String accountId;
	private String username;
	private String password;

	public Account() {
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
	private static AccountRepository accountRepository;

	private static AccountRepository getAccountRepository() {
		if (accountRepository == null) {
			accountRepository = InstanceFactory
					.getInstance(AccountRepository.class);
		}
		return accountRepository;
	}

	public static Account get(String accountId) {
		return getAccountRepository().getAccount(accountId);
	}

	public static Account remove(String accountId) {
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
