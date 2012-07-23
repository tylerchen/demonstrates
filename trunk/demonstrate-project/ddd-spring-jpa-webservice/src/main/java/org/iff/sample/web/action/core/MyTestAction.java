/*******************************************************************************
 * Copyright (c) 2012-6-3 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.web.action.core;

import org.iff.sample.web.base.BaseAction;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-3
 */
public class MyTestAction extends BaseAction {

	private String hello;

	public String hello() {
		hello = "iTravel";
		return RESULT_METHOD;
	}

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

}
