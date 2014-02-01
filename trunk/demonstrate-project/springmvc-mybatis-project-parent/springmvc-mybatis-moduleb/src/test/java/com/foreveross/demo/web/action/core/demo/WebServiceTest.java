/*******************************************************************************
 * Copyright (c) 2013-5-9 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.web.action.core.demo;

import org.junit.Test;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.demo.moduleb.application.DemoBApplication;
import com.foreveross.infra.test.AbstractIntegratedTestCase;
import com.foreveross.infra.util.SocketHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-5-9
 */
public class WebServiceTest extends AbstractIntegratedTestCase {

	@Test
	public void test() {
		if (SocketHelper.test("localhost", 8080)) {
			DemoBApplication demo = InstanceFactory.getInstance(
					DemoBApplication.class, "demoBService");
			System.out.println(demo.pageFindAllAccount());
		}
	}
}
