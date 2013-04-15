/*******************************************************************************
 * Copyright (c) 2013-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-28
 */
@SuppressWarnings("unchecked")
public class GsonHelper {

	private GsonHelper() {
	}

	public static Map toJsonMap(String json) {
		Gson gson = new Gson();
		LinkedHashMap fromJson = gson.fromJson(json, LinkedHashMap.class);
		return fromJson;
	}

	public static String toJsonString(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	/**
	 * Access Map Object.
	 * @param <T>
	 * @param map
	 * @param namePath properties split by dot '.', such as: rule.ruleName.
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-2-28
	 */
	public static <T> T accessMap(Map map, String namePath) {
		if (map == null || namePath == null || map.isEmpty()
				|| namePath.length() < 1) {
			return (T) null;
		}
		String[] names = namePath.split("\\.");
		Object value = null;
		Map tempMap = map;
		for (String name : names) {
			value = tempMap.get(name);
			if (value == null || !(value instanceof Map)) {
				break;
			}
			tempMap = (Map) value;
		}
		return (T) value;
	}
}
