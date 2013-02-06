/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.iff.demo.application.DemoApplication;
import org.iff.demo.biz.core.domain.User;
import org.iff.demo.biz2.core.domain.Role;
import org.iff.demo.vo.biz.UserVO;
import org.iff.demo.vo.biz2.RoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@Named
@Transactional
public class DemoApplicationImpl implements DemoApplication {

	@Override
	public RoleVO addRole(RoleVO role) {
		Role r = new Role();
		{
			r.setName(role.getName());
		}
		r.save();
		RoleVO result = new RoleVO();
		{
			result.setId(r.getId());
			result.setName(r.getName());
		}
		return result;
	}

	@Override
	public UserVO addUser(UserVO user) {
		User u = new User();
		{
			u.setUserName(user.getUserName());
		}
		u.save();
		UserVO result = new UserVO();
		{
			result.setId(u.getId());
			result.setUserName(u.getUserName());
		}
		return result;
	}

	@Override
	public void removeRole(Long roleId) {
		Role r = new Role();
		{
			r.setId(roleId);
		}
		r.remove();
	}

	@Override
	public void removeUser(Long userId) {
		User u = User.get(User.class, userId);
		u.remove();
	}

	@Override
	public List<UserVO> findUserByUserName(String userName) {
		List<UserVO> vos = new ArrayList<UserVO>();
		List<User> list = User.findByUserName(userName);
		for (User u : list) {
			UserVO vo = new UserVO();
			vo.setId(u.getId());
			vo.setUserName(u.getUserName());
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public List<UserVO> findAllUserPageable(int page, int size) {
		PageRequest pr = new PageRequest(page, size);
		List<UserVO> vos = new ArrayList<UserVO>();
		Page<User> list = User.findPageable(pr);
		for (User u : list.getContent()) {
			UserVO vo = new UserVO();
			vo.setId(u.getId());
			vo.setUserName(u.getUserName());
			vos.add(vo);
		}
		return vos;
	}

}
