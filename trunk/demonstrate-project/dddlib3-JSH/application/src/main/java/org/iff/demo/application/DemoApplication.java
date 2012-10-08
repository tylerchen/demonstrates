/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application;

import java.util.List;

import org.iff.demo.vo.biz.UserVO;
import org.iff.demo.vo.biz2.RoleVO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
public interface DemoApplication {

	UserVO addUser(UserVO user);

	void removeUser(Long userId);

	RoleVO addRole(RoleVO role);

	void removeRole(Long roleId);

	List<UserVO> findAllUserPageable(int page, int size);

	List<UserVO> findUserByUserName(String userName);
}
