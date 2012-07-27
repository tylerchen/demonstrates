package com.poinge.dao.hibernate;

import junit.framework.Assert;

import org.junit.Test;

import com.poinge.test.BaseDaoTestCase;

public class EntityManagerTest extends BaseDaoTestCase {

	public String[] getDataSetFilePaths() {
		return new String[] { "/dataset/themes.xml" };
	}

	@Test
	public void testColumnMapping() throws Exception {
		Assert.assertNotNull(getEntityManager());
	}

}
