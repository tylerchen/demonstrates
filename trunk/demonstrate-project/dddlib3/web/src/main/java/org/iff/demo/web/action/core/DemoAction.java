/*******************************************************************************
 * Copyright (c) 2012-9-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.web.action.core;

import java.util.Arrays;

import javax.inject.Inject;

import org.iff.demo.application.DemoApplication;
import org.iff.demo.vo.biz.UserVO;
import org.iff.demo.web.action.base.BaseAction;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-14
 */
public class DemoAction extends BaseAction {

	@Inject
	DemoApplication demoApplication;

	private String hello;

	public String hello() {
		{
			UserVO vo = new UserVO();
			vo.setUserName("hello");
			demoApplication.addUser(vo);
		}
		hello = Arrays.toString(demoApplication.findAllUserPageable(0, 10)
				.toArray());
		hello = hello + "<br/>";
		hello = hello
				+ Arrays.toString(demoApplication.findUserByUserName("hello")
						.toArray());
		return RESULT_METHOD;
	}

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
}
