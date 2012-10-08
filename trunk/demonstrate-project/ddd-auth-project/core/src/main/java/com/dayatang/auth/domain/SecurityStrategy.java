package com.dayatang.auth.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

import com.dayatang.domain.BaseEntity;
import com.dayatang.domain.InstanceFactory;

/**
 * 安全策略
 * 
 * @author chencao
 * 
 */
@Entity
@Table(name = "RAMS_SECURITY_STRATEGY")
public class SecurityStrategy extends BaseEntity {

	public enum PasswordComplexity {
		UNRESTRICTED, NUMERIC, LETTER, NUMERIC_AND_LETTER;
	}

	private static final long serialVersionUID = 7944068499990849578L;

	private static final Character NEED_CAPTCHA_N = 'N';
	private static final Character NEED_CAPTCHA_Y = 'Y';

	private static Integer NEVER_EXPIRED = 0;

	private static Integer UNLIMITED = 0;

	// 密码最小长度
	@Column(name = "PASSWORD_MIN_LENGTH")
	@Range(min = 6, max = 30)
	private Integer passwordMinLength;

	// 密码复杂度
	@Column(name = "PASSWORD_COMPLEXITY")
	private PasswordComplexity passwordComplexity;

	// 密码过期天数，密码过期则强制要求修改密码
	@Column(name = "PASSWORD_EXPIRED_DAYS")
	private Integer passwordExpiredDays = NEVER_EXPIRED;

	// 尝试登陆次数，为0表示无限制
	@Column(name = "LOGIN_TIMES")
	private Integer loginTimes = UNLIMITED;

	// 尝试登陆的时间限制（分钟）
	// 比如若3分钟内，登陆失败5次，则锁定帐户，只有在loginTimes不为0的时候才起作用
	@Column(name = "LOGIN_TIMES_MINUTE_LIMIT")
	private Integer loginTimesMinuteLimit;

	// 帐户自动解锁的小时数，为0表示手工解锁
	@Column(name = "AUTO_UNLOCK_HOURS")
	private Integer autoUnlockHours;

	// 是否需要验证码
	@Column(name = "IS_NEED_CAPTCHA")
	private Character needCaptcha = NEED_CAPTCHA_Y;

	public Integer getPasswordMinLength() {
		return passwordMinLength;
	}

	public void setPasswordMinLength(Integer passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	public PasswordComplexity getPasswordComplexity() {
		return passwordComplexity;
	}

	public void setPasswordComplexity(PasswordComplexity passwordComplexity) {
		this.passwordComplexity = passwordComplexity;
	}

	public Integer getPasswordExpiredDays() {
		return passwordExpiredDays;
	}

	public void setPasswordExpiredDays(Integer passwordExpiredDays) {
		this.passwordExpiredDays = passwordExpiredDays;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Integer getLoginTimesMinuteLimit() {
		return loginTimesMinuteLimit;
	}

	public void setLoginTimesMinuteLimit(Integer loginTimesMinuteLimit) {
		this.loginTimesMinuteLimit = loginTimesMinuteLimit;
	}

	public Integer getAutoUnlockHours() {
		return autoUnlockHours;
	}

	public void setAutoUnlockHours(Integer autoUnlockHours) {
		this.autoUnlockHours = autoUnlockHours;
	}

	public Character getNeedCaptcha() {
		return needCaptcha;
	}

	public void setNeedCaptcha(Character needCaptcha) {
		this.needCaptcha = needCaptcha;
	}

	public boolean equals(Object arg0) {
		if (arg0 == null || !(arg0 instanceof SecurityStrategy)) {
			return false;
		}
		if (arg0 == this) {
			return true;
		}
		SecurityStrategy ss = (SecurityStrategy) arg0;
		return new EqualsBuilder().append(this.getId(), ss.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(id).append(passwordMinLength)
				.append(passwordExpiredDays).append(loginTimesMinuteLimit)
				.append(loginTimes).toHashCode();
	}

	public String toString() {
		return "SecurityStrategy [autoUnlockHours=" + autoUnlockHours
				+ ", loginTimes=" + loginTimes + ", loginTimesMinuteLimit="
				+ loginTimesMinuteLimit + ", needCaptcha=" + needCaptcha
				+ ", passwordComplexity=" + passwordComplexity
				+ ", passwordExpiredDays=" + passwordExpiredDays
				+ ", passwordMinLength=" + passwordMinLength + "]";
	}

	private static SecurityStrategyRepository securityStrategyRepository;

	private static SecurityStrategyRepository getSecurityStrategyRepository() {
		if (securityStrategyRepository == null) {
			securityStrategyRepository = InstanceFactory
					.getInstance(SecurityStrategyRepository.class);
		}
		return securityStrategyRepository;
	}

	public static SecurityStrategy get(Long id) {
		return getSecurityStrategyRepository().get(id);
	}

	@Transient
	public boolean isNeedCaptcha() {
		return NEED_CAPTCHA_Y.equals(getNeedCaptcha());
	}

	public void needCaptcha() {
		setNeedCaptcha(NEED_CAPTCHA_Y);
	}

	public void unNeedCaptcha() {
		setNeedCaptcha(NEED_CAPTCHA_N);
	}

	public static SecurityStrategy load() {
		List<SecurityStrategy> result = getSecurityStrategyRepository()
				.findAll();
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			SecurityStrategy strategy = new SecurityStrategy();
			strategy.setPasswordMinLength(6);
			strategy.needCaptcha();
			strategy.setVersion(0);
			return strategy;
		}
	}

	public void save(SecurityStrategy strategy) {
		getSecurityStrategyRepository().executeUpdate(
				"delete from SecurityStrategy", new Object[0]);
		getSecurityStrategyRepository().save(strategy);
	}

	public void remove() {
		getSecurityStrategyRepository().remove(this);
	}
}
