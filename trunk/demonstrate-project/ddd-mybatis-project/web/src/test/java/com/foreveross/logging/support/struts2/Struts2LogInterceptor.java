/*******************************************************************************
 * Copyright (c) 2013-8-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.support.struts2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.foreveross.logging.LogProcessStep;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * <pre>
 * Struts2.xml配置如下：
 *	&lt;package name="data" extends="json-default" namespace="/get">
 *		&lt;!-- 日志拦截器，package范围 -->
 *		&lt;interceptors>
 *		    &lt;interceptor name="Struts2LogInterceptor" class="com.foreveross.logging.support.struts2.Struts2LogInterceptor">
 *		    &lt;/interceptor>
 *		&lt;/interceptors>			
 *		&lt;action>...&lt;/action>
 *	&lt;/package>
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-11
 */
public class Struts2LogInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -546937787478648687L;
	public static Log log = LogFactory.getLog(Struts2LogInterceptor.class);

	private LogProcessStep logProcessStep;

	/**
	 * 拦截所有Action方法
	 */
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		try {
			//String action = invocation.getInvocationContext().getName();
			//Map<String, Object> session = invocation.getInvocationContext().getSession();
			//boolean isEmpty = session.isEmpty();
			//1、如果只记录登陆的日志，需要做登陆检查
			//2、如果记录指定URL的日志，需要做URL配置检查
			//3、获得用户信息，用户名、访问URL、IP地址、操作类型等
			//4、获得的日志信息可以存放到指定的地方，线程变量，公共存储等
			//getDataCarrier().put(null, data);
			getLogProcessStep().step1(new Object[] { invocation });
		} catch (Throwable t) {
			//不抛出任何异常信息
		}

		Exception e = null;
		String rval = null;
		try {
			rval = invocation.invoke();
		} catch (Exception ee) {
			e = ee;
		}
		{
			//在调用后进行日志记录
			try {
				//getDataCarrier().get(null)
				getLogProcessStep().step6(new Object[] { invocation, e, rval });
			} catch (Throwable tt) {
				// Do nothing.
			}
		}
		if (e != null) {
			throw e;
		}
		return rval;
	}

	public LogProcessStep getLogProcessStep() {
		return logProcessStep;
	}

	public void setLogProcessStep(LogProcessStep logProcessStep) {
		this.logProcessStep = logProcessStep;
	}

}
