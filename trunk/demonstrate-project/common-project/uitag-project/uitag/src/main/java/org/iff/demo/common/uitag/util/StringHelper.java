/*******************************************************************************
 * Copyright (c) 2012-3-23 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.common.uitag.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-23
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

	public static void main(String[] args) {
		System.out.println(replaceBlock(
				"<script type='text/javascript'>{json};{script};</script>",
				new String[] { "11", "{22}" }, null));
		System.out.println(replaceBlock("test{addd},{ccc}{aa}", new String[] {
				"11", "22" }, "33"));
		System.out
				.println(replaceXmlChar("testest>>>>>>>>>><><><><><\"\',dsa.f,dsa.f,.dsa,f.dsaf"));
	}

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
			sb.replace(start + offset, end + offset, replace);
			offset += replace.length() - (end - start);
		}
		return sb.toString();
	}

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
			sb.replace(start + offset, end + offset, replace);
			offset += replace.length() - (end - start);
		}
		return sb.toString();
	}
}
