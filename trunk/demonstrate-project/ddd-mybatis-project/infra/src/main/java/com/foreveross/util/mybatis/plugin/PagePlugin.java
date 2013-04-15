package com.foreveross.util.mybatis.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.foreveross.util.jdbc.dialet.Dialect;

@SuppressWarnings("unchecked")
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {

	private static Dialect dialectObject = null; // 数据库方言
	private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
					.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
					.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper
					.getValueByFieldName(delegate, "mappedStatement");

			if (mappedStatement.getId().matches(pageSqlId)) { // 拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if (parameterObject == null) {
					throw new NullPointerException(
							"boundSql.getParameterObject() is null!");
				} else {

					Page page = null;
					if (parameterObject instanceof Page) { // 参数就是Pages实体
						page = (Page) parameterObject;
					} else if (parameterObject instanceof Map) {
						for (Entry entry : (Set<Entry>) ((Map) parameterObject)
								.entrySet()) {
							if (entry.getValue() instanceof Page) {
								page = (Page) entry.getValue();
								break;
							}
						}
					} else { // 参数为某个实体，该实体拥有Pages属性
						page = ReflectHelper.getValueByFieldType(
								parameterObject, Page.class);
						if (page == null) {
							return ivk.proceed();
						}
					}

					String sql = boundSql.getSql();
					PreparedStatement countStmt = null;
					ResultSet rs = null;
					try {
						Connection connection = (Connection) ivk.getArgs()[0];
						String countSql = "select count(1) from (" + sql
								+ ") tmp_count"; // 记录统计
						countStmt = connection.prepareStatement(countSql);
						ReflectHelper.setValueByFieldName(boundSql, "sql",
								countSql);
						DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
								mappedStatement, parameterObject, boundSql);
						parameterHandler.setParameters(countStmt);
						rs = countStmt.executeQuery();
						int count = 0;
						if (rs.next()) {
							count = ((Number) rs.getObject(1)).intValue();
						}
						page.setTotalCount(count);
					} finally {
						try {
							rs.close();
						} catch (Exception e) {
						}
						try {
							countStmt.close();
						} catch (Exception e) {
						}
					}
					String pageSql = generatePagesSql(sql, page);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
				}
			}
		}
		return ivk.proceed();
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePagesSql(String sql, Page page) {
		if (page != null && dialectObject != null) {
			return dialectObject.getLimitString(sql, page.getCurrentPage()
					* page.getPageSize(), page.getPageSize());
		}
		return sql;
	}

	public Object plugin(Object plugin) {
		return Plugin.wrap(plugin, this);
	}

	public void setProperties(Properties p) {
		String dialect = ""; // 数据库方言
		dialect = p.getProperty("dialect");
		if (StringUtils.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} else {
			try {
				dialectObject = (Dialect) Class.forName(dialect)
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(dialect + ", init fail!\n" + e);
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
}
