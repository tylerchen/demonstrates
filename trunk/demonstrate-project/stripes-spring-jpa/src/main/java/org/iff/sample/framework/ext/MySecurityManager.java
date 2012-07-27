/***
 * Excerpted from "Stripes: and Java Web Development is Fun Again",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
***/

package org.iff.sample.framework.ext;

import java.lang.reflect.Method;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.iff.sample.business.core.domainmodel.Role;
import org.iff.sample.business.core.domainmodel.User;
import org.iff.sample.web.action.BaseActionBean;
import org.stripesstuff.plugin.security.InstanceBasedSecurityManager;
import org.stripesstuff.plugin.security.SecurityHandler;

/*
START:this
public class MySecurityManager extends J2EESecurityManager {
    @Override
    protected Boolean isUserAuthenticated(ActionBean bean, Method handler) {
        return getUser(bean) != null;
    }
    @Override
    protected boolean hasRole(ActionBean actionBean, Method handler,
        String role)
    {
        User user = getUser(bean);
        if (user != null) {
            Collection<Role> roles = user.getRole();
            return roles != null && roles.contains(new Role(role));
        }
        return false;
    }
    private User getUser(ActionBean bean) {
        return ((BaseActionBean) bean).getContext().getUser();
    }
}
END:this
*/
/*
START:handler
public class MySecurityManager
    extends J2EESecurityManager 
    implements SecurityHandler
{
END:handler
*/

public class MySecurityManager extends InstanceBasedSecurityManager implements
		SecurityHandler {
	@Override
	protected Boolean isUserAuthenticated(ActionBean bean, Method handler) {
		return getUser(bean) != null;
	}

	@Override
	protected Boolean hasRoleName(ActionBean bean, Method handler, String role) {
		User user = getUser(bean);
		if (user != null) {
			Collection<Role> roles = user.getRoles();
			return roles != null && roles.contains(new Role(role));
		}
		return false;
	}

	public Resolution handleAccessDenied(ActionBean bean, Method handler) {
		if (!isUserAuthenticated(bean, handler)) {
			RedirectResolution resolution = new RedirectResolution(
					org.iff.sample.web.action.LoginActionBean.class);
			if (bean.getContext().getRequest().getMethod().equalsIgnoreCase(
					"GET")) {
				String loginUrl = ((BaseActionBean) bean).getLastUrl();
				resolution.addParameter("loginUrl", loginUrl);
			}
			return resolution;
		} else {// need admin role
			User user = getUser(bean);
			boolean isAdmin = false;
			if (user != null && user.getRoles() != null) {
				isAdmin = user.getRoles().contains(Role.createSuperAdmin());
			}
			if (isAdmin) {
				RedirectResolution resolution = new RedirectResolution(
						org.iff.sample.web.action.LoginActionBean.class,
						"viewAdminLogin");
				if (bean.getContext().getRequest().getMethod()
						.equalsIgnoreCase("GET")) {
					String loginUrl = ((BaseActionBean) bean).getLastUrl();
					resolution.addParameter("loginUrl", loginUrl);
				}
				return resolution;
			}
		}
		return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/* ... */

	private User getUser(ActionBean bean) {
		return ((BaseActionBean) bean).getContext().getUser();
	}
}
