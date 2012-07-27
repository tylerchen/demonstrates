/*******************************************************************************
 * Copyright (c) 2012-5-6 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.poinge.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.iff.sample.application.core.MailSenderService;
import org.junit.Test;

import com.poinge.test.BaseDaoTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-6
 */
public class MailSenderServiceTest extends BaseDaoTestCase {

	final static int PORT = 587;
	@Inject
	@Named("errorMail")
	MailSenderService errorMail;

	@Test
	public void testSend() throws Exception {
		System.out.println(errorMail);
		Map model = new HashMap();
		{
			model.put("to", "outute@163.com");
			model.put("subject", "test");
		}
		errorMail.sendWithTemplate(model);
	}

}
