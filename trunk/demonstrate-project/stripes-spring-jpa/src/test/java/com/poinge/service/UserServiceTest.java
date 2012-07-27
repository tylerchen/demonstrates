/*******************************************************************************
 * Copyright (c) 2012-5-5 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.poinge.service;

import javax.inject.Inject;

import org.iff.sample.application.core.UserService;
import org.junit.Test;

import com.poinge.test.BaseDaoTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-5
 */
public class UserServiceTest extends BaseDaoTestCase {

	@Inject
	UserService userService;

	@Test
	public void save_user() {
	}

}
