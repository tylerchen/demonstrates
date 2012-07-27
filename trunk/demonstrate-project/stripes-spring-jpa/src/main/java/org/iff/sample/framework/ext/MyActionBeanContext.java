/***
 * My action bean context to store session things.
 * Author: Joaquin Valdez
 *
 ***/

package org.iff.sample.framework.ext;

import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.iff.sample.business.core.domainmodel.User;


import net.sourceforge.stripes.action.ActionBeanContext;

public class MyActionBeanContext extends ActionBeanContext {

	private static final String USER = "LOGINUSER"; //Users.java

	protected void setCurrent(String key, Object value) {
		getRequest().getSession().setAttribute(key, value);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getCurrent(String key, T defaultValue) {
		T value = (T) getRequest().getSession().getAttribute(key);
		if (value == null) {
			value = defaultValue;
			setCurrent(key, value);
		}
		return value;
	}

	public void setUser(User user) {
		setCurrent(USER, user);
	}

	public User getUser() {
		return getCurrent(USER, null);
	}

	public void logout() {
		setUser(null);

		HttpSession session = getRequest().getSession();
		if (session != null) {
			session.invalidate();
		}
	}

	// **************** COOKIE CODE ****************

	public void deleteCookie(String cookieName, String domain) {
		setCookie(cookieName, domain, "Deleted", 0); // time of 0 means delete
	}

	public void setSessionCookie(String cookieName, String cookieData) {
		// let session cookie domain be null so it distinguishes between domains
		setCookie(cookieName, null, cookieData, -1);
	}

	public void setCookie(String cookieName, String domain, String cookieData,
			int durationInSeconds) {
		Cookie cookie = new Cookie(cookieName, cookieData);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setMaxAge(durationInSeconds);
		cookie.setPath(getRequest().getContextPath());
		getResponse().addCookie(cookie);
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}

		return null;
	}

	private static final Pattern ipRegex = Pattern
			.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}.*");

	public String getRootCookieDomain() {
		String requestDomain = getRequest().getServerName();
		if (requestDomain.startsWith("localhost")
				|| ipRegex.matcher(requestDomain).matches()) {
			// localhost shouldn't set the domain, nor should direct ip queries
			return null;
		}

		if (requestDomain.startsWith("my.")) {
			requestDomain = requestDomain.substring(3);
		} else if (requestDomain.startsWith("www.")) {
			requestDomain = requestDomain.substring(4);
		}

		return "." + requestDomain;
	}

}
