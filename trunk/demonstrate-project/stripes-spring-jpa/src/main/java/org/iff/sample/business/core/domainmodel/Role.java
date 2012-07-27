/***
 * Roles Model Base class.
 * Author: Joaquin Valdez
 *
***/
package org.iff.sample.business.core.domainmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.iff.sample.business.base.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "sample_role", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class Role extends BaseEntity {
	public static final String ROLE_SUPER_ADMIN = "SuperAdmin";
	public static final String ROLE_PUBLIC = "Public";

	@Column(name = "name")
	private String name;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public static Role createSuperAdmin() {
		return new Role(ROLE_SUPER_ADMIN);
	}

	public static Role createPublic() {
		return new Role(ROLE_PUBLIC);
	}

	//getter and setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object object) {
		try {
			return name.equals(((Role) object).getName());
		} catch (Exception exc) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}
