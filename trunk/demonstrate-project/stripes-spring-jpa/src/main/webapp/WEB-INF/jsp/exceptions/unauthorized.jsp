<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message var="title" key="unauthorized.title" />
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_default.jsp"
	title="${title}">
	<s:layout-component name="body">
		<p style="color: red"><fmt:message key="unauthorized.message" /></p>
		<s:link href="/">
			<fmt:message key="exception.startOver" />
		</s:link>
	</s:layout-component>
</s:layout-render>
