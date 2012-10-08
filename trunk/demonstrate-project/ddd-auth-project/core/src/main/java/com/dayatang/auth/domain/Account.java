package com.dayatang.auth.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.dayatang.domain.BaseEntity;
import com.dayatang.domain.InstanceFactory;

/**
 * 帐户
 * 
 * @author chencao
 * 
 */
@Entity
@Table(name = "RAMS_ACCOUNT")
@NamedQueries( { @NamedQuery(name = "Account.findRoles", query = "select r from Account a join a.roles r where a.id=? order by r.name") })
public class Account extends BaseEntity {

	private static final long serialVersionUID = -321590260221566017L;
	private static final Character LOCK_N = 'N';
	private static final Character LOCK_Y = 'Y';

	// 账户名
	@NotNull
	@Pattern(regexp = "^\\w{2,}$", message = "username not match pattern")
	@Column(name = "USERNAME", length = 25, nullable = false, unique = true)
	private String username;

	// 密码
	@NotNull
	@Length(min = 6, max = 50)
	@Column(name = "PASSWORD", length = 50, nullable = false)
	private String password;

	// 真实姓名
	@Column(name = "REAL_NAME")
	private String realName;

	// 电子邮件
	@Email
	@Column(name = "EMAIL", length = 50, nullable = false)
	private String email;

