<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<ul class="pagination">
	<c:if test="${ isPreviousPageAvailable }">
		<li><a id="previousPageLink"
			href="<cdb:link target="dashboard" offset="${ previousPageOffset }" entitiesPerPage="${ entitiesPerPage }"/>"
			aria-label="Previous"> <span aria-hidden="true">&laquo;
					Previous Page</span>
		</a></li>
	</c:if>
	<c:if test="${ isNextPageAvailable }">
		<li><a id="nextPageLink"
			href="<cdb:link target="dashboard" offset="${ nextPageOffset }" entitiesPerPage="${ entitiesPerPage }"/>"
			aria-label="Next"> <span aria-hidden="true">Next Page
					&raquo;</span>
		</a></li>
	</c:if>
</ul>