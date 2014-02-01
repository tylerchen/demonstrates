<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Demo</title>
<jsp:include page="/pages/common/header.jsp" />
</head>
<body>
<h1>Hello ${hello}
<h1 />
<h1>${pageContext.request.contextPath}</h1>
<div id="baseInfoGrid"></div>
<p></p>
<form action="core-Demo-submit.action" method="post" id="test"><input
	type="hidden" name="user.id" value="${user.id}" /> <input
	type="hidden" name="user.role.id" value="${user.role.id}" />
<table width="50%" align="center">
	<tbody>
		<tr>
			<td>用户名</td>
			<td><input type="text" name="user.userName"
				value="${user.userName}" /></td>
		</tr>
		<tr>
			<td>角色</td>
			<td><select name="user.role.id">
				<option value="-">-</option>
				<c:forEach items="${roles}" var="r">
					<option value="${r.id}">${r.name}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td><input type="submit" name="保存" /></td>
			<td></td>
		</tr>
	</tbody>
</table>
</form>
</body>
<script type="text/javascript">
$(function (){
	initBaseInfoGrid();
});
function initBaseInfoGrid(){
	var searchParams=$("#test").serializeArray();
	baseInfoGrid = $("#baseInfoGrid").ligerGrid({
        url : "${ctx}/core/demo-Demo-query.action",
		width : '100%',
		//height : '100%',
        sortName : 'id',
        root : 'rows',
        pageParmName: 'currentPage',                  
        record : 'totalSize',
        parms : searchParams,
        checkbox: true,
    	columns : [
            { display : 'ID'  , name : 'accountId'  , minWidth : 100 },
            { display : 'Name', name : 'username', minWidth : 100 }
        ]
    });
}
</script>
</html>