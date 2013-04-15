/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.demo.sys.application;

import java.util.List;

import javax.jws.WebService;

import com.foreveross.demo.sys.application.vo.DemoVO;
import com.foreveross.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@WebService
public interface DemoApplication {

	DemoVO getAccountVO(String accountId);

	DemoVO saveAccount(DemoVO vo);

	void removeAccount(String accountId);

	Page pageFindAccount(Page pages, String username);

	List<DemoVO> findAll(Page pages, String username);
}
