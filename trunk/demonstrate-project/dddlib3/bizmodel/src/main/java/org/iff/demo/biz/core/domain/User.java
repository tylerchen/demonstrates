/*******************************************************************************
 * Copyright (c) 2012-9-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.biz.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.iff.demo.biz.core.domain.infra.jpa.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dayatang.domain.BaseEntity;
import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-9-11
 */
@SuppressWarnings("serial")
@Entity
@Table
public class User extends BaseEntity {

	@Column(name = "USERNAME")
	private String userName;

	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		return new EqualsBuilder().append(getId(), ((User) obj).getId())
				.isEquals();
	}

	static UserRepository getUserRepository() {
		return InstanceFactory.getInstance(UserRepository.class);
	}

	//
	public static List<User> findByUserName(String userName) {
		return getUserRepository().findByUserName(userName);
	}

	public static Page<User> findPageable(Pageable pageable) {
		return getUserRepository().findAll(pageable);
	}

}
