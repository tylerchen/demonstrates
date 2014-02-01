/*******************************************************************************
 * Copyright (c) 2014-1-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.hnair.edms.common.alarm;

import java.util.Map;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-1-26
 */
public class AlarmApplication {

	/**
	 * 预警接口，对于未知（需求中未列出的）预警调用该接口
	 * @param alarmType  预警的类型
	 * @param alarmLevel 预警的级别，包括：红色预警、黄色预警、绿色预警
	 * @param title      预警的标题
	 * @param content    预警的正文
	 * @param model      预警的数据，包括：标题的填充值、内容的填充值以及邮件相关的内容等，参考邮件接口
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public void alarm(String alarmType, String alarmLevel, String title,
			String content, Map<String, Object> model) {
	}

	/**
	 * 预警接口，对于需求已经列出的接口，调用该接口
	 * @param alarmType  预警的类型，包括：完工状态同步预警、竣工情况预警、实际构型同步异常预警、证书过期预警、LSAP服务器异常预警、CA服务器异常预警、AMICOS服务器异常预警、ACARS服务器异常预警
	 * @param model      预警的数据，包括：标题的填充值、内容的填充值以及邮件相关的内容等，参考邮件接口
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-1-26
	 */
	public void alarm(String alarmType, Map<String, Object> model) {
	}
}
