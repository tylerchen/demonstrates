package com.dayatang.auth.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.auth.core.AuthResourceBundleI18nService;
import com.dayatang.domain.BaseEntity;
import com.dayatang.domain.InstanceFactory;

/**
 * 角色
 * 
 * @author chencao
 * 
 */
@Entity
@Table(name = "RAMS_ROLE")
public class Role extends BaseEntity {

	public enum Status {
		DISABLED, ENABLED
	}

	private static final long serialVersionUID = -4179133191545832397L;

	// 角色名称
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	// 状态
	@Column(name = "STATUS", nullable = false)
	private Status status;

	// 描述
	@Column(name = "DESCRIPTION", length = 1000)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = FunctionEntity.class)
	@JoinTable(name = "RAMS_ROLE_R_FUNCTION_ENTITY", joinColumns = { @JoinColumn(name = "ROLE_ID", insertable = false) }, inverseJoinColumns = { @JoinColumn(name = "FUNCTION_ENTITY_ID", insertable = true) })
	private Set<FunctionEntity> functionEntities = new HashSet<FunctionEntity>();

	public Role() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<FunctionEntity> getFunctionEntities() {
		return functionEntities;
	}

	public void setFunctionEntities(Set<FunctionEntity> functionEntities) {
		this.functionEntities = functionEntities;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Role)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		boolean flag = false;
		if (obj != null && Role.class.isAssignableFrom(obj.getClass())) {
			Role f = (Role) obj;
			flag = new EqualsBuilder().append(getName(), f.getName()).append(
					getId(), f.getId()).isEquals();
		}
		return flag;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getName())
				.toHashCode();
	}

	public String toString() {
		return "Role [description=" + description + ", name=" + name
				+ ", status=" + status + "]";
	}

	/**
	 * 仓储对象
	 */
	private static RoleRepository roleRepository;

	public static RoleRepository getRoleRepository() {
		if (roleRepository == null) {
			roleRepository = InstanceFactory.getInstance(RoleRepository.class);
		}
		return roleRepository;
	}

	/**///////////////////////领域行为 start/////////////////////////////*/

	/**
	 * 创建
	 */
	public Role create() {

		boolean exists = getRoleByName(getName()) != null;

		if (exists) {
			throw new RuntimeException(AuthResourceBundleI18nService
					.getMessage("auth.role.roleIsExist", "当前角色名称已经存在"));
		}
		getRoleRepository().save(this);
		return this;
	}

	public static Role get(Long id) {
		return getRoleRepository().get(id);
	}

	public static Role getRoleByName(String name) {
		List<com.dayatang.domain.Entity> roles = getRoleRepository().find(
				"from Role r where r.name=?", new Object[] { name });
		return (Role) ((roles == null || roles.isEmpty()) ? null : roles
				.iterator().next());
	}

	/**
	 * 更新
	 */
	public Role update() {

		//原始对象
		Role originalRole = getRoleRepository().get(getId());

		//如果没做修改直接返回
		if (originalRole.equals(this)) {
			if (StringUtils.equals(getDescription(), originalRole
					.getDescription()))
				return originalRole;
		}

		List<Role> exists = getRoleRepository().find(
				"from Role r where r.name=? and r.id<>?",
				new Object[] { getName(), getId() });

		if (exists != null && exists.isEmpty() == false) {
			throw new RuntimeException(AuthResourceBundleI18nService
					.getMessage("auth.role.roleIsExist", "当前角色名称已经存在"));
		}

		//		BeanUtils.copyProperties(t, originalRole);
		if (StringUtils.isNotEmpty(getName()))
			originalRole.setName(getName());
		if (StringUtils.isNotEmpty(getDescription()))
			originalRole.setDescription(getDescription());

		getRoleRepository().save(originalRole);
		return originalRole;
	}

	/**
	 * 删除
	 */
	public void delete() {

		List<Account> accounts = Account.getAccountRepository()
				.queryAccountByRole(this);
		if (accounts != null) {
			for (Account account : accounts) {
				Set<Role> roles = account.getRoles();
				for (Role role : roles) {
					if (role.getId().equals(id)) {
						roles.remove(role);
						account.setRoles(roles);
						Account.getAccountRepository().save(account);
						break;
					}
				}
			}
		}
		getRoleRepository().remove(this);
	}

	/**
	 * 状态注销
	 */
	public void disabled() {
		this.setStatus(Status.DISABLED);
		getRoleRepository().save(this);
	}

	/**
	 * 状态激活
	 */
	public void enabled() {
		this.setStatus(Status.ENABLED);
		getRoleRepository().save(this);
	}

	/**
	 * 授权
	 * @return
	 */
	public Role authorize(Set<FunctionEntity> functionEntities) {
		this.setFunctionEntities(functionEntities);
		getRoleRepository().save(this);
		return this;
	}

}
