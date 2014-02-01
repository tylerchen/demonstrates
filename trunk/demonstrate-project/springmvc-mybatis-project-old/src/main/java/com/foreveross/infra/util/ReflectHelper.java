/*******************************************************************************
 * Copyright (c) 2013-2-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-14
 */
public class ReflectHelper {

	private static boolean CACHE = false;
	private static Multimap<Class<?>, Field> CACHE_FIELD = ArrayListMultimap
			.create();
	private static Multimap<Class<?>, Method> CACHE_METHOD = ArrayListMultimap
			.create();

	private ReflectHelper() {
	}

	public static boolean isCache() {
		return CACHE;
	}

	public static void setCache(boolean cache) {
		CACHE = cache;
	}

}
