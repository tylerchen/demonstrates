/*******************************************************************************
 * Copyright (c) 2013-2-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.support.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.foreveross.logging.LogProcessStep;

/**
 * <pre>
 * mybatis spring xml配置：
 * 	&lt;bean id="mybatisLogInterceptor" class="com.foreveross.logging.support.mybatis.MybatisLogInterceptor" />
 * 	&lt;bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 * 		&lt;property name="plugins">
 * 			&lt;array>
 * 				&lt;ref bean="mybatisLogInterceptor" />
 * 			&lt;/array>
 * 		&lt;/property>
 * 		&lt;property ... />
 * 	&lt;/bean>
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-2
 */
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MybatisLogInterceptor implements Interceptor {

	private LogProcessStep logProcessStep;

	public MybatisLogInterceptor() {
	}

	public Object intercept(Invocation invocation) throws Throwable {
		try {
			getLogProcessStep().step3(new Object[] { invocation });
		} catch (Throwable t) {
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
				getLogProcessStep().step4(new Object[] { invocation, t, rval });
			} catch (Throwable tt) {
				// Do nothing.
			}
		}
		if (t != null) {
			throw t;
		}
		return rval;
	}

	public Object plugin(Object plugin) {
		return Plugin.wrap(plugin, this);
	}

	public void setProperties(Properties paramProperties) {

	}

	public LogProcessStep getLogProcessStep() {
		return logProcessStep;
	}

	public void setLogProcessStep(LogProcessStep logProcessStep) {
		this.logProcessStep = logProcessStep;
	}
}
