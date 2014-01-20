/*******************************************************************************
 * Copyright (c) 2013-8-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.authorization.data.impl;

import java.lang.reflect.Method;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.foreveross.authorization.data.DataAuthorization;
import com.foreveross.authorization.data.DataAuthorizationStep;
import com.foreveross.util.mybatis.plugin.ReflectHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-14
 */
public class DefaultDataAuthorizationStep implements DataAuthorizationStep {

	public void step1(Object[] parameters) {
		org.apache.ibatis.plugin.Invocation invocation = (org.apache.ibatis.plugin.Invocation) parameters[0];
		try {
			Method method = invocation.getMethod();
			DataAuthorization dataAuthorization = null;
			try {
				dataAuthorization = method
						.getAnnotation(DataAuthorization.class);
				if (!dataAuthorization.enable()) {
					return;
				}
			} catch (Exception e) {
			}
			if (dataAuthorization == null) {
				return;
			}
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
					.getTarget();
			MappedStatement mappedStatement = (MappedStatement) invocation
					.getArgs()[0];
			BoundSql boundSql = statementHandler.getBoundSql();
			String sql = boundSql.getSql();
			String sql_lowercase = sql.trim().toLowerCase();
			if (sql_lowercase.startsWith("select")) {
				//
				String insertIndex = dataAuthorization.insertIndex();
				String joinTable = dataAuthorization.joinTable();
				String condition = dataAuthorization.condition();
				String[] parameterNames = dataAuthorization.parameterNames();
				String before = "", after = "";
				if (insertIndex == null || insertIndex.length() < 1) {
					before = sql;
					after = "";
				} else {
					int indexOf = sql.indexOf(insertIndex);
					if (indexOf < 1) {
						before = sql;
						after = "";
					} else {
						before = sql.substring(0, indexOf);
						after = sql.substring(indexOf + insertIndex.length());
					}
				}
				StringBuilder newSql = new StringBuilder(512);
				newSql.append(before).append(joinTable).append(insertIndex)
						.append(condition).append(after);
				TypeHandlerRegistry typeHandlerRegistry = mappedStatement
						.getConfiguration().getTypeHandlerRegistry();
				TypeHandler<Object> unknownTypeHandler = typeHandlerRegistry
						.getUnknownTypeHandler();
				for (int i = parameterNames.length - 1; i > -1; i--) {
					boundSql.getParameterMappings().add(
							0,
							new ParameterMapping.Builder(mappedStatement
									.getConfiguration(), parameterNames[i],
									unknownTypeHandler).build());
				}
				ReflectHelper.setValueByFieldName(boundSql, "sql", newSql);
			} else {
				//NOT SUPPORT!!
			}
		} catch (Exception e) {
			throw new RuntimeException("DataAuthorization Exception:", e);
		}
	}

	public void step2(Object[] parameters) {

	}

	public void step3(Object[] parameters) {

	}

	public void step4(Object[] parameters) {

	}

	public void step5(Object[] parameters) {

	}

	public void step6(Object[] parameters) {

	}

	public void step7(Object[] parameters) {

	}

	public void step8(Object[] parameters) {

	}

	public void step9(Object[] parameters) {

	}

}
