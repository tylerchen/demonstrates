/*******************************************************************************
 * Copyright (c) 2011-11-23 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.dayatang.auth.core;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2011-11-23
 */
public class DefaultPasswordEncoder implements PasswordEncoder {

	public DefaultPasswordEncoder() {
	}

	public String encodePassword(String rawPass) {
		return rawPass;
	}

	public boolean isPasswordValid(String encPass, String rawPass) {
		return rawPass != null && encPass.equals(encodePassword(rawPass));
	}

}
