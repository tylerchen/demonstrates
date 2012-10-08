/*******************************************************************************
 * Copyright (c) 2012-9-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application;

import org.iff.demo.vo.biz.UserVO;
import org.iff.test.AbstractIntegratedTestCase;
import org.junit.Test;

import com.dayatang.domain.EntityRepository;
import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-13
 */
public class DemoApplicationTest extends AbstractIntegratedTestCase {

	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/User.xml" };
	}

	@Test
	public void test_addUser() {
		DemoApplication demo = InstanceFactory
				.getInstance(DemoApplication.class);
		System.out.println(demo);
		System.out.println(InstanceFactory.getInstance(EntityRepository.class));
		UserVO dto = new UserVO();
		{
			dto.setUserName("Tyler");
		}
		dto = demo.addUser(dto);
		System.out.println(dto);
	}

	@Test
	public void test_removeUser() {
		DemoApplication demo = InstanceFactory
				.getInstance(DemoApplication.class);
		demo.removeUser(1001L);
	}
}
