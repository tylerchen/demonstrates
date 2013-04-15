/*******************************************************************************
 * Copyright (c) 2012-9-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.web.action.core.demo;

import javax.inject.Inject;

import com.foreveross.demo.sys.application.DemoApplication;
import com.foreveross.demo.sys.application.vo.DemoVO;
import com.foreveross.demo.web.action.base.BaseAction;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-14
 */
public class DemoAction extends BaseAction {

	@Inject
	DemoApplication demoApplication;

	private String hello;

	public String hello() {
		DemoVO vo = new DemoVO();
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

	public String query() {
		Page page = demoApplication.pageFindAccount(getPage(), "test");
		setPage(page);
		return RESULT_JSON;
	}

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

}
