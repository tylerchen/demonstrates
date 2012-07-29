package org.iff.sample.dao.hibernate;

import junit.framework.Assert;

import org.iff.sample.test.BaseDaoTestCase;
import org.junit.Test;


public class EntityManagerTest extends BaseDaoTestCase {

	public String[] getDataSetFilePaths() {
		return new String[] { "/dataset/sample_role.xml" };
	}

	@Test
	public void testColumnMapping() throws Exception {
		Assert.assertNotNull(getEntityManager());
	}

}
