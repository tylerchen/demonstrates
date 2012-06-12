/*******************************************************************************
 * Copyright (c) 2012-3-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application.impl;

import org.iff.demo.dddallinone.application.AbstractIntegratedTestCase;
import org.iff.demo.dddallinone.application.EJBFacade;
import org.iff.demo.dddallinone.dto.EJBFacadeDTO;
import org.iff.demo.dddallinone.dto.MyUserDTO;
import org.junit.Assert;
import org.junit.Test;

import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-13
 */
public class EJBFacadeImplTest extends AbstractIntegratedTestCase {

	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/MyUser.xml" };
	}

	public EJBFacade getEJBFacade() {
		return InstanceFactory
				.getInstance(EJBFacade.class, "AllInOneEJBFacade");
	}

	@Test
	public void test_MyUser_save() {
		try {
			MyUserDTO myUser = new MyUserDTO();
			myUser.setName("aaaa");

			EJBFacadeDTO invoke = getEJBFacade()
					.invoke(
							new EJBFacadeDTO(
									"org.iff.demo.dddallinone.application.MyUserApplication",
									"myUserApplication", "saveMyUser",
									new Object[] { myUser }));

			MyUserDTO saveMyUser = (MyUserDTO) invoke.getResult();
			Assert.assertNotNull(saveMyUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
