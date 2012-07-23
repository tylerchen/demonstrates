/*******************************************************************************
 * Copyright (c) 2012-6-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.sample.busineess.base;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-6-2
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity extends com.dayatang.domain.BaseEntity {

	@Transient
	final Logger logger = LoggerFactory.getLogger(getClass());

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
}
