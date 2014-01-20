/*******************************************************************************
 * Copyright (c) 2013-8-12 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.impl;

import com.foreveross.logging.LogDataCarrier;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-12
 */
public class ThreadLocalDataCarrier implements LogDataCarrier {

	private static final ThreadLocal threadCarrier = new ThreadLocal();

	public ThreadLocalDataCarrier() {
	}

	public <T> T get(String key) {
		return (T) threadCarrier.get();
	}

	public void put(String key, Object value) {
		threadCarrier.set(value);
	}
}
