/* Base Action Bean Class */
/* Author Joaquin Valdez */
package org.iff.sample.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iff.sample.application.core.UserService;
import org.iff.sample.business.core.domainmodel.Role;
import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.framework.ext.MyActionBeanContext;
import org.iff.sample.framework.ext.MyLocalePicker;

@HttpCache(allow = false)
public abstract class BaseActionBean implements ActionBean {
	protected final Log log = LogFactory.getLog(getClass());

	@SpringBean
	UserService userService;

	private MyActionBeanContext context;

	public MyActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (MyActionBeanContext) context;
	}

	@SuppressWarnings("unchecked")
	public String getLastUrl() {
		HttpServletRequest req = getContext().getRequest();
		StringBuilder sb = new StringBuilder();

		// Start with the URI and the path
		String uri = (String) req
				.getAttribute("javax.servlet.forward.request_uri");
		String path = (String) req
				.getAttribute("javax.servlet.forward.path_info");
		if (uri == null) {
			uri = req.getRequestURI();
			path = req.getPathInfo();
		}
		sb.append(uri);
		if (path != null) {
			sb.append(path);
		}

		// Now the request parameters
		sb.append('?');
		Map<String, String[]> map = new HashMap<String, String[]>(req
				.getParameterMap());

		// Remove previous locale parameter, if present.
		map.remove(MyLocalePicker.LOCALE);

		// Append the parameters to the URL
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			for (String value : values) {
				sb.append(key).append('=').append(value).append('&');
			}
		}
		// Remove the last '&'
		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	public User getLoginUser() {
		return getContext().getUser();
	}

	public void setLoginUser(User user) {
		getContext().setUser(user);
	}

	boolean isAdmin() {
		User user = getLoginUser();
		if (user != null && user.getRoles() != null) {
			return user.getRoles().contains(Role.createSuperAdmin());
		}
		return false;
	}

}
