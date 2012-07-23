/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.webservice.core.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.iff.sample.application.core.MyApplication;
import org.iff.sample.infra.dto.core.MyTestDTO;
import org.iff.sample.webservice.core.MyApplicationWS;
import org.iff.sample.webservice.dto.MyTestWSDTO;


/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@Named("myApplicationWS")
public class MyApplicationWSImpl implements MyApplicationWS {

	@Inject
	MyApplication myApplication;

	public List<MyTestWSDTO> findMyTestByName(String name) {
		List<MyTestDTO> results = myApplication.findMyTestByName(name);
		System.out.println(name);
		try {
			System.out.println(java.net.URLEncoder.encode("我们是中国人", "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		{
			results.add(new MyTestDTO(1L, "我们是中国人", 33));
		}
		List<MyTestWSDTO> list = new ArrayList<MyTestWSDTO>();
		for (MyTestDTO t : results) {
			MyTestWSDTO wst = new MyTestWSDTO();
			try {
				BeanUtils.copyProperties(wst, t);
				list.add(wst);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public List<MyTestWSDTO> findMyTestByNameTest(String name, MyTestWSDTO test) {
		System.out.println(test);
		return findMyTestByName(name);
	}

	public List<MyTestWSDTO> findMyTestByNameTest2(String name, MyTestWSDTO test) {
		System.out.println(test);
		return findMyTestByName(name);
	}

	public List<MyTestWSDTO> findMyTestByNameTest3(String name, MyTestWSDTO test) {
		System.out.println(test);
		return findMyTestByName(name);
	}

	public List<MyTestWSDTO> findMyTestByNameTest4(String name, Integer age,
			String userName) {
		System.out.println("age:" + age + ",userName:" + userName);
		return findMyTestByName(name);
	}

	public List<MyTestWSDTO> findMyTestAll() {
		List<MyTestDTO> results = myApplication.findMyTestAll();
		List<MyTestWSDTO> list = new ArrayList<MyTestWSDTO>();
		for (MyTestDTO t : results) {
			MyTestWSDTO wst = new MyTestWSDTO();
			try {
				BeanUtils.copyProperties(wst, t);
				list.add(wst);
			} catch (Exception e) {
			}
		}
		return list;
	}

	public MyTestWSDTO saveMyTest(String name, int age) {

		MyTestWSDTO wst = new MyTestWSDTO();
		wst.setName(name);
		wst.setAge(age);

		MyTestDTO t = myApplication.createMyTest(wst);
		try {
			BeanUtils.copyProperties(wst, t);
		} catch (Exception e) {

		}

		return wst;
	}

	public boolean deleteMytest(long id) {

		return myApplication.deleteMytest(id);
	}

	public MyTestWSDTO updateMyTest(long id, int age) {

		MyTestWSDTO wst = new MyTestWSDTO();
		//wst.setId(id);
		wst.setAge(age);

		MyTestDTO t = myApplication.updateMyTest(wst);
		try {
			BeanUtils.copyProperties(wst, t);
		} catch (Exception e) {

		}

		return wst;
	}

}
