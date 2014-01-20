/*******************************************************************************
 * Copyright (c) 2013-3-1 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.application;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-3-1
 */
@SuppressWarnings("serial")
public class Test3 implements Serializable {
	private Date f;
	private String g;

	public Test3() {
	}

	public Test3(Date f, String g) {
		this.f = f;
		this.g = g;
	}

	public Date getF() {
		return f;
	}

	public void setF(Date f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

}
