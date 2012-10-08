package com.dayatang.auth.core;

/**
 * @author Tyler
 */
public abstract interface PasswordEncoder {

	/**
	 * Encodes the specified raw password with an implementation specific algorithm.
	 * @param rawPass the password to encode
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2011-11-23
	 */
	String encodePassword(String rawPass);

	/**
	 * Validates a specified "raw" password against an encoded password.
	 * @param encPass a pre-encoded password
	 * @param rawPass a raw password to encode and compare against the pre-encoded password
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2011-11-23
	 */
	boolean isPasswordValid(String encPass, String rawPass);
}