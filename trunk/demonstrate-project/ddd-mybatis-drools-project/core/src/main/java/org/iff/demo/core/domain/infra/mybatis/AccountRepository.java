/*******************************************************************************
 * Copyright (c) 2013-1-30 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.core.domain.infra.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import org.iff.demo.core.domain.Account;
import org.iff.demo.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-1-30
 */
public interface AccountRepository {

	Account getAccount(String accountId);

	Account getAccountByUsernameAndPassword(Account account);

	void insertAccount(Account account);

	void updateAccount(Account account);

	void deleteAccount(String id);

	List<Account> pageFindAccount(@Param(value = "page") Page page,
			@Param(value = "username") String username);

	List<Account> pageFindAccount2(Map<String, Object> params);
}
