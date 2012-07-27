<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_default.jsp">
	<s:layout-component name="body">
		<br>
		<table>
			<tbody>
				<tr>
					<td style="vertical-align: top;">
					<table width="600">
						<tbody>
							<tr>
								<td align="left"></td>
							</tr>
							<tr>
								<td align="left">
								<p><br>
								<br>
								<span class="text"> <b> <s:label for="userList" /> </b>
								</span></p>
								</td>
							</tr>
						</tbody>
					</table>
					<s:url beanclass="org.iff.sample.web.action.UserActionBean"
						var="formUrl" />
					<form action="${formUrl}" method="POST" name="displayForm">
					<s:form partial="true"
						beanclass="org.iff.sample.web.action.UserActionBean">
						<d:table id="bean" list="${actionBean.userPage.content}"
							cellspacing="5" cellpadding="5"
							pagesize="<%=org.iff.sample.web.action.UserActionBean.RECORD_SIZE%>"
							size="${actionBean.totalCount}" class="textbody" export="false"
							excludedParams="*" partialList="true" htmlId="tag"
							form="displayForm">
							<d:column sortable="false" property="name" escapeXml="true"
								title="name" style="width:20%" />
							<d:column sortable="false" property="userName" escapeXml="true"
								title="userName" style="width:20%" />
							<d:column sortable="false" property="email" escapeXml="true"
								title="email" style="width:20%" />
							<d:column sortable="false" escapeXml="false"
								titleKey="repweb.list.action" style="width:32%">
								<s:link beanclass="org.iff.sample.web.action.UserActionBean"
									event="view" class="button white">
									<s:param name="user.id" value="${bean.id}" />
									<s:label for="view" />
								</s:link>&nbsp;<s:link
									beanclass="org.iff.sample.web.action.UserActionBean"
									event="edit" class="button white">
									<s:param name="user.id" value="${bean.id}" />
									<s:label for="Edit" />
								</s:link>
							</d:column>
						</d:table>
					</s:form></form>
					</td>
				</tr>
			</tbody>
		</table>
	</s:layout-component>
</s:layout-render>

