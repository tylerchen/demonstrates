<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message var="title" key="exception.title" />
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_default.jsp"
	title="${title}">
	<s:layout-component name="body">
		<p style="color: red"><fmt:message
			key="exception.not_found.message" /></p>
		<s:link href="/">
			<fmt:message key="exception.startOver" />
		</s:link>
	</s:layout-component>
</s:layout-render>
