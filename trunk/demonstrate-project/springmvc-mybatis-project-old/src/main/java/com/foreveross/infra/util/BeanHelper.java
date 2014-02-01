/*******************************************************************************
 * Copyright (c) 2013-1-31 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-1-31
 */
@SuppressWarnings("unchecked")
public class BeanHelper {

	private static final String DATE_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final String[] DATE_PARSE_PATTERNS = new String[] {
			"yyyy-MM-dd", DATE_DEFAULT_PATTERN, "yyyy-MM-dd HH:mm" };

	//

	private BeanHelper() {
	}

	public static <T> T copyProperties(Object dest, Object orig) {
		if (orig == null || dest == null) {
			return (T) dest;
		}
		Map<String, BeanField> origFieldMap = new HashMap<String, BeanField>();
		Map<String, BeanField> destFieldMap = new HashMap<String, BeanField>();
		if (orig instanceof Map) {// Is Map and Not cache
			Map origMap = (Map) orig;
			for (Entry entry : (Set<Entry>) origMap.entrySet()) {
				String name = entry.getKey().toString();
				BeanField beanField = BeanField.getBeanField(name, origMap);
				if (beanField != null) {
					origFieldMap.put(name, beanField);
				}
			}
		} else {// Is Object
			Class<?> clazz = orig.getClass();
			while (clazz != null && clazz != Object.class) {
				Field[] origFields = clazz.getDeclaredFields();
				for (Field f : origFields) {
					BeanField beanField = BeanField.getBeanField(f, orig, true);
					if (beanField != null
							&& !origFieldMap.containsKey(f.getName())) {
						origFieldMap.put(f.getName(), beanField);
					}
				}
				clazz = clazz.getSuperclass();
			}
		}
		if (dest instanceof Map) {// Is Map and Not cache
			for (Entry<String, BeanField> entry : origFieldMap.entrySet()) {
				BeanField beanField = BeanField.copyBeanField(entry.getValue(),
						(Map) dest);
				if (beanField != null) {
					destFieldMap.put(entry.getKey(), beanField);
				}
			}
		} else {// Is Object 
			Class<?> clazz = dest.getClass();
			while (clazz != null && clazz != Object.class) {
				Field[] destFields = clazz.getDeclaredFields();
				for (Field f : destFields) {
					BeanField beanField = BeanField
							.getBeanField(f, dest, false);
					if (beanField != null
							&& !destFieldMap.containsKey(f.getName())) {
						destFieldMap.put(f.getName(), beanField);
					}
				}
				clazz = clazz.getSuperclass();
			}
		}

		for (Entry<String, BeanField> entry : origFieldMap.entrySet()) {
			BeanField origField = entry.getValue();
			BeanField destField = destFieldMap.get(entry.getKey());
			if (destField == null || origField == null) {
				continue;
			}
			if (!(origField.getClazz() instanceof Class && destField.getClazz() instanceof Class)) {
				continue;
			}
			if (origField.getClazz() == destField.getClazz()) {
				destField.setValue(origField.getValue());
			} else {
				Class<?> destClazz = (Class<?>) destField.getClazz();
				Class<?> origClazz = (Class<?>) origField.getClazz();

				if ((destClazz.isPrimitive() || Number.class
						.isAssignableFrom(destClazz))
						&& (origClazz.isPrimitive()
								|| Number.class.isAssignableFrom(origClazz) || CharSequence.class
								.isAssignableFrom(origClazz))) {
					boolean isString = CharSequence.class
							.isAssignableFrom(origClazz);
					Object value = origField.getValue();
					if (value == null) {/* if value is null */
						if (destClazz.isPrimitive()) {
							destField.setValue((byte) 0);
						} else {
							destField.setValue(null);
						}
					} else {/* if value is not null */
						if (destClazz == Double.class
								|| destClazz == double.class) {
							if (isString) {
								destField.setValue(new Double(String
										.valueOf(value)));
							} else {
								destField.setValue(((Number) value)
										.doubleValue());
							}
						} else if (destClazz == Float.class
								|| destClazz == float.class) {
							if (isString) {
								destField.setValue(new Float(String
										.valueOf(value)));
							} else {
								destField.setValue(((Number) value)
										.floatValue());
							}
						} else if (destClazz == Long.class
								|| destClazz == long.class) {
							if (isString) {
								destField.setValue(new Long(String
										.valueOf(value)));
							} else {
								destField
										.setValue(((Number) value).longValue());
							}
						} else if (destClazz == Integer.class
								|| destClazz == int.class) {
							if (isString) {
								destField.setValue(new Integer(String
										.valueOf(value)));
							} else {
								destField.setValue(((Number) value).intValue());
							}
						} else if (destClazz == Short.class
								|| destClazz == short.class) {
							if (isString) {
								destField.setValue(new Short(String
										.valueOf(value)));
							} else {
								destField.setValue(((Number) value)
										.shortValue());
							}
						} else if (destClazz == Byte.class
								|| destClazz == byte.class) {
							if (isString) {
								destField.setValue(new Byte(String
										.valueOf(value)));
							} else {
								destField
										.setValue(((Number) value).byteValue());
							}
						} else if (destClazz == BigDecimal.class
								&& (origClazz == Double.class
										|| origClazz == double.class
										|| origClazz == Float.class
										|| origClazz == float.class || isString)) {
							if (isString) {
								destField.setValue(BigDecimal
										.valueOf(new Double(String
												.valueOf(value))));
							} else {
								destField
										.setValue(BigDecimal
												.valueOf(((Number) value)
														.doubleValue()));
							}

						} else if (destClazz == BigDecimal.class) {
							if (isString) {
								destField.setValue(BigDecimal
										.valueOf(new Double(String
												.valueOf(value))));
							} else {
								destField.setValue(BigDecimal
										.valueOf(((Number) value).longValue()));
							}
						} else {
							System.out.println(destField
									+ " Can't set Unknowed Number value!");
						}
					}/* END if value is not null */
				} else if (destClazz == boolean.class
						|| destClazz == Boolean.class) {// Object to Boolean
					Object value = origField.getValue();
					if (origClazz == boolean.class
							|| origClazz == Boolean.class) {
						destField.setValue(value);
					} else {
						destField.setValue(Boolean.valueOf(String
								.valueOf(value)));
					}
				} else if (Date.class.isAssignableFrom(destClazz)
						&& Date.class.isAssignableFrom(origClazz)) {// Date to Date
					Date value = origField.getValue();
					if (java.sql.Date.class == destClazz) {
						destField.setValue(value == null ? null
								: new java.sql.Date(value.getTime()));
					} else {
						destField.setValue(value == null ? null : value);
					}
				} else if (Date.class.isAssignableFrom(destClazz)
						&& CharSequence.class.isAssignableFrom(origClazz)) {// String to Date
					CharSequence value = origField.getValue();
					Date parseDate = null;
					try {
						parseDate = DateUtils.parseDate(value.toString(),
								DATE_PARSE_PATTERNS);
					} catch (Exception e) {
					}
					if (value == null) {
						destField.setValue(null);
					} else if (java.sql.Date.class == destClazz) {
						destField.setValue(new java.sql.Date(parseDate
								.getTime()));
					} else {
						destField.setValue(parseDate);
					}
				} else if (String.class.isAssignableFrom(destClazz)
						&& Date.class.isAssignableFrom(origClazz)) {// Date to String
					Date value = origField.getValue();
					destField.setValue(value == null ? null : DateFormatUtils
							.format(value, DATE_DEFAULT_PATTERN));
				} else if (String.class.isAssignableFrom(destClazz)) {// Object to String
					Object value = (Object) origField.getValue();
					destField.setValue(value == null ? null : String
							.valueOf(value));
				} else if ((destClazz.getName().indexOf("java.") > -1 || destClazz
						.getName().indexOf("javax.") > -1)
						&& (origClazz.getName().indexOf("java.") > -1 || origClazz
								.getName().indexOf("javax.") > -1)) {// Custom complex type
					System.out.println("Not support the complex type:"
							+ destField + ", " + origField);
				} else {
					System.out.println("Type is not match: " + destField + ", "
							+ origField);
				}
			}/* END origField.getClazz() != destField.getClazz() */
		}
		return (T) dest;
	}

