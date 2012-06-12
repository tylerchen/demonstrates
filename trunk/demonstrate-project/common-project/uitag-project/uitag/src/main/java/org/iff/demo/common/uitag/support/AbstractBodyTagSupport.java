package org.iff.demo.common.uitag.support;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public abstract class AbstractBodyTagSupport extends BodyTagSupport implements
		BodyTag {

	String id;
	String idHash;

	public int doStartTag() throws JspException {
		beforeStartTag();
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		beforeEndTag();
		reset();
		return EVAL_PAGE;
	}

	public abstract void beforeStartTag();

	public abstract void beforeEndTag();

	public abstract void resetField();

	public void reset() {
		resetField();
		id = null;
		idHash = null;
	}

	public void out(PageContext pageContext, String out) {
		try {
			JspWriter w = pageContext.getOut();
			w.write(out);
		} catch (Exception e) {
			new JspException(e);
		}
	}

	public String getIdHash() {
		if (idHash == null) {
			idHash = Integer.toHexString(getId().hashCode());
		}
		return idHash;
	}

	public String getId() {
		if (id == null) {
			id = "uiid_" + Long.toHexString(System.nanoTime());
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}