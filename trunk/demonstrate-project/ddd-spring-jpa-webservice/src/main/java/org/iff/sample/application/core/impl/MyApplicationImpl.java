/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.application.core.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.iff.sample.application.core.MyApplication;
import org.iff.sample.busineess.core.domainmodel.MyTestEntity;
import org.iff.sample.infra.dto.core.MyTestDTO;
import org.iff.sample.webservice.dto.MyTestWSDTO;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@Named("myApplication")
@Transactional
public class MyApplicationImpl implements MyApplication {

	public List<MyTestDTO> findMyTestByName(String name) {
		List<MyTestDTO> list = new ArrayList<MyTestDTO>();
		List<MyTestEntity> results = MyTestEntity.findMyTestByName(name);
		for (MyTestEntity t : results) {
			list.add(new MyTestDTO(t.getId(), t.getName(), t.getAge()));
		}
		return list;
	}
	public List<MyTestDTO> findMyTestAll(){
		List<MyTestDTO> list = new ArrayList<MyTestDTO>();
		List<MyTestEntity> results = MyTestEntity.findMyTestAll();
		for (MyTestEntity t : results) {
			list.add(new MyTestDTO(t.getId(), t.getName(), t.getAge()));
		}
		return list;
	}
	
	//增加
	public MyTestDTO createMyTest(MyTestWSDTO wst) {
		
		MyTestDTO mytest = new MyTestDTO();
		try {
			BeanUtils.copyProperties(mytest,wst);
		} catch (Exception e) {
			
		}
		
		MyTestEntity mytestentity = MyTestEntity.createMyTest(mytest);
		
		//组装返回
		MyTestDTO result = new MyTestDTO(mytestentity.getId(),mytestentity.getName(),mytestentity.getAge());
		
		
		return result;
	}
	
	//删除
	public boolean deleteMytest(long id){
		
		boolean result= MyTestEntity.deleteMytest(id);
		
		return result;		
	}
	
	//修改
	public MyTestDTO updateMyTest(MyTestWSDTO wst){
		
		MyTestDTO mytest = new MyTestDTO();
		try {
			BeanUtils.copyProperties(mytest,wst);
		} catch (Exception e) {
			
		}
		
		MyTestEntity mytestentity = MyTestEntity.updateMyTest(mytest);
		//组装返回
		MyTestDTO result = new MyTestDTO(mytestentity.getId(),mytestentity.getName(),mytestentity.getAge());
		return result;
		
	}

}
