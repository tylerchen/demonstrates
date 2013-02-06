/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.dayatang.domain.InstanceFactory;
import org.iff.demo.application.DemoApplication;
import org.iff.demo.application.vo.AccountVO;
import org.iff.demo.core.domain.Account;
import org.iff.demo.core.domain.infra.mybatis.AccountRepository;
import org.iff.demo.util.BeanHelper;
import org.iff.demo.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@Named
@Transactional
public class DemoApplicationImpl implements DemoApplication {

	@Override
	public void removeAccount(String accountId) {
		Account.remove(accountId);
	}

	@Override
	public AccountVO saveAccount(AccountVO vo) {
		Account a = new Account();
		BeanHelper.copyProperties(a, vo);
		a.save();
		AccountVO copyProperties = BeanHelper
				.copyProperties(new AccountVO(), a);
		return copyProperties;
	}

	@Override
	public AccountVO getAccountVO(String accountId) {
		return BeanHelper.copyProperties(new AccountVO(), Account
				.get(accountId));
	}

	@Override
	public Page pageFindAccount(Page pages, String username) {
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<Account> l = InstanceFactory.getInstance(
					AccountRepository.class).pageFindAccount2(map);
			System.out.println(l);
		}
		List<Account> list = InstanceFactory.getInstance(
				AccountRepository.class).pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(AccountVO.class);
	}

}
