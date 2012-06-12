/*******************************************************************************
 * Copyright (c) 2012-3-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.iff.demo.dddallinone.dto.EJBFacadeDTO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-3-13
 */
@SuppressWarnings("restriction")
@javax.jws.WebService
public interface WebServiceFacade {

	@WebResult(partName = "result")
	@POST
	@Path("/webServiceFacadeRS")
	EJBFacadeDTO invoke(@WebParam(name = "dto") EJBFacadeDTO dto);
}
