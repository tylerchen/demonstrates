/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.foreveross.demo.sys.application.vo.DemoVO;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@Produces( { MediaType.APPLICATION_XML })
public interface DemoApplication {

	@GET
	@Path("/getAccountVO/{accountId}")
	DemoVO getAccountVO(@PathParam("accountId") String accountId);

	@POST
	@Path("/saveAccount")
	DemoVO saveAccount(DemoVO vo);

	@DELETE
	@Path("/removeAccount/{accountId}")
	void removeAccount(@PathParam("accountId") String accountId);

	@POST
	@Path("/pageFindAccount")
	Page pageFindAccount(Page pages, String username);

	@POST
	@Path("/findAll")
	List<DemoVO> findAll(Page pages, String username);
}
