<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:layout-definition>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="" />
	<title>${title}</title>
	<%-- header start --%>
	<s:layout-component name="header" />
	<%--  header end --%>
	</head>
	<body>
	<table border="0" cellpadding="0" cellspacing="0" valign="top"
		width="986" align="center" style="vertical-align: top;">
		<tr>
			<td id="logo-header"></td>
			<td id="login"></td>
		</tr>
		<tr>
			<td id="menu" colspan="2"><%-- menu start --%> <s:layout-component
				name="menu" /> <%--  menu end --%></td>
		</tr>
		<tr>
			<td id="content" colspan="2">

			<table border="0" cellpadding="0" cellspacing="0" width="900"
				align="center">
				<tr>
					<td id="intbody">
					<table border="0" cellpadding="0" cellspacing="0" width="900"
						align="center">
						<tr>
							<td id="topmenu" colspan="2"><%-- topmenu start --%> <s:layout-component
								name="topmenu" /> <%--  topmenu end --%></td>
						</tr>
						<tr>
							<td id="sidemenu" style="vertical-align: top;"><%-- "sidemenu" start --%>
							<s:layout-component name="sidemenu" /> <%--  "sidemenu" end --%></td>
							<td id="pagecontent"><%--  pagecontent start --%> <s:layout-component
								name="body" /> <%--  pagecontent end --%></td>
						</tr>
						<tr>
							<td id="spacer" colspan="2"></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td align="center">
					<div id="footer"><%-- footer start --%> <s:layout-component
						name="footer" /> <%--  footer end --%></div>
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</body>
	</html>
</s:layout-definition>