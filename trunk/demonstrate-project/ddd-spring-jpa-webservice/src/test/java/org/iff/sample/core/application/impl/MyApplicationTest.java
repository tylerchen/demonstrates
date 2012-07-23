/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.core.application.impl;

import java.util.List;

import junit.framework.Assert;

import org.iff.sample.application.core.MyApplication;
import org.iff.sample.infra.dto.core.MyTestDTO;
import org.iff.test.AbstractIntegratedTestCase;
import org.junit.Test;

import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
public class MyApplicationTest extends AbstractIntegratedTestCase {

	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/MyTest.xml" };
	}

	MyApplication getMyApplication() {
		return InstanceFactory.getInstance(MyApplication.class);
	}

	@Test
	public void test_findMyTestEntityByName() {
		List<MyTestDTO> list = getMyApplication().findMyTestByName("test1");
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void test_findMyTestAll() {
		List<MyTestDTO> list = getMyApplication().findMyTestAll();
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void test() {
	}
}
