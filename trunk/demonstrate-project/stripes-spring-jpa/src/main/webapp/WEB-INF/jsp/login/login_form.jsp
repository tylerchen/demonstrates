<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-render name="/WEB-INF/jsp/layout/layout_main_login.jsp">
	<s:layout-component name="body">
		<br />
		<table>
			<tr>
				<td style="vertical-align: top;">
				<table class="form" cellpadding="0" cellspacing="0">
					<tr>
						<td style="padding-right: 10px;"><span class="title1"
							style="text-align: left;">
						<p><s:label for="pleaseLogin" />:</p>
						</span> <br />
						<s:errors globalErrorsOnly="false" /> <s:messages /> <s:form
							beanclass="org.iff.sample.web.action.LoginActionBean"
							autocomplete="off">
							<s:hidden name="loginUrl" value="${loginUrl}" />
							<table class="form" style="height: 600dpx;">
								<tr>
									<td>
									<table class="form">
										<tr>
											<td><s:label for="userName" />:</td>
											<td><s:text id="user.userName" name="user.userName" /></td>
										</tr>
										<tr>
											<td><s:label for="password" />:</td>
											<td><s:password id="user.password" name="user.password" /></td>
										</tr>
										<%-- <tr>
											<td><s:label for="verifyCode" />:</td>
											<td><s:text id="user.realp" name="user.realp" /><s:errors
												field="register.captcha.error" /></td>
											<td>
										</tr>
										<tr>
											<td></td>
											<td><img src="${contextPath}/action/verifycode"
												alt="jCaptcha image"
												onclick="this.src='${contextPath}/action/verifycode'+'?_t_='+new Date().getTime();" /></td>
											<td>
										</tr> --%>
										<tr>
											<td></td>
											<td><s:submit class="button white" name="login"
												value="Login" /></td>
										</tr>
										<tr>
											<td colspan="2"></td>
										</tr>
										<tr>
											<td></td>
											<td><a class="button white" href=""><s:label
												for="forgotPassword" />?</a></td>
										</tr>
									</table>
									</td>
									<td valign="middle"></td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
	</s:layout-component>
</s:layout-render>

