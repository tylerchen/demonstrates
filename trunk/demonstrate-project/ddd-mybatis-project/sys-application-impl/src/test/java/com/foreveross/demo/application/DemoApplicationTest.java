/*******************************************************************************
 * Copyright (c) 2012-9-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.application;

import org.junit.Test;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.sys.application.DemoApplication;
import com.foreveross.demo.sys.application.vo.DemoVO;
import com.foreveross.test.AbstractIntegratedTestCase;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-13
 */
public class DemoApplicationTest extends AbstractIntegratedTestCase {

	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/Demo.xml" };
	}

	@Test
	public void test_getAccount() {
		DemoApplication demo = InstanceFactory
				.getInstance(DemoApplication.class);
		System.out.println(demo.getAccountVO(String.valueOf(1001L)));
	}

//	@Test
//	public void test_addAccount() {
//		DemoApplication demo = InstanceFactory
//				.getInstance(DemoApplication.class);
//		DemoVO vo = new DemoVO();
//		vo.setPassword("1111");
//		vo.setUsername("11111");
//		for (int i = 0; i < 100; i++) {
//			vo.setAccountId(null);
//			vo = demo.saveAccount(vo);
//			System.out.println(vo);
//		}
//		System.out.println(demo.pageFindAccount(new Page(), "11111"));
//	}
//
//	@Test
//	public void test_pageFind() {
//		DemoApplication demo = InstanceFactory
//				.getInstance(DemoApplication.class);
//		System.out.println(demo.pageFindAccount(new Page(), "11111"));
//	}
}