	// 注册日期
	@NotNull
	@Column(name = "REGISTRY_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registryDate;

	// 是否被锁定
	@NotNull
	@Column(name = "IS_LOCKED", nullable = false)
	private Character locked = LOCK_N;

	// 最后的密码修改日期
	@NotNull
	@Column(name = "PASSWORD_LAST_UPDATE_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date passwordLastUpdateDate;

	// 描述
	@Column(name = "DESCRIPTION", length = 1000)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class)
	@JoinTable(name = "RAMS_ACCOUNT_R_ROLE", joinColumns = { @JoinColumn(name = "ACCOUNT_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;

	@ElementCollection(fetch = FetchType.LAZY, targetClass = BizSystemBinding.class)
	@CollectionTable(name = "RAMS_BIZ_SYSTEM_BINDING", joinColumns = @JoinColumn(name = "ACCOUNT_ID"))
	private Set<BizSystemBinding> bizSystemBindings;

	public Account() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}

	public Character getLocked() {
		return locked;
	}

	public void setLocked(Character locked) {
		this.locked = locked;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPasswordLastUpdateDate() {
		return passwordLastUpdateDate;
	}

	public void setPasswordLastUpdateDate(Date passwordLastUpdateDate) {
		this.passwordLastUpdateDate = passwordLastUpdateDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<BizSystemBinding> getBizSystemBindings() {
		return bizSystemBindings;
	}

	public void setBizSystemBindings(Set<BizSystemBinding> bizSystemBindings) {
		this.bizSystemBindings = bizSystemBindings;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof Account)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o instanceof Account) {
			Account a = (Account) o;
			EqualsBuilder equals = new EqualsBuilder();
			equals.append(id, a.id);
			return equals.isEquals();
		}
		return false;
	}

	public int hashCode() {
		HashCodeBuilder hash = new HashCodeBuilder();
		hash.append(id).append(username);
		return hash.toHashCode();
	}

	public String toString() {
		return "Account [description=" + description + ", email=" + email
				+ ", locked=" + locked + ", password=" + password
				+ ", passwordLastUpdateDate=" + passwordLastUpdateDate
				+ ", realName=" + realName + ", registryDate=" + registryDate
				+ ", username=" + username + "]";
	}

	/**
	 * lingen.liu 仓储
	 */
	private static AccountRepository accountRepository;

	public static AccountRepository getAccountRepository() {
		if (accountRepository == null) {
			accountRepository = InstanceFactory
					.getInstance(AccountRepository.class);
		}
		return accountRepository;
	}

	public static void setAccountRepositoryRepository(
			AccountRepository accountRepository) {
		Account.accountRepository = accountRepository;
	}

	/**/// ////////////////////领域行为 start/////////////////////////////*/

	@Transient
	public boolean isLock() {
		return !LOCK_N.equals(getLocked());
	}

	public void lock() {
		setLocked(LOCK_Y);
	}

	public void unlock() {
		setLocked(LOCK_N);
	}

	/**
	 * 新增帐户
	 */
	public void add() {
		unlock();
		this.setRegistryDate(new Date());
		this.setPasswordLastUpdateDate(new Date());
		setPassword(getAccountRepository().encodePassword(getPassword()));
		getAccountRepository().save(this);
	}

	/**
	 * 删除帐户
	 */
	public void remove() {
		this.setRoles(null);
		this.setBizSystemBindings(null);
		update();
		getAccountRepository().remove(this);
	}

	/**
	 * 更新一个帐户
	 */
	public void update() {
		getAccountRepository().save(this);
	}

	/**
	 * 锁定帐户
	 */
	public void disabled() {
		if (!isLock()) {
			lock();
			getAccountRepository().save(this);
		}
	}

	/**
	 * 解锁帐户
	 */
	public void enabled() {
		if (isLock()) {
			unlock();
			getAccountRepository().save(this);
		}
	}

	public boolean changePassword(String oldPassword, String newPassword) {
		if (oldPassword == null || newPassword == null) {
			return false;
		}
		if (getAccountRepository().isPasswordValid(getPassword(), oldPassword)) {
			setPassword(getAccountRepository().encodePassword(newPassword));
			setPasswordLastUpdateDate(new Date());
			return true;
		}
		return false;
	}

	public boolean validatePassword(String password) {
		if (password == null) {
			return false;
		}
		return getAccountRepository().isPasswordValid(getPassword(), password);
	}

	/**
	 * 绑定系统
	 */
	public void bindBizSystem(Set<BizSystemBinding> bizSystemBindings) {
		this.setBizSystemBindings(bizSystemBindings);
		getAccountRepository().save(this);
	}

	/**
	 * 通过Id获取一个帐号
	 * @param id
	 * @return
	 */
	public static Account get(Long id) {
		return getAccountRepository().get(id);
	}

	/**
	 * 检测是否已存在当前邮箱
	 * @return
	 */
	public static boolean isEmailExist(String email) {
		return getAccountRepository().isEmailExist(email);
	}

	/**
	 * 检测是否存在当前用户名
	 * @return
	 */
	public static boolean isUsernameExist(String username) {
		return getAccountRepository().isUsernameExist(username);
	}

	/**
	 * 一个帐号绑定多个系统
	 * @param systems
	 */
	public void bindingBizSystems(String[] systems) {
		Set<BizSystemBinding> bizSystemBindings = getBizSystemBindings();
		for (String systemId : systems) {
			//获取要绑定的系统
			BizSystem bizSystem = BizSystem.get(Long.parseLong(systemId));

			//绑定当前帐号与系统
			BizSystemBinding bizSystemBinding = new BizSystemBinding();
			bizSystemBinding.setBizSystem(bizSystem);
			bizSystemBinding.setStatus(BizSystemBinding.Status.ENABLED);
			if (!bizSystemBindings.contains(bizSystemBinding)) {
				bizSystemBindings.add(bizSystemBinding);
			}
		}
		setBizSystemBindings(bizSystemBindings);
		update();
	}

	/**
	 * 一个帐号同多个系统解除绑定
	 * @param systems
	 */
	public void unBindingBizSystems(String[] systems) {
		Set<BizSystemBinding> bizSystemBindings = getBizSystemBindings();
		for (String systemId : systems) {
			//获取要绑定的系统
			BizSystem bizSystem = BizSystem.get(Long.parseLong(systemId));

			//绑定当前帐号与系统
			BizSystemBinding bizSystemBinding = new BizSystemBinding();
			bizSystemBinding.setBizSystem(bizSystem);
			bizSystemBinding.setStatus(BizSystemBinding.Status.ENABLED);
			if (bizSystemBindings.contains(bizSystemBinding)) {
				bizSystemBindings.remove(bizSystemBinding);
			}
		}
		setBizSystemBindings(bizSystemBindings);
		update();
	}

	/**
	 * 设置某个帐号关联的某个系统的可用性
	 * @param systemId
	 * @param status
	 */
	public void setSystemStatus(long systemId, BizSystemBinding.Status status) {
		Set<BizSystemBinding> bizSystemBindings = getBizSystemBindings();
		for (BizSystemBinding bizSystemBinding : bizSystemBindings) {
			BizSystem bizSystem = bizSystemBinding.getBizSystem();
			if (bizSystem.getId().equals(systemId)) {
				if (!bizSystemBinding.getStatus().equals(status)) {
					bizSystemBinding.setStatus(status);
				}
			}
		}
		update();
	}

	/**
	 * 根据用户名获取帐号信息
	 * @param username
	 * @return
	 */
	public static Account get(String username) {
		return getAccountRepository().get(username);
	}

	public List<Role> findRoles() {
		return getAccountRepository().findByNamedQuery("Account.findRoles",
				new Object[] { getId() });
	}
}