	static class BeanField {
		private String name;
		private Object value;
		private Class<?> clazz;
		//
		private Field field = null;
		private Object instance = null;

		private BeanField() {
		}

		static BeanField getBeanField(Field field, Object instance,
				boolean withValue) {
			if ((field.getModifiers() & Modifier.STATIC) != 0) {
				return null;
			}
			BeanField bf = new BeanField();
			{
				bf.name = field.getName();
				bf.clazz = field.getType();
				if (withValue) {
					try {
						if (field.isAccessible()) {
							bf.value = field.get(instance);
						} else {
							field.setAccessible(true);
							bf.value = field.get(instance);
							field.setAccessible(false);
						}
					} catch (Exception e) {
					}
				}
				bf.field = field;
				bf.instance = instance;
			}
			return bf;
		}

		static BeanField getBeanField(String name, Map instance) {
			BeanField bf = new BeanField();
			{
				bf.name = name;
				try {
					bf.value = instance.get(name);
					if (bf.value == null) {
						bf.clazz = Object.class;
					} else {
						bf.clazz = bf.value.getClass();
					}
				} catch (Exception e) {
				}
				bf.field = null;
				bf.instance = instance;
			}
			return bf;
		}

		static BeanField copyBeanField(BeanField field, Map instance) {
			BeanField bf = new BeanField();
			{
				bf.name = field.name;
				bf.clazz = field.getClazz();
				bf.field = null;
				bf.instance = instance;
			}
			return bf;
		}

