/*******************************************************************************
 * Copyright (c) 2012-2-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.dddallinone.bizmodel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dayatang.domain.AbstractEntity;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-2-7
 */
@Entity
@Table(name = "MY_USER")
public class MyUser extends AbstractEntity {

	private static final long serialVersionUID = -7127544624412922863L;

	@Column(name = "NAME", nullable = false)
	private String name;

	public MyUser() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int hashCode() {
		int result = 31 + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MyUser other = (MyUser) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "MyUser [name=" + name + "]";
	}

	// domain functions
	public static MyUser get(Long id) {
		return get(MyUser.class, id);
	}
}
