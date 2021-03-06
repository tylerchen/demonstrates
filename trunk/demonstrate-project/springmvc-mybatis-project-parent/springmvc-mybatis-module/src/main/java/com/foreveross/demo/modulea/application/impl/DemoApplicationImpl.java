/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.modulea.application.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.modulea.application.DemoApplication;
import com.foreveross.demo.modulea.application.vo.DemoVO;
import com.foreveross.demo.modulea.domain.Demo;
import com.foreveross.demo.modulea.repository.DemoRepository;
import com.foreveross.infra.util.BeanHelper;
import com.foreveross.infra.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@Named("demoApplication")
@Transactional
public class DemoApplicationImpl implements DemoApplication {

	@Override
	public void removeAccount(String accountId) {
		Demo.remove(accountId);
	}

	@Override
	public DemoVO saveAccount(DemoVO vo) {
		Demo a = new Demo();
		BeanHelper.copyProperties(a, vo);
		a.save();
		DemoVO copyProperties = BeanHelper.copyProperties(new DemoVO(), a);
		return copyProperties;
	}

	@Override
	public DemoVO getAccountVO(String accountId) {
		return BeanHelper.copyProperties(new DemoVO(), Demo.get(accountId));
	}

	@Override
	public Page pageFindAccount(Page pages, String username) {
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<Demo> l = InstanceFactory.getInstance(DemoRepository.class)
					.pageFindAccount2(map);
			System.out.println(l);
		}
		List<Demo> list = InstanceFactory.getInstance(DemoRepository.class)
				.pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(DemoVO.class);
	}

	@Override
	public List<DemoVO> findAll(Page pages, String username) {
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<Demo> l = InstanceFactory.getInstance(DemoRepository.class)
					.pageFindAccount2(map);
			System.out.println(l);
		}
		List<Demo> list = InstanceFactory.getInstance(DemoRepository.class)
				.pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(DemoVO.class).getRows();
	}

	@Override
	public Page pageFindAllAccount() {
		Page pages = new Page(10);
		String username = "test";
		{
			DemoVO vo = new DemoVO();
			vo.setUsername("test");
			vo.setPassword("test");
			saveAccount(vo);
		}
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<Demo> l = InstanceFactory.getInstance(DemoRepository.class)
					.pageFindAccount2(map);
			System.out.println(l);
		}
		List<Demo> list = InstanceFactory.getInstance(DemoRepository.class)
				.pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(DemoVO.class);
	}

}
