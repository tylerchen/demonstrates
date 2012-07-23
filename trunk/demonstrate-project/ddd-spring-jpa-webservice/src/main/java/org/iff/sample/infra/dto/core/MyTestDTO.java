/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.infra.dto.core;

import org.iff.sample.infra.dto.base.BaseDTO;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
public class MyTestDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;

	public MyTestDTO() {
	}

	public MyTestDTO(Long id, String name, Integer age) {
		//setId(id);
		this.name = name;
		this.age = age;
	}

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
