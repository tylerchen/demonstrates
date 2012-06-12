/*******************************************************************************
 * Copyright (c) 2011-11-15 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.common.uitag;

import java.util.Map;

import org.apache.taglibs.standard.lang.jstl.ELException;
import org.apache.taglibs.standard.lang.jstl.VariableResolver;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2011-11-15
 */
public class MapVariableResolver implements VariableResolver {

	public Object resolveVariable(String pName, Object pContext)
			throws ELException {
		if (pContext == null || !(pContext instanceof Map)) {
			return null;
		}
		Map map = (Map) pContext;
		return map.get(pName);
	}

}
