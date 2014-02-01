/*******************************************************************************
 * Copyright (c) 2014-1-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.hnair.edms.common.alarm;


/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-1-26
 */
public final class AlarmConstant {

	/** 完工状态同步预警 **/
	public static final String ALARM_TYPE_WORK_SYNC = "WORK_SYNC";
	/** 竣工情况预警 **/
	public static final String ALARM_TYPE_WORK_FINISH = "WORK_FINISH";
	/** 实际构型同步异常预警 **/
	public static final String ALARM_TYPE_FLYAWAY_SYNC = "FLYAWAY_SYNC";
	/** 证书过期预警 **/
	public static final String ALARM_TYPE_CA_EXPIRED = "CA_EXPIRED";
	/** LSAP服务器异常预警 **/
	public static final String ALARM_TYPE_LSAP_ERROR = "LSAP_ERROR";
	/** CA服务器异常预警 **/
	public static final String ALARM_TYPE_CA_ERROR = "CA_ERROR";
	/** AMICOS服务器异常预警 **/
	public static final String ALARM_TYPE_AMICOS_ERROR = "AMICOS_ERROR";
	/** ACARS服务器异常预警 **/
	public static final String ALARM_TYPE_ACARS_ERROR = "ACARS_ERROR";
	//
	/** 红色预警  **/
	public static final String ALARM_LEVEL_RED = "RED";
	/** 黄色预警  **/
	public static final String ALARM_LEVEL_YELLOW = "YELLOW";
	/** 绿色预警  **/
	public static final String ALARM_LEVEL_GREEN = "GREEN";

	private AlarmConstant() {
	}
}
