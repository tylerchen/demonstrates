<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:choose>
	<c:when test='<%= session.getAttribute("LOGINUSER") == null %>'>
		<jsp:forward page="/action/login" />
	</c:when>
	<c:otherwise>
		<jsp:forward page="/action/user" />
	</c:otherwise>
</c:choose>