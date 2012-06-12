/*******************************************************************************
 * Copyright (c) 2012-2-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.iff.demo.dddallinone.application.MyUserApplication;
import org.iff.demo.dddallinone.bizmodel.domain.MyUser;
import org.iff.demo.dddallinone.dto.MyUserDTO;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-2-7
 */
@Service("myUserApplication")
@Stateless(name = "MyUserApplicationBean")
@Remote(MyUserApplication.class)
@javax.interceptor.Interceptors(SpringEJBIntercepter.class)
public class MyUserApplicationImpl implements MyUserApplication {

	public MyUserApplicationImpl() {
	}

	public String sayHello(String name) {
		return "Hello " + name;
	}

	public MyUserDTO saveMyUser(MyUserDTO myUsreDTO) {
		MyUser myUser = new MyUser();
		{
			myUser.setId(myUsreDTO.getId());
			myUser.setName(myUsreDTO.getName());
		}
		myUser.save();
		MyUserDTO dto = convert2DTO(myUser);
		return dto;
	}

	public void deleteMyUser(Long myUyserId) {
		MyUser.get(myUyserId).remove();
	}

	public List<MyUserDTO> findAllMyUserDTO() {
		List<MyUser> list = MyUser.findAll(MyUser.class);
		List<MyUserDTO> result = new ArrayList<MyUserDTO>();
		for (MyUser myUser : list) {
			result.add(convert2DTO(myUser));
		}
		return result;
	}

	public MyUserDTO findMyUserById(Long myUserId) {
		return convert2DTO(MyUser.get(myUserId));
	}

	private MyUserDTO convert2DTO(MyUser myUser) {
		MyUserDTO dto = new MyUserDTO();
		{
			dto.setId(myUser.getId());
			dto.setName(myUser.getName());
		}
		return dto;
	}

}
