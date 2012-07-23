/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.busineess.core.service.impl;

import javax.inject.Named;

import org.iff.sample.busineess.core.service.MyService;


/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@Named("myService")
public class MyServiceImpl implements MyService {

	public String hello() {
		return "HELLO";
	}

}
