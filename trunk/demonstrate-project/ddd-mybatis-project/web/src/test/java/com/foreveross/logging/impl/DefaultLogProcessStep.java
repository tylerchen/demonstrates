/*******************************************************************************
 * Copyright (c) 2013-8-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.foreveross.logging.Log;
import com.foreveross.logging.LogDataBus;
import com.foreveross.logging.LogDataCarrier;
import com.foreveross.logging.LogProcessStep;
import com.foreveross.logging.support.util.BeanHelper;
import com.foreveross.logging.support.util.ReflectHelper;
import com.google.gson.Gson;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-13
 */
public class DefaultLogProcessStep implements LogProcessStep {

	private static final Gson gson = new Gson();

	private LogDataCarrier logDataCarrier;

	private LogDataBus logDataBus;

	//private LogRepository

	public void step1(Object[] parameters) {
		com.opensymphony.xwork2.ActionInvocation invocation = (com.opensymphony.xwork2.ActionInvocation) parameters[0];
		String action = invocation.getInvocationContext().getName();
		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();
		boolean isEmpty = session.isEmpty();
		//如果未登陆，即不作日志记录
		if (isEmpty || !session.containsKey("sysUser")) {
			return;
		}
		//如果Action路径不在ActionMapper内，即不作日志记录
		//if (!ActionMapper.actionMapper.containsKey(action)) {
		//	return;
		//}

		Map<String, Object> logMap = new HashMap<String, Object>();
		{
			logMap.put("operator", "test");
			logMap.put("loggingType", "test");
			logMap.put("loggingTime", new Date());
			logMap.put("detail", new HashMap<String, Object>());
		}
		getLogDataCarrier().put("", logMap);
	}

	public void step2(Object[] parameters) {
		//DO Nothing.
	}

