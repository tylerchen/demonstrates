/*******************************************************************************
 * Copyright (c) 2012-9-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application;

import org.iff.test.AbstractIntegratedTestCase;
import org.junit.Test;

import com.dayatang.domain.InstanceFactory;
import org.iff.demo.application.impl.DemoDroolsApplicationImpl;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-13
 */
public class DemoDroolsApplicationTest extends AbstractIntegratedTestCase {

	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/Account.xml" };
	}

	@Test
	public void test_pageFind() {
		DemoDroolsApplicationImpl demo = InstanceFactory
				.getInstance(DemoDroolsApplicationImpl.class);
		for (int i = 0; i < 10; i++) {
			InstanceFactory.getInstance(DemoDroolsApplicationImpl.class);
		}
		try {
			demo.customSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
