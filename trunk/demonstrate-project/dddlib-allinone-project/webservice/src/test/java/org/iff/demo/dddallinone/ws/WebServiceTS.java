/*******************************************************************************
 * Copyright (c) 2012-3-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.ws;

import org.iff.demo.dddallinone.application.WebServiceFacade;
import org.iff.demo.dddallinone.dto.EJBFacadeDTO;
import org.iff.demo.dddallinone.dto.MyUserDTO;
import org.junit.Assert;
import org.junit.Test;

import com.dayatang.domain.InstanceFactory;
import com.dayatang.springtest.PureSpringTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-13
 */
public class WebServiceTS extends PureSpringTestCase {
	protected String[] springXmlPath() {
		return new String[] { "classpath:spring/dddlib-allinone-ws/cxfbeans-context.xml" };
	}

	@Test
	public void test() {
		try {
			WebServiceFacade instance = InstanceFactory
					.getInstance(
							org.iff.demo.dddallinone.application.WebServiceFacade.class,
							"identityValidateServiceClient");
			System.out.println(instance);
			MyUserDTO myUser = new MyUserDTO();
			myUser.setName("aaaa");

			EJBFacadeDTO invoke = instance
					.invoke(new EJBFacadeDTO(
							"org.iff.demo.dddallinone.application.MyUserApplication",
							"myUserApplication", "saveMyUser",
							new Object[] { myUser }));

			MyUserDTO saveMyUser = (MyUserDTO) invoke.getResult();
			Assert.assertNotNull(saveMyUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
