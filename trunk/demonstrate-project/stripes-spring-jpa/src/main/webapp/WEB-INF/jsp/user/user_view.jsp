<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message var="title" key="repWebsiteViewTitle" />
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_default.jsp"
	title="${title}">
	<s:layout-component name="body">
		<br>
		<table>
			<tbody>
				<tr>
					<td style="vertical-align: top;">
					<table width="600">
						<tbody>
							<tr>
								<td align="left"><span class="title1"> <fmt:message
									key="hello" /> <b>${actionBean.memberSite.name}</b>! <br>
								</span></td>
							</tr>
							<tr>
								<td align="left">
								<p><br>
								<br>
								<span class="text"> <b><fmt:message
									key="repWebsiteView" /></b> </span></p>
								</td>
							</tr>
						</tbody>
					</table>
					<table width="700" class="textbody" cellspacing="4" cellpadding="4">
						<tbody>
							<tr>
								<td><s:label for="picture" />:</td>
								<td><img
									src="${contextPath}/action/files/${actionBean.user.username}-<c:if test="${actionBean.uploadFileName!=null}">temp</c:if>pic/picture.jpg"
									alt="" border="0" width="180px" height="180px"></td>
							</tr>
							<tr>
								<td><s:label for="memberSite.name" />:</td>
								<td>${actionBean.memberSite.name}</td>
							</tr>
							<tr>
								<td><s:label for="memberSite.contactEmail" />:</td>
								<td>${actionBean.memberSite.contactEmail}</td>
							</tr>
							<tr>
								<td><s:label for="memberSite.contactPhone" />:</td>
								<td>${actionBean.memberSite.contactPhone}</td>
							</tr>
							<tr>
								<td><s:label for="memberSite.websiteName" />:</td>
								<td>${actionBean.memberSite.websiteName}</td>
							</tr>
							<tr>
								<td><s:label for="memberSite.entryDate" />:</td>
								<td><s:format value="${actionBean.memberSite.entryDate}"
									formatType="date" formatPattern="M-d-yy" /></td>
							</tr>
							<tr>
								<td><s:label for="memberSite.validToDate" />:</td>
								<td><s:format value="${actionBean.memberSite.validToDate}"
									formatType="date" formatPattern="M-d-yy" /></td>
							</tr>
							<tr>
								<td><s:label for="theme.name" />:</td>
								<td>${actionBean.theme.name}</td>
							</tr>
							<tr>
								<td><s:label for="memberSite.bio" />:</td>
								<td><c:set var="newLineString"
									value="${actionBean.memberSite.bio}" scope="request" /><jsp:include
									page="/WEB-INF/jsp/components/newline-to-br.jsp" /></td>
							</tr>
							<tr>
								<td><s:label for="memberSite.testimonial" />:</td>
								<td><c:set var="newLineString"
									value="${actionBean.memberSite.testimonial}" scope="request" /><jsp:include
									page="/WEB-INF/jsp/components/newline-to-br.jsp" /></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><s:link
									beanclass="com.poinge.action.ReplicatedWebsiteAdminAction"
									event="edit" class="button white">
									<s:param name="user.id" value="${actionBean.user.id}" />
									<fmt:message key="repweb.list.changeInformation" />
								</s:link>&nbsp;<s:link
									beanclass="com.poinge.action.ReplicatedWebsiteAdminAction"
									event="cancel" class="button white">
									<fmt:message key="repweb.list.backToHome" />
								</s:link></td>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>
			</tbody>
		</table>
	</s:layout-component>
</s:layout-render>

