/*******************************************************************************
 * Copyright (c) 2013-2-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.demo.util.mybatis.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-2
 */
@Intercepts( { @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
public class UUIDPlugin implements Interceptor {

	public UUIDPlugin() {
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT) {
			ReflectHelper.setValueByFieldName(mappedStatement, "keyGenerator",
					new UUIDKeyGenerator());
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object paramObject) {
		return Plugin.wrap(paramObject, this);
	}

	@Override
	public void setProperties(Properties paramProperties) {

	}

}
