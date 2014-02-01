/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.moduleb.application.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.moduleb.application.DemoBApplication;
import com.foreveross.demo.moduleb.application.vo.DemoBVO;
import com.foreveross.demo.moduleb.domain.DemoB;
import com.foreveross.demo.moduleb.repository.DemoBRepository;
import com.foreveross.infra.util.BeanHelper;
import com.foreveross.infra.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@Named("demoBApplication")
@Transactional
public class DemoBApplicationImpl implements DemoBApplication {

	@Override
	public void removeAccount(String accountId) {
		DemoB.remove(accountId);
	}

	@Override
	public DemoBVO saveAccount(DemoBVO vo) {
		DemoB a = new DemoB();
		BeanHelper.copyProperties(a, vo);
		a.save();
		DemoBVO copyProperties = BeanHelper.copyProperties(new DemoBVO(), a);
		return copyProperties;
	}

	@Override
	public DemoBVO getAccountVO(String accountId) {
		return BeanHelper.copyProperties(new DemoBVO(), DemoB.get(accountId));
	}

	@Override
	public Page pageFindAccount(Page pages, String username) {
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<DemoB> l = InstanceFactory.getInstance(DemoBRepository.class)
					.pageFindAccount2(map);
			System.out.println(l);
		}
		List<DemoB> list = InstanceFactory.getInstance(DemoBRepository.class)
				.pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(DemoBVO.class);
	}

	@Override
	public List<DemoBVO> findAll(Page pages, String username) {
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<DemoB> l = InstanceFactory.getInstance(DemoBRepository.class)
					.pageFindAccount2(map);
			System.out.println(l);
		}
		List<DemoB> list = InstanceFactory.getInstance(DemoBRepository.class)
				.pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(DemoBVO.class).getRows();
	}

	@Override
	public Page pageFindAllAccount() {
		Page pages = new Page(10);
		String username = "test";
		{
			DemoBVO vo = new DemoBVO();
			vo.setUsername("test");
			vo.setPassword("test");
			saveAccount(vo);
		}
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pages);
			map.put("username", username);
			List<DemoB> l = InstanceFactory.getInstance(DemoBRepository.class)
					.pageFindAccount2(map);
			System.out.println(l);
		}
		List<DemoB> list = InstanceFactory.getInstance(DemoBRepository.class)
				.pageFindAccount(pages, username);
		pages.setRows(list);
		return pages.toPage(DemoBVO.class);
	}

}
