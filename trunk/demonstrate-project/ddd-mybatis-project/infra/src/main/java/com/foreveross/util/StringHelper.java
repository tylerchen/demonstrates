/*******************************************************************************
 * Copyright (c) 2012-11-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-11-21
 */
public final class StringHelper {

	public static final int HIGHEST_SPECIAL = '>' + 1;
	public static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL][];
	static {
		specialCharactersRepresentation['&'] = "&amp;".toCharArray();
		specialCharactersRepresentation['<'] = "&lt;".toCharArray();
		specialCharactersRepresentation['>'] = "&gt;".toCharArray();
		specialCharactersRepresentation['"'] = "&#034;".toCharArray();
		specialCharactersRepresentation['\''] = "&#039;".toCharArray();
	}

	private StringHelper() {
	}

	public static String concat(String... strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			if (str == null) {
				str = "";
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * replace the xml chars:
	 * <code>&</code> to <code>&amp;amp;</code>
	 * <code><</code> to <code>&amp;lt;</code>
	 * <code>></code> to <code>&amp;gt;</code>
	 * <code>"</code> to <code>&amp;#034;</code>
	 * <code>'</code> to <code>&amp;#039;</code>
	 * </pre>
	 * @param str xml or html
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-7-12
	 */
	public static String replaceXmlChar(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(str.length() * 2);
		for (int i = 0, len = str.length(); i < len; i++) {
			char c = str.charAt(i);
			if (c < HIGHEST_SPECIAL) {
				char[] escaped = specialCharactersRepresentation[c];
				if (escaped != null) {
					sb.append(escaped);
				} else {
					sb.append(c);
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * replace string block to the specified value.
	 * the string block should wrap with {a-zA-z0-9}, such as: {name}, {helloWorld}
	 * this kind of the string block will not parse, such as: {name a}, {name,a}
	 * examples:
	 * hello {name} + "world" => hello world
	 * </pre>
	 * @param str
	 * @param replaces
	 * @param blank
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-7-12
	 */
	public static String replaceBlock(String str, Map<String, Object> replaces,
			String blank) {
		if (str == null || str.length() == 0) {
			return "";
		}
		int i = 0;
		replaces = replaces == null ? new HashMap<String, Object>() : replaces;
		StringBuffer sb = new StringBuffer(str);
		Matcher matcher = Pattern.compile("(\\{\\w*\\})").matcher(str);
		int offset = 0;
		for (; matcher.find(); i++) {
			String group = matcher.group();
			String substring = group.substring(1, group.length() - 1);
			String replace = replaces.containsKey(substring) ? String
					.valueOf(replaces.get(substring)) : (blank == null ? group
					: blank);
			int start = matcher.start(), end = matcher.end();
			sb.replace(Math.min(start + offset, sb.length()), Math.min(end
					+ offset, sb.length()), replace);
			offset += replace.length() - (end - start);
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * replace string block to the specified value.
	 * the string block should wrap with {a-zA-z0-9}, such as: {name}, {helloWorld}
	 * this kind of the string block will not parse, such as: {name a}, {name,a}
	 * examples:
	 * hello {name} + "world" => hello world
	 * </pre>
	 * @param str
	 * @param replaces
	 * @param blank
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-7-12
	 */
	public static String replaceBlock(String str, Object[] replaces,
			String blank) {
		if (str == null || str.length() == 0) {
			return "";
		}
		int i = 0, len = replaces != null ? replaces.length : 0;
		StringBuffer sb = new StringBuffer(str);
		Matcher matcher = Pattern.compile("(\\{\\w*\\})").matcher(str);
		int offset = 0;
		for (; matcher.find(); i++) {
			String replace = i < len ? String.valueOf(replaces[i])
					: (blank == null ? matcher.group() : blank);
			int start = matcher.start(), end = matcher.end();
			sb.replace(Math.min(start + offset, sb.length()), Math.min(end
					+ offset, sb.length()), replace);
			offset += replace.length() - (end - start);
		}
		return sb.toString();
	}

	public static String pathConcat(String... paths) {
		if (paths == null || paths.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String path : paths) {
			if (path == null) {
				path = "";
			}
			sb.append(path).append(getFileSeparator());
		}
		return pathBuild(sb.toString(), getFileSeparator());
	}

	/**
	 * <pre>
	 * build the beautiful path.
	 * example:
	 * c:\a\\\\b\\\c   =>  c:\a\b\c
	 * c:\a\/b\\\c   =>  c:\a\b\c
	 * /a\/b\\\c   =>  /a/b/c
	 * </pre>
	 * @param str
	 * @param fileSeparator
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-7-12
	 */
	public static String pathBuild(String str, String fileSeparator) {
		if (str == null || str.length() == 0) {
			return "";
		}
		String replace = fileSeparator;
		int i = 0;
		StringBuffer sb = new StringBuffer(str);
		{
			Matcher matcher = Pattern.compile("((\\\\+|/+)+)").matcher(str);
			int offset = 0;
			for (; matcher.find(); i++) {
				int start = matcher.start(), end = matcher.end();
				sb.replace(Math.min(start + offset, sb.length()), Math.min(end
						+ offset, sb.length()), replace);
				offset += replace.length() - (end - start);
			}
		}
		{
			Matcher matcher = Pattern.compile("((\\\\+|/+)+)").matcher(
					sb.toString());
			int offset = 0;
			for (; matcher.find(); i++) {
				int start = matcher.start(), end = matcher.end();
				sb.replace(Math.min(start + offset, sb.length()), Math.min(end
						+ offset, sb.length()), replace);
				offset += replace.length() - (end - start);
			}
		}
		return sb.toString();
	}

	public static String getFileSeparator() {
		return File.separator;
	}

	public static String getNotNullValue(String value) {
		return value == null ? "" : value;
	}

}
