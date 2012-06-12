/*******************************************************************************
 * Copyright (c) 2012-3-23 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.common.uitag.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-23
 */
public final class ReflectHelper {
	private ReflectHelper() {
	}

	public static void main(String[] args) {
		class A {
			private String name;
			private boolean sex;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public boolean isSex() {
				return sex;
			}

			public void setSex(boolean sex) {
				this.sex = sex;
			}
		}
		class B extends A {
			private int age;

			public int getAge() {
				return age;
			}

			public void setAge(int age) {
				this.age = age;
			}
		}
		Map<String, Method> getters = getGetters(B.class);
		for (Entry<String, Method> entry : getters.entrySet()) {
			System.out.println(entry.getKey() + ":"
					+ entry.getValue().toString());
		}
	}

	public static Map<String, Method> getGetters(Class<?> clazz) {
		Map<String, Method> map = new LinkedHashMap<String, Method>();
		Class<?> searchType = clazz;
		while (searchType != null && searchType != Object.class) {
			Method[] methods = (searchType.isInterface()) ? new Method[0]
					: searchType.getDeclaredMethods();
			for (Method method : methods) {
				String name = method.getName();
				int modifiers = method.getModifiers();
				if (modifiers - Modifier.PUBLIC == 0
						&& method.getParameterTypes().length == 0) {
					method.setAccessible(true);
					if (name.length() > 3 && name.startsWith("get")) {
						name = Character.toLowerCase(name.charAt(3))
								+ name.substring(4, name.length());
						map.put(name, method);
					} else if (name.length() > 2 && name.startsWith("is")) {
						name = Character.toLowerCase(name.charAt(2))
								+ name.substring(3, name.length());
						map.put(name, method);
					}
				}
			}
			searchType = searchType.getSuperclass();
		}
		return map;
	}

	public static Object getGetterValue(Method method, Object o) {
		try {
			return method.invoke(o, null);
		} catch (Exception e) {
		}
		return null;
	}
}
