<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%--
<c:set var="newLineString" scope="request" value=""/>
 --%>
<c:if test="${newLineString!=null}"><%=request.getAttribute("newLineString").toString().replaceAll("\\n", "<br/>")%></c:if>