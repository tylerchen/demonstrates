/*******************************************************************************
 * Copyright (c) 2012-3-12 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application;

import org.iff.demo.dddallinone.dto.EJBFacadeDTO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-12
 */
public interface EJBFacade {

	EJBFacadeDTO invoke(EJBFacadeDTO dto);

	void refreshMethod();
}