	public void step3(Object[] parameters) {
		org.apache.ibatis.plugin.Invocation invocation = (org.apache.ibatis.plugin.Invocation) parameters[0];
		Method method = invocation.getMethod();
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
				.getValueByFieldName(statementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectHelper
				.getValueByFieldName(delegate, "mappedStatement");
		Log log = null;
		try {
			String id = mappedStatement.getId();
			String className = id.substring(0, id.lastIndexOf('.'));
			String methodName = id.substring(id.lastIndexOf('.') + 1);
			Class<?> forName = Class.forName(className);
			Method[] methods = forName.getMethods();
			for (Method m : methods) {
				if (m.getName().equals(methodName)) {
					log = m.getAnnotation(Log.class);
					break;
				}
			}
			System.out.println(id);

			if (!log.enable()) {
				return;
			}
		} catch (Exception e) {
		}
		if (log == null) {
			return;
		}
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql();
		String sql_lowercase = sql.trim().toLowerCase();
		TreeMap<String, Object> oldValue = new TreeMap<String, Object>();
		TreeMap<String, Object> newValue = new TreeMap<String, Object>();
		if (sql_lowercase.startsWith("update")) {
			getValue4Update(invocation, delegate, boundSql, sql, sql_lowercase,
					oldValue, newValue);
		} else if (sql_lowercase.startsWith("insert")) {
			//oldValue = getOldValue4Insert(invocation, statementHandler, boundSql, sql, sql_lowercase);
		} else if (sql_lowercase.startsWith("delete")) {
			//oldValue = getOldValue4Delete(invocation, statementHandler, boundSql, sql, sql_lowercase);
		} else if (sql_lowercase.startsWith("select")) {
			//oldValue = getOldValue4Select(invocation, statementHandler,boundSql, sql, sql_lowercase);
		}
		{
			Map<String, Object> logMap = getLogDataCarrier().get(null);
			Map<String, Object> detail = (Map<String, Object>) logMap
					.get("detail");
			detail.put("oldValue", oldValue);
			detail.put("newValue", newValue);
			getLogDataCarrier().put("", logMap);
		}
	}

	public void step4(Object[] parameters) {
		//DO Nothing.
	}

	public void step5(Object[] parameters) {
		Map<String, Object> logMap = getLogDataCarrier().get(null);
		Map<String, Object> detail = (Map<String, Object>) logMap.get("detail");
		Throwable t = (Throwable) parameters[1];
		if (t == null) {
			logMap.put("loggingStatus", "SUCCESS");
			getLogDataBus().put(getLogDataCarrier().get(null));
		} else {
			logMap.put("loggingStatus", "FAIL");
			StringWriter out = new StringWriter();
			t.printStackTrace(new PrintWriter(out));
			String error = out.toString();
			if (error.length() > 512) {
				error = error.substring(0, 512);
			}
			detail.put("ERROR", error);
		}
	}

	public void step6(Object[] parameters) {
		//DO Nothing.
	}

	public void step7(Object[] parameters) {
		Map<String, Object> logMap = (Map<String, Object>) parameters[0];
		Object object = logMap.get("detail");
		logMap.put("detail", gson.toJson(object));
		DefaultLoggingInfomation dest = new DefaultLoggingInfomation();
		BeanHelper.copyProperties(dest, logMap);
		//SAVE DefaultLoggingInfomation;
	}

	public void step8(Object[] parameters) {
		//NO USE!!
	}

	public void step9(Object[] parameters) {
		//NO USE!!
	}

	public LogDataCarrier getLogDataCarrier() {
		return logDataCarrier;
	}

	public void setLogDataCarrier(LogDataCarrier logDataCarrier) {
		this.logDataCarrier = logDataCarrier;
	}

	public LogDataBus getLogDataBus() {
		return logDataBus;
	}

	public void setLogDataBus(LogDataBus logDataBus) {
		this.logDataBus = logDataBus;
	}

	/**
	 * 转换Update语句 为Select语句，查出旧值
	 * @param invocation
	 * @param statementHandler
	 * @param boundSql
	 * @param sql
	 * @param sql_lowercase
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	private void getValue4Update(
			org.apache.ibatis.plugin.Invocation invocation,
			BaseStatementHandler statementHandler, BoundSql boundSql,
			String sql, String sql_lowercase, TreeMap<String, Object> oldValue,
			TreeMap<String, Object> newValue) {
		String selectSql = sqlUpdate2Select(sql, sql_lowercase, newValue);
		MappedStatement mappedStatement = (MappedStatement) ReflectHelper
				.getValueByFieldName(statementHandler, "mappedStatement");
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				Connection connection = (Connection) invocation.getArgs()[0];
				stmt = connection.prepareStatement(selectSql);
				ReflectHelper.setValueByFieldName(boundSql, "sql", selectSql);
				DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
						mappedStatement, boundSql.getParameterObject(),
						boundSql, newValue);
				parameterHandler.setParameters(stmt);
				rs = stmt.executeQuery();
				toMap(rs, getColumnNames(rs), oldValue);
			} catch (Throwable t) {
				//
			} finally {
				try {
					rs.close();
				} catch (Exception e) {
				}
				try {
					stmt.close();
				} catch (Exception e) {
				}
				ReflectHelper.setValueByFieldName(boundSql, "sql", sql); // 语句反射回BoundSql.
			}
		}
	}

	private void getValue4Insert(
			org.apache.ibatis.plugin.Invocation invocation,
			BaseStatementHandler statementHandler, BoundSql boundSql,
			String sql, String sql_lowercase, TreeMap<String, Object> oldValue,
			TreeMap<String, Object> newValue) {
	}

	private void getValue4Delete(
			org.apache.ibatis.plugin.Invocation invocation,
			BaseStatementHandler statementHandler, BoundSql boundSql,
			String sql, String sql_lowercase, TreeMap<String, Object> oldValue,
			TreeMap<String, Object> newValue) {
		// 转换Delete语句 为Select语句，查出旧值
		String selectSql = sqlDelete2Select(sql, sql_lowercase);

		MappedStatement mappedStatement = (MappedStatement) ReflectHelper
				.getValueByFieldName(statementHandler, "mappedStatement");
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				Connection connection = (Connection) invocation.getArgs()[0];
				stmt = connection.prepareStatement(selectSql);
				ReflectHelper.setValueByFieldName(boundSql, "sql", selectSql);
				DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
						mappedStatement, boundSql.getParameterObject(),
						boundSql, newValue);
				parameterHandler.setParameters(stmt);
				rs = stmt.executeQuery();
				toMap(rs, getColumnNames(rs), oldValue);
			} catch (Throwable t) {
				//
			} finally {
				try {
					rs.close();
				} catch (Exception e) {
				}
				try {
					stmt.close();
				} catch (Exception e) {
				}
				ReflectHelper.setValueByFieldName(boundSql, "sql", sql); // 语句反射回BoundSql.
			}
		}
	}

	private void getOldValue4Select(
			org.apache.ibatis.plugin.Invocation invocation,
			BaseStatementHandler statementHandler, BoundSql boundSql,
			String sql, String sql_lowercase, TreeMap<String, Object> oldValue,
			TreeMap<String, Object> newValue) {
	}

	/**
	 * SQL: update Table set a='1', b=?, c = ? where id=?
	 * TO: SELECT * FROM Table where id=?
	 */
	private static String sqlDelete2Select(String sql, String sql_lowercase) {
		//
		StringBuilder sb = new StringBuilder(256);
		//
		int start = sql_lowercase.indexOf("delete ") + "delete ".length();
		String frag = sql.substring(start);
		sb.append("SELECT * ").append(frag);
		return sb.toString();
	}

	/**
	 * SQL: Delete From Table where id=?
	 * TO: SELECT a, b, c FROM Table where (1=1 OR (a='1' AND b=? AND c = ?)) AND id=?
	 */
	private static String sqlUpdate2Select(String sql, String sql_lowercase,
			TreeMap<String, Object> map) {
		//
		StringBuilder sb = new StringBuilder(256);
		//
		int start = sql_lowercase.indexOf("update ") + "update ".length();
		int end = sql_lowercase.indexOf("set ", start);
		String tableName = sql.substring(start, end).trim();
		//
		start = end + "set ".length();
		end = sql_lowercase.indexOf("where ", start);
		String columnValues = sql.substring(start, end);
		//
		String conditions = sql.substring(end + "where ".length());
		//
		{
			boolean splitColumnName = true, inValue = false;
			char[] chars = columnValues.toCharArray();
			StringBuilder sbc = new StringBuilder(64);
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (splitColumnName) {
					if (c != '=') {
						sbc.append(c);
					} else {
						map.put(sbc.toString().trim(), null);
						sbc.setLength(0);
						splitColumnName = false;
						continue;
					}
				} else {
					if (inValue) {
						if (c == '\'') {
							inValue = false;
							map.put(map.lastKey(), sbc.toString());
							sbc.setLength(0);
							splitColumnName = true;
							while (++i < chars.length) {
								if (chars[i] == ',') {
									break;
								}
							}
							continue;
						} else {
							sbc.append(c);
						}
					} else {
						if (c == '\'') {
							inValue = true;
							sbc.setLength(0);
							continue;
						} else if (c == '?') {
							map.put(map.lastKey(), "?");
							sbc.setLength(0);
							splitColumnName = true;
							while (++i < chars.length) {
								if (chars[i] == ',') {
									break;
								}
							}
							continue;
						}
					}
				}
			}
		}
		//
		{
			columnValues = "," + columnValues;
			start = end = 0;
			sb.append("SELECT ");
			for (int i = 0; i < columnValues.length(); i++) {
				start = columnValues.indexOf(',', end) + 1;
				end = columnValues.indexOf('=', end + 1);
				if (start < 0 || end < 0) {
					break;
				}
				String columnName = columnValues.substring(start, end);
				sb.append(columnName).append(", ");
			}
			{
				sb.setLength(sb.length() - 2);
			}
			sb.append(" FROM ").append(tableName).append(' ').append(
					"where (1=1 OR (").append(
					columnValues.substring(1).replaceAll(",", " AND ")).append(
					")) AND ").append(conditions);
		}
		return sb.toString();
	}

