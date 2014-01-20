/*******************************************************************************
 * Copyright (c) 2013-8-12 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @param <T>
 * @since 2013-8-12
 */
public interface LogDataCarrier {

	void put(String key, Object value);

	<T> T get(String key);
}
