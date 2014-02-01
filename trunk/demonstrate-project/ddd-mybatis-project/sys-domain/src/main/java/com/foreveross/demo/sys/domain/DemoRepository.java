/*******************************************************************************
 * Copyright (c) 2013-1-30 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-1-30
 */
public interface DemoRepository {

	Demo getAccount(String accountId);

	Demo getAccountByUsernameAndPassword(Demo account);

	void insertAccount(Demo account);

	void updateAccount(Demo account);

	void deleteAccount(String id);

	List<Demo> pageFindAccount(@Param(value = "page") Page page,
			@Param(value = "username") String username);

	List<Demo> pageFindAccount2(Map<String, Object> params);
}
