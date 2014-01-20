/*******************************************************************************
 * Copyright (c) 2013-8-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-11
 */
public interface LoggingInfomation extends Serializable {

	String getOperator();

	void setOperator(String operator);

	String getLoggingStatus();

	void setLoggingStatus(String loggingStatus);

	String getLoggingType();

	void setLoggingType(String loggingType);

	Date getLoggingTime();

	void setLoggingTime(Date loggingTime);

	String getDetail();

	void setDetail(String detail);
}
