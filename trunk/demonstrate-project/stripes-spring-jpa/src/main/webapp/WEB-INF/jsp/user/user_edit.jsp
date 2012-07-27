<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message var="title" key="repWebsiteEditTitle" />
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_default.jsp"
	title="${title}">
	<s:layout-component name="body">
		<br />
		<table>
			<tr>
				<td style="vertical-align: top;">
				<table width="600">
					<tr>
						<td align="left"><span class="title1"> <fmt:message
							key="hello" /> <b>${actionBean.memberSite.name}</b>! <br />
						</span></td>
					</tr>
					<tr>
						<td align="left">
						<p><br>
						<br>
						<span class="text"> <b><fmt:message
							key="repWebsiteEdit" /></b> </span></p>
						</td>
					</tr>
				</table>
				<s:errors globalErrorsOnly="false" /> <s:form
					beanclass="com.poinge.action.ReplicatedWebsiteAdminAction"
					autocomplete="off">
					<s:hidden name="user.username" value="${actionBean.user.username}" />
					<table width="700" class="textbody" cellspacing="4" cellpadding="4">
						<tr>
							<td><s:label for="picture" />:</td>
							<td><img
								src="${contextPath}/action/files/${actionBean.user.username}-<c:if test="${actionBean.uploadFileName!=null}">temp</c:if>pic/picture.jpg"
								alt="" border="0" width="180px" height="180px" /> <br>
							<s:file name="updloadFile" /> <br>
							<%-- <s:submit name="uploadPicture" value="Change Picture"
								class="button white" /> --%></td>
						</tr>
					</table>

					<table width="700" class="textbody" cellspacing="4" cellpadding="4">
						<tr>
							<td><s:label for="memberSite.name" />:</td>
							<td><s:text id="memberSite.name" name="memberSite.name"
								value="${actionBean.memberSite.name}" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.contactEmail" />:</td>
							<td><s:text id="memberSite.contactEmail"
								name="memberSite.contactEmail"
								value="${actionBean.memberSite.contactEmail}" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.contactPhone" />:</td>
							<td><s:text id="memberSite.contactPhone"
								name="memberSite.contactPhone"
								value="${actionBean.memberSite.contactPhone}" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.websiteName" />:</td>
							<td><s:text id="memberSite.websiteName"
								name="memberSite.websiteName"
								value="${actionBean.memberSite.websiteName}" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.entryDate" />:</td>
							<td><s:text id="memberSite.entryDate"
								name="memberSite.entryDate"
								value="${actionBean.memberSite.entryDate}" formatType="date"
								formatPattern="M-d-yy" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.validToDate" />:</td>
							<td><s:text id="memberSite.validToDate"
								name="memberSite.validToDate"
								value="${actionBean.memberSite.validToDate}" formatType="date"
								formatPattern="M-d-yy" /></td>
						</tr>
						<tr>
							<td><s:label for="theme.name" />:</td>
							<td><jsp:include
								page="/WEB-INF/jsp/components/themes_select.jsp" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.bio" />:</td>
							<td><s:textarea name="memberSite.bio"
								value="${actionBean.memberSite.bio}" rows="10" cols="80" /></td>
						</tr>
						<tr>
							<td><s:label for="memberSite.testimonial" />:</td>
							<td><s:textarea name="memberSite.testimonial"
								value="${actionBean.memberSite.testimonial}" rows="10" cols="80" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td><s:submit class="button white"
								name="toConfirmInfomation" />&nbsp; <s:submit
								class="button white" name="cancel" /></td>
						</tr>
					</table>
				</s:form></td>
			</tr>
		</table>
	</s:layout-component>
</s:layout-render>

