/*******************************************************************************
 * Copyright (c) 2012-2-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application.impl;

import org.iff.demo.dddallinone.application.AbstractIntegratedTestCase;
import org.iff.demo.dddallinone.application.MyUserApplication;
import org.iff.demo.dddallinone.dto.MyUserDTO;
import org.junit.Assert;
import org.junit.Test;

import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-2-7
 */
public class MyUserApplicationImplTest extends AbstractIntegratedTestCase {

	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/MyUser.xml" };
	}

	public MyUserApplication getMyUserApplication() {
		return InstanceFactory.getInstance(MyUserApplication.class);
	}

	@Test
	public void test_MyUser_save() {
		try {
			MyUserDTO myUser = new MyUserDTO();
			myUser.setName("aaaa");
			MyUserDTO saveMyUser = getMyUserApplication().saveMyUser(myUser);
			MyUserDTO findMyUserById = getMyUserApplication().findMyUserById(
					saveMyUser.getId());
			Assert.assertNotNull(findMyUserById);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
