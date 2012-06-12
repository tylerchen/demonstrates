package org.iff.demo.common.uitag.grid;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.iff.demo.common.uitag.support.AbstractBodyTagSupport;
import org.iff.demo.common.uitag.util.ReflectHelper;
import org.iff.demo.common.uitag.util.StringHelper;

import com.google.gson.Gson;

public class GridTag extends AbstractBodyTagSupport {

	private static final long serialVersionUID = -8855003871934108582L;

	private Object data;

	public void beforeStartTag() {
	}

	public void beforeEndTag() {
		String string = "";
		String script = genGridScript(data);
		if (bodyContent != null && (string = bodyContent.getString()) != null) {
			out(pageContext, string + "=" + data);
		} else {
			out(pageContext, script);
		}
	}

	public void resetField() {
		data = null;
	}

	private String genGridScript(Object data) {
		String script = "<div id='{id0}'>aaaaa</div><script type='text/javascript'>var data_{idHashCode1}={Rows:{rows2},Total:{total3}};function gridFunc_{idHashCode4}(){/*grid start*/$('#{id5}').ligerGrid({columns:[{columns6}],data:data_{idHashCode7},height:300,pageSize:10});/*grid end*/};$(function(){setTimeout(gridFunc_{idHashCode8},100);});</script>";
		String header = "{display:'{display}',name:'{name}'},";
		StringBuilder columns = new StringBuilder(128);
		int size = -1;
		if (data == null) {
		} else if (data instanceof List<?>) {
			List<?> list = (List<?>) data;
			size = list.size();
			if (!list.isEmpty()) {
				Map<String, Method> getters = ReflectHelper.getGetters(list
						.get(0).getClass());
				for (Entry<String, Method> entry : getters.entrySet()) {
					columns.append(StringHelper
							.replaceBlock(header, new String[] {
									entry.getKey(), entry.getKey() }, ""));
				}
				columns.setLength(columns.length() - 1);
			}
		}
		if (size < 0) {
			size = 0;
		}
		if (columns.length() < 1) {
			columns.append("{display:'column',name:'column'}");
		}
		return StringHelper.replaceBlock(script, new String[] { getId(),
				getIdHash(), new Gson().toJson(data), String.valueOf(size),
				getIdHash(), getId(), columns.toString(), getIdHash(),
				getIdHash() }, "");
	}

	public void setData(Object data) {
		this.data = data;
	}
}