	/**
	* Return all column names as a list of strings
	* @param database query result set
	* @return list of column name strings
	* @throws SQLException if the query fails
	*/
	private static List<String> getColumnNames(ResultSet rs) throws Throwable {
		List<String> columnNames = new ArrayList<String>();
		ResultSetMetaData meta = rs.getMetaData();
		int numColumns = meta.getColumnCount();
		for (int i = 1; i <= numColumns; ++i) {
			columnNames.add(meta.getColumnName(i));
		}
		return columnNames;
	}

	/**
	* Helper method that maps a ResultSet into a list of maps, one per row
	* @param query ResultSet
	* @param list of columns names to include in the result map
	* @return list of maps, one per column row, with column names as keys
	* @throws SQLException if the connection fails
	*/
	private static void toMap(ResultSet rs, List<String> wantedColumnNames,
			TreeMap<String, Object> oldValue) throws Throwable {
		int numWantedColumns = wantedColumnNames.size();
		while (rs.next()) {
			for (int i = 0; i < numWantedColumns; ++i) {
				String columnName = wantedColumnNames.get(i);
				Object value = rs.getObject(columnName);
				oldValue.put(columnName, value);
			}
			break;
		}
	}

	class DefaultParameterHandler implements ParameterHandler {

		private final TypeHandlerRegistry typeHandlerRegistry;

		private final MappedStatement mappedStatement;
		private final Object parameterObject;
		private BoundSql boundSql;
		private Configuration configuration;
		private TreeMap<String, Object> map;

		public DefaultParameterHandler(MappedStatement mappedStatement,
				Object parameterObject, BoundSql boundSql,
				TreeMap<String, Object> map) {
			this.mappedStatement = mappedStatement;
			this.configuration = mappedStatement.getConfiguration();
			this.typeHandlerRegistry = mappedStatement.getConfiguration()
					.getTypeHandlerRegistry();
			this.parameterObject = parameterObject;
			this.boundSql = boundSql;
			this.map = map;
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
						{
							for (Entry<String, Object> entry : map.entrySet()) {
								if ("?".equals(entry.getValue())) {
									entry.setValue(value);
									break;
								}
							}
						}
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		String updateSQL = "Update A \r\nSET b = 'c', c = ?, d=? \r\nwhere id='1'";
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		System.out.println(sqlUpdate2Select(updateSQL, updateSQL.toLowerCase(),
				map));
		System.out.println(map);
	}
}
