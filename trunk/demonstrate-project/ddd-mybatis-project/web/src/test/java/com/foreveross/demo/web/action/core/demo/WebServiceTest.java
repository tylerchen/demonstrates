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
import com.foreveross.demo.sys.application.DemoApplication;
import com.foreveross.test.AbstractIntegratedTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-5-9
 */
public class WebServiceTest extends AbstractIntegratedTestCase {

	@Test
	public void test() {
		try {
			// FIXME fix the maven test config to start the web server before running this test
			DemoApplication demo = InstanceFactory.getInstance(
					DemoApplication.class, "demoService");
			System.out.println(demo.pageFindAllAccount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
