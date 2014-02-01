/*******************************************************************************
 * Copyright (c) 2013-1-30 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.moduleb.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foreveross.demo.moduleb.domain.DemoB;
import com.foreveross.infra.util.mybatis.mapper.Mapper;
import com.foreveross.infra.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-1-30
 */
@Mapper
public interface DemoBRepository {

	DemoB getAccount(String accountId);

	DemoB getAccountByUsernameAndPassword(DemoB account);

	void insertAccount(DemoB account);

	void updateAccount(DemoB account);

	void deleteAccount(String id);

	List<DemoB> pageFindAccount(@Param(value = "page") Page page,
			@Param(value = "username") String username);

	List<DemoB> pageFindAccount2(Map<String, Object> params);
}
