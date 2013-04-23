/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.busineess.core.domainmodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.iff.sample.busineess.base.BaseEntity;
import org.iff.sample.busineess.core.domainmodel.infra.jpa.MyTestEntityRepository;
import org.iff.sample.infra.dto.core.MyTestDTO;

import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "itravel_mytest")
@NamedQueries(value = {})
public class MyTestEntity extends BaseEntity {
	@NotNull
	@Column(name = "NAME", nullable = false)
	private String name;
	@Column(name = "age")
	private Integer age;

	public MyTestEntity() {
	}

	private static MyTestEntityRepository myTestEntityRepository;

	private static MyTestEntityRepository getMyTestEntityRepository() {
		if (myTestEntityRepository == null) {
			myTestEntityRepository = InstanceFactory
					.getInstance(MyTestEntityRepository.class);
		}
		return myTestEntityRepository;
	}

	public static List<MyTestEntity> findMyTestByName(String name) {
		return getMyTestEntityRepository().findByName(name);
	}

	public static List<MyTestEntity> findMyTestAll() {
		return getMyTestEntityRepository().findAll();
	}

	//添加
	public static MyTestEntity createMyTest(MyTestDTO wst) {

		//组装
		MyTestEntity mytest = new MyTestEntity();
		mytest.setName(wst.getName());
		mytest.setAge(wst.getAge());
		mytest.save();

		return mytest;
	}

	//删除	
	public static boolean deleteMytest(Long id) {
		boolean result = false;

		/*MyTestEntity mytest = getMyTestEntityRepository().findOne(id);
		if(mytest!=null){
			mytest.remove();
			result=true;
		}*/
		boolean isexists = getMyTestEntityRepository().exists(id);
		if (isexists) {
			getMyTestEntityRepository().delete(id);
			result = true;
		}
		return result;
	}

	//更新
	public static MyTestEntity updateMyTest(MyTestDTO wst) {

		MyTestEntity mytest = getMyTestEntityRepository().findOne((long) 1);
		if (mytest != null) {
			mytest.setAge(wst.getAge());
			mytest = getMyTestEntityRepository().save(mytest);
		}
		return mytest;
	}

	// getter setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
