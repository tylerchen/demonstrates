/*******************************************************************************
 * Copyright (c) 2012-9-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.web.action.core;

import javax.inject.Inject;

import org.iff.demo.web.action.base.BaseAction;

import org.iff.demo.application.DemoApplication;
import org.iff.demo.application.vo.AccountVO;
import org.iff.demo.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-14
 */
public class DemoAction extends BaseAction {

	@Inject
	DemoApplication demoApplication;

	private String hello;

	public String hello() {
		AccountVO vo = new AccountVO();
		{
			vo.setUsername("test");
			vo.setPassword("test");
			vo = demoApplication.saveAccount(vo);
		}
		hello = demoApplication.pageFindAccount(new Page(), "test").toString();
		hello = hello + "<br/>";
		hello = hello + demoApplication.getAccountVO(vo.getAccountId());
		return RESULT_METHOD;
	}

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
}
