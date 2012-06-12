package org.iff.demo.common.uitag.form;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.iff.demo.common.uitag.support.AbstractBodyTagSupport;
import org.iff.demo.common.uitag.util.ReflectHelper;
import org.iff.demo.common.uitag.util.StringHelper;

public class FormTag extends AbstractBodyTagSupport {

	private static final long serialVersionUID = -8855003871934108582L;

	private String url;
	private String formClassName;

	public void beforeStartTag() {
	}

	public void beforeEndTag() {
		out(pageContext, genTable());
	}

	private String genTable() {
		String templateTable = "<div class='form-div'><form action='{url}' method='post' class='form-class' id='{id}'><table class='form-table'>{tr}<tr class='form-tr'><td align=right class='form-td-name' colspan=2><input type='submit' name='submit'/></td></tr></table></form></div><script type='text/javascript'>$(function (){$('#{id}').ligerForm();});</script>";
		String templateTr = "<tr class='form-tr'><td class='form-td-name'>{name}:</td><td class='form-td-value'><input type='text' name='{name}' ltype='date' validate='{required:true}'/></td></tr>";
		StringBuilder sb = new StringBuilder(128);
		try {
			Map<String, Method> getters = ReflectHelper.getGetters(Class
					.forName(formClassName));
			for (Entry<String, Method> entry : getters.entrySet()) {
				sb.append(StringHelper.replaceBlock(templateTr, new String[] {
						entry.getKey(), entry.getKey() }, ""));
			}
			return StringHelper.replaceBlock(templateTable, new String[] { url,
					getId(), sb.toString(), getId() }, "");
		} catch (Exception e) {
			sb.append(e);
		}
		return sb.toString();
	}

	public void resetField() {
		url = null;
		formClassName = null;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setFormClassName(String formClassName) {
		this.formClassName = formClassName;
	}

}