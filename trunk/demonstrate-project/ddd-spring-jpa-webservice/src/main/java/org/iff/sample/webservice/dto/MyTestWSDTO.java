/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.webservice.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.iff.sample.infra.dto.core.MyTestDTO;


/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@SuppressWarnings("serial")
@XmlRootElement(name = "MyTestWSDTO")
public class MyTestWSDTO extends MyTestDTO {

	@XmlElement(name = "age")
	@Override
	public Integer getAge() {
		return super.getAge();
	}

	@XmlElement(name = "name")
	@Override
	public String getName() {
		return super.getName();
	}

	/*@XmlElement(name = "id")
	@Override
	public Long getId() {
		return super.getId();
	}*/

}
