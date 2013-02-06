/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application;


import org.iff.demo.application.vo.AccountVO;
import org.iff.demo.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
public interface DemoApplication {

	AccountVO getAccountVO(String accountId);

	AccountVO saveAccount(AccountVO vo);

	void removeAccount(String accountId);

	Page pageFindAccount(Page pages, String username);
}
