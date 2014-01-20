/*******************************************************************************
 * Copyright (c) 2013-8-13 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.logging;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-8-13
 */
public interface LogProcessStep {

	/**
	 * step1: 执行日志拦截的【步骤1】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Action的所有方法，在方法执行前执行该【步骤1】，
	 * 主要作用是获得客户端访问URL、地址、会话等信息。
	 * parameters[0]: com.opensymphony.xwork2.ActionInvocation
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step1(Object[] parameters);

	/**
	 * step2: 执行日志拦截的【步骤2】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Spring Bean(Application)的所有方法，在方法执行前执行该【步骤2】，
	 * 如果WEB层与应用层分包部署，则可以把【步骤1】的传过来的相关日志信息存到指定地方（如线程变量），
	 * 如果WEB层与应用层同包部署，则不需要作任何处理，因为日志已经存到指定地方（如线程变量）。
	 * parameters[0]: org.aopalliance.intercept.MethodInvocation
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step2(Object[] parameters);

	/**
	 * step3: 执行日志拦截的【步骤3】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Mybatis的所有数据操作方法，在数据操作方法执行前执行该【步骤3】，
	 * 对所有增、删、改、查操作进行拦截，以获得相关的日志信息。
	 * parameters[0]: org.apache.ibatis.plugin.Invocation
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step3(Object[] parameters);

	/**
	 * step4: 执行日志拦截的【步骤4】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Mybatis的所有数据操作方法，在数据操作方法执行后执行该【步骤4】，
	 * 目前没有处理要求。
	 * parameters[0]: org.apache.ibatis.plugin.Invocation
	 * parameters[1]: java.lang.Throwable
	 * parameters[2]: java.lang.Object
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step4(Object[] parameters);

	/**
	 * step5: 执行日志拦截的【步骤5】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Spring Bean(Application)的所有方法，在方法执行后执行该【步骤5】，
	 * 需要把日志数据放到LogDataBus中。
	 * parameters[0]: org.aopalliance.intercept.MethodInvocation
	 * parameters[1]: java.lang.Throwable
	 * parameters[2]: java.lang.Object
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step5(Object[] parameters);

	/**
	 * step6: 执行日志拦截的【步骤6】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 拦截Action的所有方法，在方法执行前执行该【步骤6】，
	 * 目前没有处理要求。
	 * parameters[0]: com.opensymphony.xwork2.ActionInvocation
	 * parameters[1]: java.lang.Exception
	 * parameters[2]: java.lang.Object
	 * </pre>
	 * @param parameters
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2013-8-13
	 */
	void step6(Object[] parameters);

	/**
	 * step7: 执行日志拦截的【步骤7】
	 * <pre>
	 * FOR Strust2 + Spring + Mybatis:
	 * 持久化日志数据。
	 * parameters[0]: java.lang.Object
	 * </pre>
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
