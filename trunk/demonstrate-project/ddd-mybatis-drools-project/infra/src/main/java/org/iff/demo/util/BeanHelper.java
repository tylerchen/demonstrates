/*******************************************************************************
 * Copyright (c) 2013-1-31 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.util;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
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

	private BeanHelper() {
	}

	private static <T> T copyPropertiesToMap(Map dest, Object orig) {
		if (orig == null) {
			return (T) dest;
		}
		Field[] origFields = orig.getClass().getDeclaredFields();
		for (Field f : origFields) {
			boolean origAccess = f.isAccessible();
			try {
				f.setAccessible(true);
				dest.put(f.getName(), f.get(orig));
			} catch (Exception e) {
				System.out.println(f + " Can't set Map value!");
			} finally {
				f.setAccessible(origAccess);
			}
		}
		return (T) dest;
	}

	private static <T> T copyMapToMap(Map dest, Map orig) {
		if (orig == null) {
			return (T) dest;
		}
		for (Entry entry : (Set<Entry>) orig.entrySet()) {
			dest.put(entry.getKey(), entry.getValue());
		}
		return (T) dest;
	}

	private static <T> T copyPropertiesFromMap(Object dest, Map orig) {
		if (orig == null) {
			return (T) dest;
		}
		Map<String, Object> origFieldMap = new HashMap<String, Object>();
		Map<String, Field> destFieldMap = new HashMap<String, Field>();
		{
			for (Entry entry : (Set<Entry>) orig.entrySet()) {
				origFieldMap.put(entry.getKey().toString(), entry.getValue());
			}
		}
		{
			Field[] destFields = dest.getClass().getDeclaredFields();
			for (Field f : destFields) {
				destFieldMap.put(f.getName(), f);
			}
		}
		for (Entry<String, Object> entry : origFieldMap.entrySet()) {
			Object origField = entry.getValue();
			Field destField = destFieldMap.get(entry.getKey());
			if (destField == null) {
				System.out.println(origField + " Not match!");
				continue;
			}
			if ((destField.getModifiers() & Modifier.STATIC) != 0) {
				System.out.println(destField + " Is static!");
				continue;
			}
			if (!(origField.getClass() instanceof Class && destField
					.getGenericType() instanceof Class)) {
				continue;
			}
			if (origField.getClass() == destField.getGenericType()) {
				boolean destAccess = destField.isAccessible();
				try {
					destField.setAccessible(true);
					{
						Object value = origField;
						destField.set(dest, value);
					}
				} catch (Exception e) {
					System.out.println(destField
							+ " Can't set same type value!");
				} finally {
					destField.setAccessible(destAccess);
				}
			} else {
				Class<?> destClazz = (Class<?>) destField.getGenericType();
				Class<?> origClazz = (Class<?>) origField.getClass();

				if ((destClazz.isPrimitive() || Number.class
						.isAssignableFrom(destClazz))
						&& (origClazz.isPrimitive()
								|| Number.class.isAssignableFrom(origClazz) || CharSequence.class
								.isAssignableFrom(origClazz))) {
					boolean destAccess = destField.isAccessible();
					boolean isString = CharSequence.class
							.isAssignableFrom(origClazz);
					try {
						destField.setAccessible(true);
						{
							Object value = origField;
							if (value == null) {/* if value is null */
								if (destClazz.isPrimitive()) {
									destField.set(dest, (byte) 0);
								} else {
									destField.set(dest, null);
								}
							} else {/* if value is not null */
								if (destClazz == Double.class
										|| destClazz == double.class) {
									if (isString) {
										destField.set(dest, new Double(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.doubleValue());
									}
								} else if (destClazz == Float.class
										|| destClazz == float.class) {
									if (isString) {
										destField.set(dest, new Float(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.floatValue());
									}
								} else if (destClazz == Long.class
										|| destClazz == long.class) {
									if (isString) {
										destField.set(dest, new Long(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.longValue());
									}
								} else if (destClazz == Integer.class
										|| destClazz == int.class) {
									if (isString) {
										destField.set(dest, new Integer(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.intValue());
									}
								} else if (destClazz == Short.class
										|| destClazz == short.class) {
									if (isString) {
										destField.set(dest, new Short(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.shortValue());
									}
								} else if (destClazz == Byte.class
										|| destClazz == byte.class) {
									if (isString) {
										destField.set(dest, new Byte(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.byteValue());
									}
								} else if (destClazz == BigDecimal.class
										&& (origClazz == Double.class
												|| origClazz == double.class
												|| origClazz == Float.class
												|| origClazz == float.class || isString)) {
									if (isString) {
										destField.set(dest, BigDecimal
												.valueOf(new Double(String
														.valueOf(value))));
									} else {
										destField.set(dest, BigDecimal
												.valueOf(((Number) value)
														.doubleValue()));
									}

								} else if (destClazz == BigDecimal.class) {
									if (isString) {
										destField.set(dest, BigDecimal
												.valueOf(new Double(String
														.valueOf(value))));
									} else {
										destField.set(dest, BigDecimal
												.valueOf(((Number) value)
														.longValue()));
									}
								} else {
									System.out
											.println(destField
													+ " Can't set Unknowed Number value!");
								}
							}/* END if value is not null */
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set Number value!");
					} finally {
						destField.setAccessible(destAccess);
					}
				} else if (Date.class.isAssignableFrom(destClazz)
						&& Date.class.isAssignableFrom(origClazz)) {
					boolean destAccess = destField.isAccessible();
					try {
						destField.setAccessible(true);
						{
							Date value = (Date) origField;
							if (java.sql.Date.class == destClazz) {
								destField.set(dest, value == null ? null
										: new java.sql.Date(value.getTime()));
							} else {
								destField.set(dest, value == null ? null
										: value);
							}
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set String to Date value!");
					} finally {
						destField.setAccessible(destAccess);
					}
				} else if (Date.class.isAssignableFrom(destClazz)
						&& CharSequence.class.isAssignableFrom(origClazz)) {
					boolean destAccess = destField.isAccessible();
					try {
						destField.setAccessible(true);
						{
							CharSequence value = (CharSequence) origField;
							Date parseDate = DateUtils.parseDate(value
									.toString(), DATE_PARSE_PATTERNS);
							if (java.sql.Date.class == destClazz) {
								destField.set(dest,
										value == null ? null
												: new java.sql.Date(parseDate
														.getTime()));
							} else {
								destField.set(dest, value == null ? null
										: parseDate);
							}
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set String to Date value!");
					} finally {
						destField.setAccessible(destAccess);
					}
				} else if (String.class.isAssignableFrom(destClazz)
						&& Date.class.isAssignableFrom(origClazz)) {
					boolean destAccess = destField.isAccessible();
					try {
						destField.setAccessible(true);
						{
							Date value = (Date) origField;
							destField.set(dest, value == null ? null
									: DateFormatUtils.format(value,
											DATE_DEFAULT_PATTERN));
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set Date to String value!");
					} finally {
						destField.setAccessible(destAccess);
					}
				} else if (String.class.isAssignableFrom(destClazz)) {
					boolean destAccess = destField.isAccessible();
					try {
						destField.setAccessible(true);
						{
							Object value = (Object) origField;
							destField.set(dest, value == null ? null : String
									.valueOf(value));
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set String value!");
					} finally {
						destField.setAccessible(destAccess);
					}
				} else {
					System.out.println(origField.getClass()
							+ " Type is not match!");
				}
			}
		}
		return (T) dest;
	}

	public static <T> T copyProperties(Object dest, Object orig) {
		if (orig == null) {
			return (T) dest;
		}
		if (dest instanceof Map) {
			return (T) copyPropertiesToMap((Map) dest, orig);
		}
		if (dest instanceof Map && orig instanceof Map) {
			return (T) copyMapToMap((Map) dest, (Map) orig);
		}
		if (orig instanceof Map) {
			return (T) copyPropertiesFromMap(dest, (Map) orig);
		}
		Map<String, Field> origFieldMap = new HashMap<String, Field>();
		Map<String, Field> destFieldMap = new HashMap<String, Field>();
		{
			Field[] origFields = orig.getClass().getDeclaredFields();
			for (Field f : origFields) {
				origFieldMap.put(f.getName(), f);
			}
		}
		{
			Field[] destFields = dest.getClass().getDeclaredFields();
			for (Field f : destFields) {
				destFieldMap.put(f.getName(), f);
			}
		}
		for (Entry<String, Field> entry : origFieldMap.entrySet()) {
			Field origField = entry.getValue();
			Field destField = destFieldMap.get(entry.getKey());
			if (destField == null) {
				//System.out.println(origField + " Not match!");
				continue;
			}
			if ((destField.getModifiers() & Modifier.STATIC) != 0) {
				//System.out.println(destField + " Is static!");
				continue;
			}
			if (!(origField.getGenericType() instanceof Class && destField
					.getGenericType() instanceof Class)) {
				continue;
			}
			if (origField.getGenericType() == destField.getGenericType()) {
				boolean origAccess = origField.isAccessible();
				boolean destAccess = destField.isAccessible();
				try {
					origField.setAccessible(true);
					destField.setAccessible(true);
					{
						Object value = origField.get(orig);
						destField.set(dest, value);
					}
				} catch (Exception e) {
					System.out.println(destField
							+ " Can't set same type value!");
				} finally {
					origField.setAccessible(origAccess);
					destField.setAccessible(destAccess);
				}
			} else {
				Class<?> destClazz = (Class<?>) destField.getGenericType();
				Class<?> origClazz = (Class<?>) origField.getGenericType();

				if ((destClazz.isPrimitive() || Number.class
						.isAssignableFrom(destClazz))
						&& (origClazz.isPrimitive()
								|| Number.class.isAssignableFrom(origClazz) || CharSequence.class
								.isAssignableFrom(origClazz))) {
					boolean origAccess = origField.isAccessible();
					boolean destAccess = destField.isAccessible();
					boolean isString = CharSequence.class
							.isAssignableFrom(origClazz);
					try {
						origField.setAccessible(true);
						destField.setAccessible(true);
						{
							Object value = origField.get(orig);
							if (value == null) {/* if value is null */
								if (destClazz.isPrimitive()) {
									destField.set(dest, (byte) 0);
								} else {
									destField.set(dest, null);
								}
							} else {/* if value is not null */
								if (destClazz == Double.class
										|| destClazz == double.class) {
									if (isString) {
										destField.set(dest, new Double(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.doubleValue());
									}
								} else if (destClazz == Float.class
										|| destClazz == float.class) {
									if (isString) {
										destField.set(dest, new Float(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.floatValue());
									}
								} else if (destClazz == Long.class
										|| destClazz == long.class) {
									if (isString) {
										destField.set(dest, new Long(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.longValue());
									}
								} else if (destClazz == Integer.class
										|| destClazz == int.class) {
									if (isString) {
										destField.set(dest, new Integer(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.intValue());
									}
								} else if (destClazz == Short.class
										|| destClazz == short.class) {
									if (isString) {
										destField.set(dest, new Short(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.shortValue());
									}
								} else if (destClazz == Byte.class
										|| destClazz == byte.class) {
									if (isString) {
										destField.set(dest, new Byte(String
												.valueOf(value)));
									} else {
										destField.set(dest, ((Number) value)
												.byteValue());
									}
								} else if (destClazz == BigDecimal.class
										&& (origClazz == Double.class
												|| origClazz == double.class
												|| origClazz == Float.class
												|| origClazz == float.class || isString)) {
									if (isString) {
										destField.set(dest, BigDecimal
												.valueOf(new Double(String
														.valueOf(value))));
									} else {
										destField.set(dest, BigDecimal
												.valueOf(((Number) value)
														.doubleValue()));
									}

								} else if (destClazz == BigDecimal.class) {
									if (isString) {
										destField.set(dest, BigDecimal
												.valueOf(new Double(String
														.valueOf(value))));
									} else {
										destField.set(dest, BigDecimal
												.valueOf(((Number) value)
														.longValue()));
									}
								} else {
									System.out
											.println(destField
													+ " Can't set Unknowed Number value!");
								}
							}/* END if value is not null */
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set Number value!");
					} finally {
						origField.setAccessible(origAccess);
						destField.setAccessible(destAccess);
					}
				} else if (Date.class.isAssignableFrom(destClazz)
						&& Date.class.isAssignableFrom(origClazz)) {
					boolean origAccess = origField.isAccessible();
					boolean destAccess = destField.isAccessible();
					try {
						origField.setAccessible(true);
						destField.setAccessible(true);
						{
							Date value = (Date) origField.get(orig);
							if (java.sql.Date.class == destClazz) {
								destField.set(dest, value == null ? null
										: new java.sql.Date(value.getTime()));
							} else {
								destField.set(dest, value == null ? null
										: value);
							}
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set String to Date value!");
					} finally {
						origField.setAccessible(origAccess);
						destField.setAccessible(destAccess);
					}
				} else if (Date.class.isAssignableFrom(destClazz)
						&& CharSequence.class.isAssignableFrom(origClazz)) {
					boolean origAccess = origField.isAccessible();
					boolean destAccess = destField.isAccessible();
					try {
						origField.setAccessible(true);
						destField.setAccessible(true);
						{
							CharSequence value = (CharSequence) origField
									.get(orig);
							Date parseDate = DateUtils.parseDate(value
									.toString(), DATE_PARSE_PATTERNS);
							if (java.sql.Date.class == destClazz) {
								destField.set(dest,
										value == null ? null
												: new java.sql.Date(parseDate
														.getTime()));
							} else {
								destField.set(dest, value == null ? null
										: parseDate);
							}
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set String to Date value!");
					} finally {
						origField.setAccessible(origAccess);
						destField.setAccessible(destAccess);
					}
				} else if (String.class.isAssignableFrom(destClazz)
						&& Date.class.isAssignableFrom(origClazz)) {
					boolean origAccess = origField.isAccessible();
					boolean destAccess = destField.isAccessible();
					try {
						origField.setAccessible(true);
						destField.setAccessible(true);
						{
							Date value = (Date) origField.get(orig);
							destField.set(dest, value == null ? null
									: DateFormatUtils.format(value,
											DATE_DEFAULT_PATTERN));
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set Date to String value!");
					} finally {
						origField.setAccessible(origAccess);
						destField.setAccessible(destAccess);
					}
				} else if (String.class.isAssignableFrom(destClazz)) {
					boolean origAccess = origField.isAccessible();
					boolean destAccess = destField.isAccessible();
					try {
						origField.setAccessible(true);
						destField.setAccessible(true);
						{
							Object value = (Object) origField.get(orig);
							destField.set(dest, value == null ? null : String
									.valueOf(value));
						}
					} catch (Exception e) {
						System.out.println(destField
								+ " Can't set String value!");
					} finally {
						origField.setAccessible(origAccess);
						destField.setAccessible(destAccess);
					}
				} else {
					System.out.println(origField.getGenericType()
							+ " Type is not match!");
				}
			}
		}
		return (T) dest;
	}

	public static void main(String[] args) {
		B b = new B();
		C c = new C();
		Map map = new HashMap();
		{
			map.put("aa", "aa");
			map.put("b", "b");
			map.put("c", 11L);
			map.put("d", 11);
			map.put("e", new Date());
			map.put("f", "11");
			map.put("g", 111.1D);
			map.put("h", "2013-01-01");
			map.put("i", new Date());
			map.put("j", 111L);
			map.put("k", new java.sql.Date(new Date().getTime()));
		}
		System.out.println(BeanHelper.copyProperties(c, b));
		System.out.println(BeanHelper.copyProperties(c, map));
		System.out.println(BeanHelper.copyProperties(new HashMap(), b));
		System.out.println(BeanHelper.copyProperties(new HashMap(), map));
	}

	static class A {
		private int a = 1;
	}

	static class B extends A {
		private static String aa = "aa";
		//Same Type
		private String b = "b";
		private Long c = 11L;
		private int d = 11;
		private Date e = new Date();
		//String to Number
		private String f = "11";
		//Number to String
		private Double g = 111.1D;
		//String to Date
		private String h = "2013-01-01";
		//Date to String
		private Date i = new Date();
		//Other
		private Long j = 111L;
		private java.sql.Date k = new java.sql.Date(new Date().getTime());
	}

	static class C {
		private static String aa = "";
		private int a;
		//Same Type
		private String b;
		private Long c;
		private int d;
		private Date e;
		//String to Number
		private long f;
		//Number to String
		private String g;
		//String to Date
		private Date h;
		//Date to String
		private String i;
		//Other
		private Integer j;
		private Date k;

		@Override
		public String toString() {
			return "C [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e="
					+ e + ", f=" + f + ", g=" + g + ", h=" + h + ", i=" + i
					+ ", j=" + j + ", k=" + k + ", aa=" + aa + "]";
		}

	}
}
