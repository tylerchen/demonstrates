/*******************************************************************************
 * Copyright (c) 2012-5-27 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.web.action;

import java.util.Enumeration;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;

import org.iff.sample.application.core.UserService;
import org.iff.sample.business.core.domainmodel.User;
import org.springframework.data.domain.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-27
 */
@StrictBinding
@HttpCache(allow = false)
@UrlBinding("/action/user")
public class UserActionBean extends BaseActionBean implements
		ValidationErrorHandler {

	public static final int RECORD_SIZE = 10;

	private static final String LIST = "/WEB-INF/jsp/user/user_list.jsp";
	private static final String VIEW = "/WEB-INF/jsp/user/user_view.jsp";
	private static final String EDIT = "/WEB-INF/jsp/user/user_edit.jsp";

	@SpringBean
	UserService userService;
	//
	@ValidateNestedProperties( { @Validate(on = { "list" }, field = "userName") })
	User user;
	//
	Page<User> userPage;
	// for current page
	Integer page;
	// for page
	Integer totalCount;

	public Resolution handleValidationErrors(ValidationErrors error)
			throws Exception {
		prepareData(error);
		return null;
	}

	@DefaultHandler
	public Resolution list() {
		userPage = userService.findUser(page, RECORD_SIZE);
		totalCount = (int) userPage.getTotalElements();
		return new ForwardResolution(LIST);
	}

	@DontValidate
	public Resolution cancel() {
		return new RedirectResolution(getClass());
	}

	@DontValidate
	public Resolution cancelChanges() {
		return new RedirectResolution(getClass());
	}

	@ValidationMethod(on = { "view", "toChangeInfomation" })
	public void prepareData(ValidationErrors errors) {
		user = userService.findUserByUserName(getLoginUser().getUserName());
	}

	@ValidationMethod(on = { "list" })
	public void validatePage(ValidationErrors errors) {
		page = 0;
		try {
			Enumeration<?> paramNames = getContext().getRequest()
					.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String name = (String) paramNames.nextElement();
				if (name != null && name.startsWith("d-")
						&& name.endsWith("-p")) {
					String pageValue = getContext().getRequest().getParameter(
							name);
					if (pageValue != null) {
						try {
							page = Integer.parseInt(pageValue) - 1;
							break;
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (page == null) {
			page = 0;
		}
	}

	//
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public Page<User> getUserPage() {
		return userPage;
	}

}
