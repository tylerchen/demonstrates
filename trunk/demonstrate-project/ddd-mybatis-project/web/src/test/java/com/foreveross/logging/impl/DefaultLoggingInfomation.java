/*******************************************************************************
 * Copyright (c) 2013-8-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.impl;

import java.util.Date;

import com.foreveross.logging.LoggingInfomation;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-13
 */
public class DefaultLoggingInfomation implements LoggingInfomation {

	private static final long serialVersionUID = 7691822011671162974L;

	private String id;

	private String operator;

	private String loggingStatus;

	private String loggingType;

	private Date loggingTime;

	private String detail;

	public DefaultLoggingInfomation() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getLoggingStatus() {
		return loggingStatus;
	}

	public void setLoggingStatus(String loggingStatus) {
		this.loggingStatus = loggingStatus;
	}

	public String getLoggingType() {
		return loggingType;
	}

	public void setLoggingType(String loggingType) {
		this.loggingType = loggingType;
	}

	public Date getLoggingTime() {
		return loggingTime;
	}

	public void setLoggingTime(Date loggingTime) {
		this.loggingTime = loggingTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String toString() {
		return "DefaultLoggingInfomation [detail=" + detail + ", id=" + id
				+ ", loggingStatus=" + loggingStatus + ", loggingTime="
				+ loggingTime + ", loggingType=" + loggingType + ", operator="
				+ operator + "]";
	}
}
