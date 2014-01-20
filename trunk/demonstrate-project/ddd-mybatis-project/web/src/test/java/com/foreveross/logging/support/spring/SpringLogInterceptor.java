/*******************************************************************************
 * Copyright (c) 2013-8-11 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.support.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.foreveross.logging.LogProcessStep;

/**
 * <pre>
 * Spring xml配置如下：
 * &lt;?xml version="1.0" encoding="UTF-8"?>
 * &lt;beans xmlns="http://www.springframework.org/schema/beans"
 * 	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
 * 	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:security="http://www.springframework.org/schema/security"
 * 	xmlns:p="http://www.springframework.org/schema/p" xmlns:task="http://www.springframework.org/schema/task"
 * 	xmlns:aop="http://www.springframework.org/schema/aop"
 * 	xsi:schemaLocation="http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.0.xsd
 * 		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
 * 		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
 * 		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
 * 		http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task-3.0.xsd
 * 		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd">
 * 
 * 
 * 	&lt;bean id="springLogInterceptor" class="com.foreveross.logging.support.spring.SpringLogInterceptor" />
 * 	&lt;aop:config>
 * 		&lt;aop:pointcut id="pointcutSpringLogInterceptor" expression="execution(* com.foreveross..*.*ApplicationImpl.*(..))" />
 * 		&lt;!-- or execution(* com.redhat.auth.ejb.impl.*ApplicationImpl.*(..)) or execution(* com.csair.gsms.external.timeTask.*Task.*(..)) -->
 * 		&lt;aop:advisor order="10" advice-ref="springLogInterceptor" pointcut-ref="pointcutSpringLogInterceptor" />
 * 	&lt;/aop:config>
 * &lt;/beans>
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-11
 */
public class SpringLogInterceptor implements MethodInterceptor {

	private LogProcessStep logProcessStep;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		{
			//在调用前进行日志记录
			try {
				//getDataCarrier().get(null)
				getLogProcessStep().step2(new Object[] { invocation });
			} catch (Throwable tt) {
				// Do nothing.
			}
		}
		Throwable t = null;
		Object rval = null;
		try {
			rval = invocation.proceed();
		} catch (Throwable tt) {
			t = tt;
		}
		{
			//在调用后进行日志记录
			try {
				//getDataCarrier().get(null)
				getLogProcessStep().step5(new Object[] { invocation, t, rval });
			} catch (Throwable tt) {
				// Do nothing.
			}
		}
		if (t != null) {
			throw t;
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