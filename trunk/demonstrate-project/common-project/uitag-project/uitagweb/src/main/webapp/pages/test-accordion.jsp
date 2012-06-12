<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="/WEB-INF/ui.tld" prefix="ui"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/js/jquery/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ligerUI/js/ligerui.min.js"></script>
<style type="text/css">
</style>
</head>
<body>
<div style="width: 200px;"><ui:accordion>
	<ui:accordion title="test" value="a;b;c;d"></ui:accordion>
	<ui:accordion title="test" value="a;b;c;d"></ui:accordion>
</ui:accordion> <ui:accordion title="test" value="a;b;c;d"></ui:accordion></div>
</body>
</html>