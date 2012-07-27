/***
 * Excerpted from "Stripes: and Java Web Development is Fun Again",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
***/

package org.iff.sample.framework.ext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.iff.sample.application.core.InitDataService;
import org.iff.sample.business.core.domainmodel.Role;
import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.web.action.BaseActionBean;
import org.iff.sample.web.action.LoginActionBean;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.integration.spring.SpringHelper;


@Intercepts(LifecycleStage.ActionBeanResolution)
public class LoginInterceptor implements Interceptor {
	@SpringBean
	InitDataService initDataService;

	public Resolution intercept(ExecutionContext execContext) throws Exception {
		Resolution resolution = execContext.proceed();
		if (!(execContext.getActionBean() instanceof BaseActionBean)) {
			return resolution;
		}
		MyActionBeanContext ctx = (MyActionBeanContext) execContext
				.getActionBeanContext();
		User user = ctx.getUser();
		BaseActionBean actionBean = (BaseActionBean) execContext
				.getActionBean();
		Class<? extends ActionBean> cls = actionBean.getClass();
		PermitAll permitAll = cls.getAnnotation(PermitAll.class);
		RolesAllowed rolesAllowed = cls.getAnnotation(RolesAllowed.class);
		if (initDataService == null) {
			SpringHelper.injectBeans(this, StripesFilter.getConfiguration()
					.getServletContext());
			initDataService.init();
		}
		if (user == null) {
			if (permitAll == null) {// need to login
				resolution = new RedirectResolution(LoginActionBean.class,
						"view");
				ctx.getMessages().add(
						new SimpleMessage(
								"Please Login or Register to continue."));

				if (ctx.getRequest().getMethod().equalsIgnoreCase("GET")) {
					((RedirectResolution) resolution).addParameter("loginUrl",
							actionBean.getLastUrl());
				}
			} else {
				//do nothing.
			}
		} else if (user != null) {
			if (rolesAllowed != null) {
				Collection<Role> roles = user.getRoles();
				List<String> allows = Arrays.asList(rolesAllowed.value());
				if (allows.isEmpty()) {
					//do nothing
				} else if (roles == null || roles.isEmpty()) {
					resolution = new RedirectResolution(LoginActionBean.class,
							"view");
					ctx.getMessages().add(
							new SimpleMessage(
									"Please Login or Register to continue."));
					if (ctx.getRequest().getMethod().equalsIgnoreCase("GET")) {
						((RedirectResolution) resolution).addParameter(
								"loginUrl", actionBean.getLastUrl());
					}
				} else {
					boolean isHasPermit = false;
					for (String roleName : allows) {
						if (roles.contains(new Role(roleName))) {
							// has a permission.
							isHasPermit = true;
							break;
						}
					}
					if (!isHasPermit) {
						ctx.setUser(null);
						resolution = new RedirectResolution(
								LoginActionBean.class, "view");
						ctx.getMessages().add(
								new SimpleMessage(
										"Please Login as one of role in "
												+ Arrays.toString(rolesAllowed
														.value())));
						if (ctx.getRequest().getMethod()
								.equalsIgnoreCase("GET")) {
							((RedirectResolution) resolution).addParameter(
									"loginUrl", actionBean.getLastUrl());
						}
					}
				}
			}
		}
		return resolution;
	}
}
