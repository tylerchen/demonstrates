<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="d" uri="http://displaytag.sf.net"%>
<%@taglib prefix="jmesa" uri="/WEB-INF/tld/jmesa.tld"%>
<%@page isELIgnored="false"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="prefix" value="${actionBean.class.name}" />
<c:set var="title" value="Sample" />