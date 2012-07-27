<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<span id="topmenus"> <c:if
	test="${actionBean!=null&&actionBean.loginUser!=null}">
	<a class="sectionLink"> Home</a>
	| <a class="sectionLink"> Cart</a> | <span class="currentSection">
	<a>My Account</a> </span> | <s:link
		beanclass="org.iff.sample.web.action.LoginActionBean" event="logout">Logout</s:link>
</c:if></span>

<span class="usercheck"> Welcome! <c:if
	test="${actionBean!=null&&actionBean.loginUser!=null}">${actionBean.loginUser.name}</c:if></span>