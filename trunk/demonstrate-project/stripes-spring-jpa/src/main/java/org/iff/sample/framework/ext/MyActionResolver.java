/*******************************************************************************
 * Copyright (c) 2012-5-31 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.framework.ext;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.NameBasedActionResolver;
import net.sourceforge.stripes.exception.StripesServletException;

/**
 * For replicated website access.
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-5-31
 */
public class MyActionResolver extends NameBasedActionResolver {

	public ActionBean getActionBean(ActionBeanContext context, String urlBinding)
			throws StripesServletException {
		return super.getActionBean(context, urlBinding);
	}

	public Class<? extends ActionBean> getActionBeanType(String path) {
		Class<? extends ActionBean> beanType = super.getActionBeanType(path);
		//		if (path != null && path.indexOf("/action/") == 0
		//				&& (beanType == SiteAction.class)) {
		//			System.out
		//					.println("FIXME: THIS IS CAN'T BE HAPPEND! A '/action/*' path map to ReplicatingSiteAction.class!");
		//			return null;
		//		}
		//		if (beanType == null || beanType == SiteAction.class) {
		//			if (SiteAction.matchUrl(path)) {
		//				return SiteAction.class;
		//			} else {
		//				return null;
		//			}
		//		}
		return beanType;
	}
}
