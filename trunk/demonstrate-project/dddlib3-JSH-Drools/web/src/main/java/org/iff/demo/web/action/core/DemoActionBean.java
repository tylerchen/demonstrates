/*******************************************************************************
 * Copyright (c) 2012-10-8 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.web.action.core;

import java.io.Serializable;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.iff.demo.application.DemoApplication;
import org.iff.demo.vo.biz.UserVO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-10-8
 */
@SuppressWarnings("serial")
@Named("demoActionBean")
@ManagedBean
@SessionScoped
public class DemoActionBean implements Serializable {

	@Inject
	DemoApplication demoApplication;

	public String hello() {
		String hello = "";
		{
			UserVO vo = new UserVO();
			vo.setUserName("hello");
			demoApplication.addUser(vo);
		}
		hello = Arrays.toString(demoApplication.findAllUserPageable(0, 10)
				.toArray());
		hello = hello + "<br/>";
		hello = hello
				+ Arrays.toString(demoApplication.findUserByUserName("hello")
						.toArray());
		return hello;
	}

	public static void main(String[] args) {
		try {
			Class<?> elc = Class.forName("javax.el.ELResolver");
			String info = elc.getProtectionDomain().getCodeSource()
					.getLocation().toExternalForm();
			System.out.println("info = " + info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
