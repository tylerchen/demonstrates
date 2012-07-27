<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message var="title" key="exception.title" />
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_default.jsp"
	title="${title}">
	<s:layout-component name="body">
		<p><fmt:message key="exception.message" /></p>
		<p><s:errors globalErrorsOnly="false" /></p>
		<s:link class="button white" href="/">
			<fmt:message key="exception.startOver" />
		</s:link>
		<p>${errors}</p>
	</s:layout-component>
</s:layout-render>
