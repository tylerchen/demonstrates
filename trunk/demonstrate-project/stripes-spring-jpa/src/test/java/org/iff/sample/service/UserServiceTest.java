/*******************************************************************************
 * Copyright (c) 2012-5-5 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.service;

import java.util.Arrays;

import javax.inject.Inject;

import org.iff.sample.application.core.UserService;
import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.test.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.data.domain.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-5
 */
public class UserServiceTest extends BaseDaoTestCase {

	public String[] getDataSetFilePaths() {
		return new String[] { "/dataset/sample_role.xml",
				"/dataset/sample_user.xml" };
	}

	@Inject
	UserService userService;

	@Test
	public void save_user() {
		Page<User> findUser = userService.findUser(0, 10);
		System.out.println(Arrays.asList(findUser.getContent().toArray()));
	}

}
