package com.foreveross.demo.web.action.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.TokenSessionStoreInterceptor;
import org.apache.struts2.util.InvocationSessionStore;
import org.apache.struts2.util.TokenHelper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 2011-12-13
 * @author huangsong
 * 表单重复提交拦截器
 */
@SuppressWarnings("serial")
public class ITokenSessionStoreInterceptor extends TokenSessionStoreInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.struts2.interceptor.TokenSessionStoreInterceptor#
	 * handleInvalidToken(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String handleInvalidToken(ActionInvocation invocation)
			throws Exception {
		// TODO Auto-generated method stub
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) ac
				.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		String tokenName = TokenHelper.getTokenName();
		String token = TokenHelper.getToken(tokenName);
		if ((tokenName != null) && (token != null)) {
			Map params = ac.getParameters();
			params.remove(tokenName);
			params.remove("struts.token.name");

			ActionInvocation savedInvocation = InvocationSessionStore
					.loadInvocation(tokenName, token);

			if (savedInvocation != null) {
				ValueStack stack = savedInvocation.getStack();
				Map context = stack.getContext();
				request.setAttribute("struts.valueStack", stack);
				ActionContext savedContext = savedInvocation
						.getInvocationContext();
				savedContext
						.getContextMap()
						.put(
								"com.opensymphony.xwork2.dispatcher.HttpServletRequest",
								request);
				savedContext
						.getContextMap()
						.put(
								"com.opensymphony.xwork2.dispatcher.HttpServletResponse",
								response);
				Result result = savedInvocation.getResult();

				if ((result != null)
						&& (savedInvocation.getProxy().getExecuteResult())) {
					synchronized (context) {
						result.execute(savedInvocation);
					}

				}

				invocation.getProxy().setExecuteResult(false);

				return savedInvocation.getResultCode();
			}
		} else {
			return invocation.invoke();
		}

		return "invalid.token";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.TokenSessionStoreInterceptor#handleValidToken
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String handleValidToken(ActionInvocation invocation)
			throws Exception {
		// TODO Auto-generated method stub
		// return super.handleValidToken(invocation);
		String key = TokenHelper.getTokenName();
		String token = TokenHelper.getToken(key);
		InvocationSessionStore.storeInvocation(key, token, invocation);
		return invocation.invoke();
	}

}
