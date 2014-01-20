/*******************************************************************************
 * Copyright (c) 2013-2-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.authorization.data.support.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.foreveross.authorization.data.DataAuthorizationStep;

/**
 * <pre>
 * mybatis spring xml配置：
 * 	&lt;bean id="mybatisDataAuthorizationInterceptor" class="com.foreveross.authorization.data.support.mybatis.MybatisDataAuthorizationInterceptor" />
 * 	&lt;bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 * 		&lt;property name="plugins">
 * 			&lt;array>
 * 				&lt;ref bean="mybatisDataAuthorizationInterceptor" />
 * 			&lt;/array>
 * 		&lt;/property>
 * 		&lt;property ... />
 * 	&lt;/bean>
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-2
 */
@Intercepts( { @Signature(type = StatementHandler.class, method = "query", args = {
		MappedStatement.class, Object.class }) })
public class MybatisDataAuthorizationInterceptor implements Interceptor {

	private DataAuthorizationStep dataAuthorizationStep;

	public MybatisDataAuthorizationInterceptor() {
	}

	public Object intercept(Invocation invocation) throws Throwable {
		try {
			getDataAuthorizationStep().step1(new Object[] { invocation });
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
				getDataAuthorizationStep().step2(
						new Object[] { invocation, t, rval });
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

	public DataAuthorizationStep getDataAuthorizationStep() {
		return dataAuthorizationStep;
	}

	public void setDataAuthorizationStep(
			DataAuthorizationStep dataAuthorizationStep) {
		this.dataAuthorizationStep = dataAuthorizationStep;
	}

}
