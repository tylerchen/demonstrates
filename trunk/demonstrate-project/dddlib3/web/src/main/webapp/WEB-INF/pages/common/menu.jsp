<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
	<span class="main-links">
	<ul>
		<c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login"/>" class="active"><span><fmt:message key="index.title"/></span></a></li></c:if>
		<menu:displayMenu name="MainMenu"/>
        <menu:displayMenu name="Categories"/>
        <menu:displayMenu name="ContactUs"/>
		<menu:displayMenu name="HowOututeWorks"/>
		<menu:displayMenu name="Logout"/>
	</ul>
	</span>
	<c:if test="${not empty pageContext.request.remoteUser}">
	<span class="main-links">
    <ul>
        <menu:displayMenu name="ManageTutorial"/>
        <menu:displayMenu name="OfferTutorial"/>
        <menu:displayMenu name="TakeTutorial"/>
        <menu:displayMenu name="UserMenu"/>
    </ul>
    </span>
    </c:if>
</menu:useMenuDisplayer>