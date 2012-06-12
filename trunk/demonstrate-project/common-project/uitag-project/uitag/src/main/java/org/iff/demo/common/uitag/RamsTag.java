package org.iff.demo.common.uitag;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.lang.jstl.ELEvaluator;

public class RamsTag extends BodyTagSupport implements BodyTag {

	private static final long serialVersionUID = -8855003871934108582L;
	private static final String EL_NAME = "_$$_";

	private String role;
	private String entity;

	public static void main(String[] args) {
		RamsTag ramsTag = new RamsTag();
		try {
			System.out.println(ramsTag.evaluate(ramsTag
					.getStrings("!(/index&&test)||!(a||b)"), false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		String string = bodyContent.getString();
		if (bodyContent != null && string != null) {
			boolean hasAuth = false;
			try {
				hasAuth = evaluate(getStrings(role), true)
						&& evaluate(getStrings(entity), false);
			} catch (Exception e) {
				System.out.println("evaluate error: [role=" + role
						+ ", entity=" + entity + "]:" + e.getMessage());
			}
			if (hasAuth) {
				out(pageContext, string);
			}
		}
		return EVAL_PAGE;
	}

	private boolean evaluate(Map<String, String> map, boolean isRole)
			throws Exception {
		if (map.isEmpty()) {
			return true;
		}
		Map<String, Boolean> variable = new HashMap<String, Boolean>();
		for (Entry<String, String> entry : map.entrySet()) {
		}
		ELEvaluator evaluator = new ELEvaluator(new MapVariableResolver());
		Object o = evaluator.evaluate("${" + map.get(EL_NAME) + "}", variable,
				Boolean.class, new HashMap(), null);
		if (o == null || !(o instanceof Boolean)) {
			return false;
		}
		return (Boolean) o;
	}

	private Map<String, String> getStrings(String str) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (str == null || (str = str.trim()).length() < 1) {
			return map;
		}
		char and = str.indexOf(';') > 0 ? ';' : '&';
		StringBuffer el = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '(' || c == ')' || c == '|' || c == and || c == '!'
					|| c == ' ') {
				if (sb.length() > 0) {
					String string = sb.toString();
					String elName = genElName(string);
					map.put(elName, string);
					el.append(elName);
					sb.setLength(0);
				}
				if (c == and) {
					el.append("&&");
					while (el.charAt(el.length() - 1 - 1 - 1) == '&') {
						el.setLength(el.length() - 1);
					}
				} else {
					el.append(c);
				}
			} else {
				sb.append(c);
			}
			if (i + 1 == str.length() && sb.length() > 0) {
				String string = sb.toString();
				String elName = genElName(string);
				map.put(elName, string);
				el.append(elName);
				sb.setLength(0);
			}
		}
		if (el.length() > 0) {
			map.put(EL_NAME, el.toString());
		}
		return map;
	}

	private String genElName(String str) {
		return "_$" + Integer.toHexString(str.hashCode()) + "$_";
	}

	private void out(PageContext pageContext, String out) {
		try {
			JspWriter w = pageContext.getOut();
			w.write(out);
		} catch (Exception e) {
			new JspException(e);
		}
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

}