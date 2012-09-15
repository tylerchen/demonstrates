<%@ include file="/common/taglibs.jsp"%>
	<div class="top-box">
		<div class="top-left floatl">
			<fmt:message key="header.logo">
				<fmt:param><c:url value="/mainMenu"/></fmt:param>
			</fmt:message></li>
		</div>
		<div class="top-right floatl">
			<c:if test="${pageContext.request.locale.language ne 'en'}">
				<div id="switchLocale"><a href="<c:url value='/?locale=en'/>"><fmt:message key="webapp.name"/>in English</a></div>
			</c:if>
		</div>
		<div class="top-right floatr">
			<!-- AddThis Button BEGIN -->
			<div class="addthis_toolbox addthis_default_style ">
				<a href="http://www.addthis.com/bookmark.php?v=250&amp;pubid=ra-4deee9d86173325e" class="addthis_button_compact STYLE1">
					<fmt:message key="header.follow" />
				</a>
				<span class="addthis_separator">|</span>
				<a class="addthis_button_preferred_1"></a>
				<a class="addthis_button_preferred_2"></a>
				<a class="addthis_button_preferred_3"></a>
				<a class="addthis_button_preferred_4"></a>
			</div>
			<script type="text/javascript">var addthis_config = {"data_track_clickback":true};</script>
			<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-4deee9d86173325e"></script>
			<!-- AddThis Button END -->
		</div>

		<div class="top_right_lower floatr">
			<%@ include file="/common/menu.jsp" %>
		</div>
	</div>
<%-- Put constants into request scope --%>
<appfuse:constants scope="request"/>