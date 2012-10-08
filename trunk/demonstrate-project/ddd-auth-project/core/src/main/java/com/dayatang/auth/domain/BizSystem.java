package com.dayatang.auth.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import com.dayatang.domain.BaseEntity;
import com.dayatang.domain.InstanceFactory;

/**
 * 业务系统
 * 
 * @author chencao
 * 
 */
@Entity
@Table(name = "RAMS_BIZ_SYSTEM")
public class BizSystem extends BaseEntity {

	private static final long serialVersionUID = 5746935878201760545L;

	public enum Status {
		DISABLED, ENABLED
	}

	// 业务系统名称
	@Column(name = "NAME", length = 50, nullable = false)
	@NotNull
	@Length(min = 3)
	private String name;

	// 系统生成业务系统的序列号，36位
	@Column(name = "SERIAL_NUMBER", length = 36, nullable = false)
	private String serialNumber;

	// 注册日期
	@Column(name = "REGISTRY_DATE", nullable = false)
	private Date registryDate;

	// 业务系统URL
	@Column(name = "URL", length = 250)
	private String url;

	// 业务系统状态
	@Column(name = "STATUS", nullable = false)
	private Status status;

	// 描述
	@Column(name = "DESCRIPTION", length = 1000)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	/**
	 * lingen.liu 当两个BizSystem的ID一样时，便认为是同一实体
	 */
	public boolean equals(Object arg0) {
		if (arg0 == null || !(arg0 instanceof BizSystem)) {
			return false;
		}
		if (arg0 == this) {
			return true;
		}
		if (arg0 instanceof BizSystem) {
			BizSystem system = (BizSystem) arg0;
			return new EqualsBuilder().append(id, system.id).isEquals();
		}
		return false;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(id).append(name).append(url)
				.toHashCode();
	}

	public String toString() {
		return "BizSystem [description=" + description + ", name=" + name
				+ ", registryDate=" + registryDate + ", serialNumber="
				+ serialNumber + ", status=" + status + ", url=" + url + "]";
	}

	/**
	 * lingen.liu 仓储
	 */
	private static BizSystemRepository bizSystemRepository;

	public static BizSystemRepository getBizSystemRepository() {
		if (bizSystemRepository == null) {
			bizSystemRepository = InstanceFactory
					.getInstance(BizSystemRepository.class);
		}
		return bizSystemRepository;
	}

	public static void setBizSystemRepository(
			BizSystemRepository bizSystemRepository) {
		BizSystem.bizSystemRepository = bizSystemRepository;
	}

	/**
	 * 根据ID获取
	 * @param systemId
	 * @return
	 */
	public static BizSystem get(long systemId) {
		return getBizSystemRepository().get(systemId);
	}

	/**
	 * 一个系统绑定多个帐号
	 * @param systems
	 */
	public void bindingAccounts(String[] accounts) {
		for (String accountId : accounts) {
			Account account = Account.get(Long.parseLong(accountId));
			Set<BizSystemBinding> bizSystemBindings = account
					.getBizSystemBindings();

			//绑定当前帐号与系统
			BizSystemBinding bizSystemBinding = new BizSystemBinding();
			bizSystemBinding.setBizSystem(this);
			bizSystemBinding.setStatus(BizSystemBinding.Status.ENABLED);
			if (!bizSystemBindings.contains(bizSystemBinding)) {
				bizSystemBindings.add(bizSystemBinding);
			}
			account.update();
		}
	}

	/**
	 * 一个系统同多个帐号解除绑定
	 * @param systems
	 */
	public void unBindingAccounts(String[] accounts) {
		for (String accountId : accounts) {
			Account account = Account.get(Long.parseLong(accountId));
			Set<BizSystemBinding> bizSystemBindings = account
					.getBizSystemBindings();

			//绑定当前帐号与系统
			BizSystemBinding bizSystemBinding = new BizSystemBinding();
			bizSystemBinding.setBizSystem(this);
			bizSystemBinding.setStatus(BizSystemBinding.Status.ENABLED);
			if (bizSystemBindings.contains(bizSystemBinding)) {
				bizSystemBindings.remove(bizSystemBinding);
			}
			account.update();
		}
	}

	/**
	 * 创建
	 */
	public void create() {
		this.setRegistryDate(new Date());
		this.setStatus(Status.ENABLED);
		getBizSystemRepository().save(this);
	}

	/**
	 * 保存
	 */
	public void save() {
		getBizSystemRepository().save(this);
	}

	public void remove() {
		getBizSystemRepository().remove(this);
	}
}
