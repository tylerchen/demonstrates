/*******************************************************************************
 * Copyright (c) 2013-3-1 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.application;

import java.io.Serializable;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-1
 */
@SuppressWarnings("serial")
public class Test2 implements Serializable {
	private String d;
	private String e;

	public Test2() {
	}

	public Test2(String d, String e) {
		this.d = d;
		this.e = e;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

}
