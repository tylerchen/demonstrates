/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.modulea.application;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foreveross.demo.modulea.application.vo.DemoVO;
import com.foreveross.infra.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@Produces( { MediaType.APPLICATION_JSON })
public interface DemoApplication {

	@GET
	@Path("/getAccountVO/{accountId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,
			MediaType.TEXT_PLAIN })
	DemoVO getAccountVO(@PathParam("accountId") String accountId);

	@GET
	@Path("/pageFindAllAccount")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,
			MediaType.TEXT_PLAIN })
	Page pageFindAllAccount();

	@POST
	@Path("/saveAccount")
	DemoVO saveAccount(DemoVO vo);

	@DELETE
	@Path("/removeAccount/{accountId}")
	@Consumes( { MediaType.MULTIPART_FORM_DATA })
	void removeAccount(@PathParam("accountId") String accountId);

	@POST
	@Path("/pageFindAccount")
	@Consumes( { MediaType.MULTIPART_FORM_DATA })
	Page pageFindAccount(Page pages, String username);

	@POST
	@Path("/findAll")
	@Consumes( { MediaType.MULTIPART_FORM_DATA })
	List<DemoVO> findAll(Page pages, String username);
}
