package com.dayatang.auth.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.ValueObject;

/**
 * 业务系统绑定（值对象）
 * 
 * @author chencao
 * 
 */
@Embeddable
public class BizSystemBinding implements ValueObject {

	public enum Status {
		DISABLED, ENABLED
	}

	private static final long serialVersionUID = 3373709742481197580L;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = BizSystem.class)
	@JoinColumn(name = "FK_BIZ_SYSTEM_ID")
	private BizSystem bizSystem;

	@Column(name = "STATUS")
	private Status status;

	public BizSystem getBizSystem() {
		return bizSystem;
	}

	public void setBizSystem(BizSystem bizSystem) {
		this.bizSystem = bizSystem;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BizSystemBinding)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof BizSystemBinding) {
			BizSystemBinding system = (BizSystemBinding) obj;
			if (system.getBizSystem().equals(this.getBizSystem())) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(bizSystem).append(status)
				.toHashCode();
	}

	public String toString() {
		return "BizSystemBinding [bizSystem="
				+ (bizSystem == null ? null : bizSystem.getName())
				+ ", status=" + status + "]";
	}
}
