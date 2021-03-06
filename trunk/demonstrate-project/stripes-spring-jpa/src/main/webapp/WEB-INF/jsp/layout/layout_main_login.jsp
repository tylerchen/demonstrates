<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
	<s:layout-render name="/WEB-INF/jsp/layout/layout_main.jsp"
		title="${logintitle}">
		<s:layout-component name="header">
			<%@include file="/WEB-INF/jsp/layout/layout_main_head_import.jsp"%>
		</s:layout-component>
		<s:layout-component name="menu">
			<%@include file="/WEB-INF/jsp/layout/layout_main_menu.jsp"%>
		</s:layout-component>
		<s:layout-component name="topmenu">
		</s:layout-component>
		<s:layout-component name="sidemenu">
		</s:layout-component>
		<s:layout-component name="body">
		${body}
	</s:layout-component>
		<s:layout-component name="footer">
			<%@include file="/WEB-INF/jsp/layout/layout_main_footer.jsp"%>
		</s:layout-component>
	</s:layout-render>
</s:layout-definition>

