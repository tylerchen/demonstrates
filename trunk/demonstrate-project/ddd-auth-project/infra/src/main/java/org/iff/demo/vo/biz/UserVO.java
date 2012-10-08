/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.vo.biz;

import java.io.Serializable;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@SuppressWarnings("serial")
public class UserVO implements Serializable {

	private Long id;

	private String userName;

	public UserVO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + "]";
	}

}
