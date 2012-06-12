/*******************************************************************************
 * Copyright (c) 2012-2-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.application;

import java.util.List;

import org.iff.demo.dddallinone.dto.MyUserDTO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-2-7
 */
public interface MyUserApplication {

	String sayHello(String name);

	MyUserDTO saveMyUser(MyUserDTO myUsreDTO);

	void deleteMyUser(Long myUyserId);

	List<MyUserDTO> findAllMyUserDTO();
	
	MyUserDTO findMyUserById(Long myUserId);
}