		public <T> T getValue() {
			return (T) value;
		}

		public void setValue(Object value) {
			if (field != null && instance != null) {
				try {
					if (field.isAccessible()) {
						field.set(instance, value);
					} else {
						field.setAccessible(true);
						field.set(instance, value);
						field.setAccessible(false);
					}
				} catch (Exception e) {
					System.out.println("Can't setting the value: " + field
							+ ", value=" + value);
				}
			} else {
				((Map) instance).put(name, value);
			}
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public String toString() {
			return "BeanField [clazz=" + clazz + ", field=" + field
					+ ", instance=" + instance + ", name=" + name + ", value="
					+ value + "]";
		}
	}

//	public static void main(String[] args) {
//		B b = new B();
//		C c = new C();
//		b.print();
//		Map map = new HashMap();
//		{
//			map.put("aa", "aa");
//			map.put("b", "b");
//			map.put("c", 11L);
//			map.put("d", 11);
//			map.put("e", new Date());
//			map.put("f", "11");
//			map.put("g", 111.1D);
//			map.put("h", "2013-01-01");
//			map.put("i", new Date());
//			map.put("j", 111L);
//			map.put("k", new java.sql.Date(new Date().getTime()));
//		}
//		{
//			long start = System.currentTimeMillis();
//			for (int i = 0; i < 10000; i++) {
//				BeanHelper.copyProperties(c, b);
//			}
//			System.out.println("Object2Object:"
//					+ (System.currentTimeMillis() - start) / 10000.0);
//		}
//		{
//			long start = System.currentTimeMillis();
//			for (int i = 0; i < 10000; i++) {
//				BeanHelper.copyProperties(c, map);
//			}
//			System.out.println("Map2Object:"
//					+ (System.currentTimeMillis() - start) / 10000.0);
//		}
//		{
//			long start = System.currentTimeMillis();
//			for (int i = 0; i < 10000; i++) {
//				BeanHelper.copyProperties(new HashMap(), b);
//			}
//			System.out.println("Object2Map:"
//					+ (System.currentTimeMillis() - start) / 10000.0);
//		}
//		{
//			long start = System.currentTimeMillis();
//			for (int i = 0; i < 10000; i++) {
//				BeanHelper.copyProperties(new HashMap(), map);
//			}
//			System.out.println("Map2Map:"
//					+ (System.currentTimeMillis() - start) / 10000.0);
//		}
//		System.out.println(BeanHelper.copyProperties(c, b));
//		System.out.println(BeanHelper.copyProperties(c, map));
//		System.out.println(BeanHelper.copyProperties(new HashMap(), b));
//		System.out.println(BeanHelper.copyProperties(new HashMap(), map));
//	}
//
//	static class A {
//		private int a = 1;
//
//		void print() {
//			System.out.println(getClass());
//		}
//	}
//
//	static class B extends A {
//		private static String aa = "aa";
//		//Same Type
//		private String b = "b";
//		private Long c = 11L;
//		private int d = 11;
//		private Date e = new Date();
//		//String to Number
//		private String f = "11";
//		//Number to String
//		private Double g = 111.1D;
//		//String to Date
//		private String h = "2013-01-01";
//		//Date to String
//		private Date i = new Date();
//		//Other
//		private Long j = 111L;
//		private java.sql.Date k = new java.sql.Date(new Date().getTime());
//	}
//
//	static class C {
//		private static String aa = "";
//		private int a;
//		//Same Type
//		private String b;
//		private Long c;
//		private int d;
//		private Date e;
//		//String to Number
//		private long f;
//		//Number to String
//		private String g;
//		//String to Date
//		private Date h;
//		//Date to String
//		private String i;
//		//Other
//		private Integer j;
//		private Date k;
//
//		@Override
//		public String toString() {
//			return "C [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e="
//					+ e + ", f=" + f + ", g=" + g + ", h=" + h + ", i=" + i
//					+ ", j=" + j + ", k=" + k + ", aa=" + aa + "]";
//		}
//	}
}
