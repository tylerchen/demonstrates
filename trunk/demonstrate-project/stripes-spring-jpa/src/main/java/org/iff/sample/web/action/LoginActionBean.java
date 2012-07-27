/*******************************************************************************
 * Copyright (c) 2012-5-5 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.web.action;

import javax.annotation.security.PermitAll;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import org.iff.sample.application.core.UserService;
import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.framework.converter.PasswordTypeConverter;
import org.iff.sample.framework.ext.CaptchaManager;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-5
 */
@StrictBinding(allow = "loginUrl")
@HttpCache(allow = false)
@UrlBinding("/action/login")
@PermitAll
public class LoginActionBean extends BaseActionBean implements
		ValidationErrorHandler {
	private static final String DONE = "/WEB-INF/jsp/user/welcome.jsp";
	private static final String VIEW = "/WEB-INF/jsp/login/login_form.jsp";

	@SpringBean
	UserService userService;

	@ValidateNestedProperties( {
			@Validate(on = "login", field = "userName", required = true),
			@Validate(on = "login", field = "password", required = true, converter = PasswordTypeConverter.class) })
	User user;
	//@Validate(on = "login", required = true)
	String captcha;
	// Come from: http://localhost:8080/action/login;jsessionid=11lj2clnhl7um?view=&loginUrl=%2Faction%2Fsiteadmin%2Fsiteadmin&__fsk=-1179520577
	String loginUrl;

	public Resolution handleValidationErrors(ValidationErrors errors)
			throws Exception {
		System.out.println(errors);
		return null;
	}

	@DefaultHandler
	@DontValidate
	public Resolution view() {
		if (getContext().getUser() != null) {
			return new RedirectResolution(getClass(), "success");
		}
		if (getContext().getRequest().isSecure()) {
			return new ForwardResolution(VIEW);
		} else {
			return new ForwardResolution(VIEW);
		}
	}

	/**
	 * for common use login
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2012-6-19
	 */
	public Resolution login() {
		if (loginUrl != null && loginUrl.indexOf("/action/login") < 0) {
			return new RedirectResolution(loginUrl, false);
		} else {
			return new RedirectResolution(UserActionBean.class);
		}
	}

	public Resolution logout() {
		setLoginUser(null);
		return new RedirectResolution(getClass(), "view");
	}

	@DontValidate
	public Resolution success() {
		return new ForwardResolution(DONE);
	}

	@DontValidate
	public Resolution cancel() {
		return new RedirectResolution(getClass());
	}

	@ValidationMethod(on = { "login" }, priority = 100)
	public void verifyLogin(ValidationErrors errors) {
		/*if (!validateCaptcha(errors)) {
			return;
		}*/
		User login = userService.login(user.getUserName(), user.getPassword());
		if (login == null || !login.getPassword().equals(user.getPassword())) {
			errors.add("login.userNameNotFound.error", new SimpleError(
					"User name or password incorrect!"));
		}
		setLoginUser(login);
	}

	@SuppressWarnings("unused")
	private boolean validateCaptcha(ValidationErrors errors) {
		String captchaId = getContext().getRequest().getSession().getId();
		boolean isResponseCorrect = CaptchaManager.get().validateResponseForID(
				captchaId, captcha);
		if (!isResponseCorrect) {
			errors.add("register.captcha.error", new SimpleError(
					"Captcha code missmatch!"));
			return false;
		}
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
