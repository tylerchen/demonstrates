/*******************************************************************************
 * Copyright (c) 2012-3-27 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.common.uitag.accordion;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.iff.demo.common.uitag.support.AbstractBodyTagSupport;
import org.iff.demo.common.uitag.util.StringHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-27
 */
public class AccordionTag extends AbstractBodyTagSupport {

	private static final long serialVersionUID = 3095665062200143780L;

	private String title;
	private Object value;

	public void beforeStartTag() {
		Map<String, String> map = (Map<String, String>) pageContext
				.findAttribute("AccordionTag");
		if (map == null) {
			map = new LinkedHashMap<String, String>();
			pageContext.setAttribute("AccordionTag", map);
		}
		map.put(getId(), null);
	}

	public void beforeEndTag() {
		Map<String, String> map = (Map<String, String>) pageContext
				.findAttribute("AccordionTag");
		map.put(getId(), genAccordion());
		if (map.containsValue(null)) {
			return;
		} else {
			String template = "<div id='{id}'>{content}</div><script type='text/javascript'>$(function(){$('#{id}').ligerAccordion({height:300});});</script>";
			String output = "";
			int i = 0;
			String id = "";
			StringBuilder sb = new StringBuilder(128);
			if (map.size() == 1) {
				id = getIdHash();
				for (Entry<String, String> entry : map.entrySet()) {
					output = StringHelper.replaceBlock(template, new String[] {
							getIdHash(), entry.getValue(), id }, "");
				}
			} else {
				for (Entry<String, String> entry : map.entrySet()) {
					if (i++ == 0) {
						id = entry.getKey();
					} else {
						sb.append(entry.getValue());
					}
				}
				output = StringHelper.replaceBlock(template, new String[] { id,
						sb.toString(), id }, "");
			}
			out(pageContext, output);
		}
	}

	public void resetField() {
		title = null;
		value = null;
		Map<String, String> map = (Map<String, String>) pageContext
				.findAttribute("AccordionTag");
		if (!map.containsValue(null)) {
			map.clear();
			pageContext.removeAttribute("AccordionTag");
		}
	}

	private String genAccordion() {
		String template = "<div title='{title}' id='{id}'><ul>{values}</ul></div>";
		String templateUL = "<li>{value}</li>";
		StringBuilder sb = new StringBuilder(128);
		if (value == null) {
			return StringHelper.replaceBlock(template, new String[] {
					StringHelper.replaceXmlChar(title), getId(), "" }, "");
		} else if (value instanceof String || value instanceof Object) {
			String[] split = String.valueOf(value).split(";");
			for (String s : split) {
				sb.append(StringHelper.replaceBlock(templateUL,
						new String[] { StringHelper.replaceXmlChar(s) }, ""));
			}
		} else if (value instanceof String[]) {
			for (String s : ((String[]) value)) {
				sb.append(StringHelper.replaceBlock(templateUL,
						new String[] { StringHelper.replaceXmlChar(s) }, ""));
			}
		} else if (value instanceof Object[]) {
			for (Object o : ((Object[]) value)) {
				sb.append(StringHelper.replaceBlock(templateUL,
						new String[] { StringHelper.replaceXmlChar(String
								.valueOf(o)) }, ""));
			}
		}
		return StringHelper.replaceBlock(template, new String[] {
				StringHelper.replaceXmlChar(title), getId(), sb.toString() },
				"");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
