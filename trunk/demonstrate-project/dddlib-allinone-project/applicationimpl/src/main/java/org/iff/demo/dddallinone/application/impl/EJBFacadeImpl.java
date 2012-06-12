/*******************************************************************************
 * Copyright (c) 2012-3-12 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.iff.demo.dddallinone.application.EJBFacade;
import org.iff.demo.dddallinone.application.WebServiceFacade;
import org.iff.demo.dddallinone.dto.EJBFacadeDTO;
import org.springframework.stereotype.Service;

import com.dayatang.domain.InstanceFactory;

/**
 * NOTE: use Object type instead Primitive type in method parameters definition. 
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-12
 */
@Service("AllInOneEJBFacade")
@Stateless(name = "EJBFacadeBean")
@Remote(EJBFacade.class)
@javax.interceptor.Interceptors(SpringEJBIntercepter.class)
public class EJBFacadeImpl implements EJBFacade, WebServiceFacade {

	private static final Map<String, Map<String, List<Method>>> interface_methodName_methodList_map = new HashMap<String, Map<String, List<Method>>>();

	public static void main(String[] args) {
		Object[] objs = new Object[] { 1, "a" };
		System.out.println(int.class.isInstance(objs[0]));
		System.out.println(findMethod(EJBFacade.class, "test", new Object[] {
				1, 2 }));
	}

	public EJBFacadeDTO invoke(EJBFacadeDTO dto) {
		System.out.println("============================" + dto);
		if (dto == null) {
			throw new IllegalArgumentException("[EJBFacadeDTO] is null.");
		}
		if (dto.getInterfaceName() == null
				|| dto.getInterfaceName().length() < 1) {
			throw new IllegalArgumentException("[interfaceName] is required.");
		}
		if (dto.getBeanName() == null || dto.getBeanName().length() < 1) {
			throw new IllegalArgumentException("[beanName] is required.");
		}
		if (dto.getMethodName() == null || dto.getMethodName().length() < 1) {
			throw new IllegalArgumentException("[methodName] is required.");
		}
		Object instance = null;
		Method method = null;
		try {
			Class<?> forName = Class.forName(dto.getInterfaceName());
			instance = dto.getBeanName() == null ? InstanceFactory.getInstance(
					forName, dto.getBeanName()) : InstanceFactory
					.getInstance(forName);
			method = findMethod(forName, dto.getMethodName(), dto
					.getParameters());
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("[" + dto.getInterfaceName()
					+ "] class not found.", e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (instance == null) {
			throw new NullPointerException("[" + dto.getBeanName()
					+ "] bean name not found.");
		}
		if (method == null) {
			throw new NullPointerException("[" + dto.getMethodName()
					+ "] method name with parameters type not found.");
		}
		try {
			Object invoke = method.invoke(instance, dto.getParameters());
			if (invoke != null && !Serializable.class.isInstance(invoke)) {
				throw new ClassCastException("return value type:"
						+ invoke.getClass().getName()
						+ " can't cast to java.io.Serializable");
			}
			return new EJBFacadeDTO(invoke);
		} catch (Exception e) {
			throw new RuntimeException("invoke error:", e);
		}
	}

	public void refreshMethod() {
		Set<String> set = new HashSet<String>(
				interface_methodName_methodList_map.keySet());
		synchronized (interface_methodName_methodList_map) {
			if (interface_methodName_methodList_map.isEmpty()) {
				return;
			} else {
				interface_methodName_methodList_map.clear();
			}
			for (String key : set) {
				try {
					Class<?> forName = Class.forName(key);
					findMethod(forName, "test", new Object[0]);
				} catch (Exception e) {
				}
			}
		}
	}

	private static Method findMethod(Class<?> clazz, String methodName,
			Object[] parameters) {
		String className = clazz.getName();
		Map<String, List<Method>> methodNameListMap = interface_methodName_methodList_map
				.get(className);
		if (methodNameListMap == null) {/* if interface name is not included in map */
			Map<String, Map<String, List<Method>>> map = new HashMap<String, Map<String, List<Method>>>();
			Class<?> searchType = clazz;
			while (searchType != null) {
				Method[] methods = (searchType.isInterface()) ? searchType
						.getMethods() : searchType.getDeclaredMethods();
				Map<String, List<Method>> map2 = map.get(className);
				{
					map2 = map2 == null ? new HashMap<String, List<Method>>()
							: map2;
					map.put(className, map2);
				}
				for (Method method : methods) {
					List<Method> list = map2.get(method.getName());
					{
						list = list == null ? new ArrayList<Method>() : list;
						map2.put(method.getName(), list);
					}
					list.add(method);
				}
				searchType = searchType.getSuperclass();
			}
			if (!map.isEmpty()) {
				synchronized (interface_methodName_methodList_map) {
					if (!interface_methodName_methodList_map
							.containsKey(className)) {
						interface_methodName_methodList_map.putAll(map);
					}
				}
			}
			methodNameListMap = interface_methodName_methodList_map
					.get(className);
		}
		if (methodNameListMap == null) {
			return null;
		}
		List<Method> list = methodNameListMap.get(methodName);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			if (list.get(0).getParameterTypes().length == parameters.length) {
				return list.get(0);
			} else {
				return null;
			}
		}
		for (Method method : list) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length != parameters.length) {
				continue;
			}
			int i = 0, len = parameterTypes.length;
			for (; i < len; i++) {
				if (parameters[i] == null) {
					if (parameterTypes[i].isPrimitive()) {
						break;
					} else {
						continue;
					}
				} else if (parameterTypes[i].isPrimitive()) {//TODO: FIXME
					if (Number.class.isInstance(parameters[i])) {
						continue;
					}
				}
				if (!parameterTypes[i].isInstance(parameters[i])) {
					break;
				}
			}
			if (i == len) {
				return method;
			}
		}
		return null;
	}
}
