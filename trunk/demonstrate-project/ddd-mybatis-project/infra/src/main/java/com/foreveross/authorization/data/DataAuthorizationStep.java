/*******************************************************************************
 * Copyright (c) 2013-8-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.authorization.data;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-13
 */
public interface DataAuthorizationStep {

	/**
	 * step1: 执行数据权限拦截的【步骤1】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Mybatis的所有数据操作方法，在数据操作方法执行前执行该【步骤1】，
	 * 对所有增、删、改、查操作进行拦截，以获注入权限信息。
	 * parameters[0]: org.apache.ibatis.plugin.Invocation
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step1(Object[] parameters);

	/**
	 * step2: 执行数据权限拦截的【步骤2】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Mybatis的所有数据操作方法，在数据操作方法执行后执行该【步骤2】，
	 * 目前没有需要实现的。
	 * parameters[0]: org.apache.ibatis.plugin.Invocation
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step2(Object[] parameters);

	/**
	 * step3: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step3(Object[] parameters);

	/**
	 * step4: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step4(Object[] parameters);

	/**
	 * step5: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step5(Object[] parameters);

	/**
	 * step6: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step6(Object[] parameters);

	/**
	 * step7: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step7(Object[] parameters);

	/**
	 * step8: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step8(Object[] parameters);

	/**
	 * step9: NOT USE!!!
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step9(Object[] parameters);
}
