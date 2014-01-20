/*******************************************************************************
 * Copyright (c) 2013-8-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.authorization.data.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
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
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
					.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
					.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper
					.getValueByFieldName(delegate, "mappedStatement");

			DataAuthorization dataAuthorization = null;
			try {
				String id = mappedStatement.getId();
				String className = id.substring(0, id.lastIndexOf('.'));
				String methodName = id.substring(id.lastIndexOf('.') + 1);
				Class<?> forName = Class.forName(className);
				Method[] methods = forName.getMethods();
				for (Method m : methods) {
					if (m.getName().equals(methodName)) {
						dataAuthorization = m
								.getAnnotation(DataAuthorization.class);
						break;
					}
				}
				System.out.println(id);

				if (!dataAuthorization.enable()) {
					return;
				}
			} catch (Exception e) {
			}
			if (dataAuthorization == null) {
				return;
			}

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
				ReflectHelper.setValueByFieldName(boundSql, "sql", newSql);
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
//				{
//					DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
//							mappedStatement, boundSql.getParameterObject(),
//							boundSql);
//					PreparedStatement prepareStatement = ((Connection) invocation
//							.getArgs()[0]).prepareStatement(newSql.toString());
//					parameterHandler.setParameters(prepareStatement);
//					prepareStatement.executeQuery();
//				}
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

	class DefaultParameterHandler implements ParameterHandler {

		private final TypeHandlerRegistry typeHandlerRegistry;

		private final MappedStatement mappedStatement;
		private final Object parameterObject;
		private BoundSql boundSql;
		private Configuration configuration;

		public DefaultParameterHandler(MappedStatement mappedStatement,
				Object parameterObject, BoundSql boundSql) {
			this.mappedStatement = mappedStatement;
			this.configuration = mappedStatement.getConfiguration();
			this.typeHandlerRegistry = mappedStatement.getConfiguration()
					.getTypeHandlerRegistry();
			this.parameterObject = parameterObject;
			this.boundSql = boundSql;
		}

		public Object getParameterObject() {
			return parameterObject;
		}

		public void setParameters(PreparedStatement ps) throws SQLException {
			ErrorContext.instance().activity("setting parameters").object(
					mappedStatement.getParameterMap().getId());
			List<ParameterMapping> parameterMappings = boundSql
					.getParameterMappings();
			if (parameterMappings != null) {
				MetaObject metaObject = parameterObject == null ? null
						: configuration.newMetaObject(parameterObject);
				for (int i = 0; i < parameterMappings.size(); i++) {
					ParameterMapping parameterMapping = parameterMappings
							.get(i);
					if (parameterMapping.getMode() != ParameterMode.OUT) {
						Object value;
						String propertyName = parameterMapping.getProperty();
						PropertyTokenizer prop = new PropertyTokenizer(
								propertyName);
						if (parameterObject == null) {
							value = null;
						} else if (typeHandlerRegistry
								.hasTypeHandler(parameterObject.getClass())) {
							value = parameterObject;
						} else if (boundSql
								.hasAdditionalParameter(propertyName)) {
							value = boundSql
									.getAdditionalParameter(propertyName);
						} else if (propertyName
								.startsWith(ForEachSqlNode.ITEM_PREFIX)
								&& boundSql.hasAdditionalParameter(prop
										.getName())) {
							value = boundSql.getAdditionalParameter(prop
									.getName());
							if (value != null) {
								value = configuration.newMetaObject(value)
										.getValue(
												propertyName.substring(prop
														.getName().length()));
							}
						} else {
							value = metaObject == null ? null : metaObject
									.getValue(propertyName);
						}
						TypeHandler typeHandler = parameterMapping
								.getTypeHandler();
						if (typeHandler == null) {
							throw new ExecutorException(
									"There was no TypeHandler found for parameter "
											+ propertyName + " of statement "
											+ mappedStatement.getId());
						}
						JdbcType jdbcType = parameterMapping.getJdbcType();
						if (value == null && jdbcType == null)
							jdbcType = configuration.getJdbcTypeForNull();
						typeHandler.setParameter(ps, i + 1, value, jdbcType);
					}
				}
			}
		}

	}
}
