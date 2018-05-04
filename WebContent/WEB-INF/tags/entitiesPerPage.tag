<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<div class="btn-group btn-group-sm pull-right" role="group">
	<a id="10entitiesPerPage"
		href="<cdb:link target="dashboard" offset="${ offset }" entitiesPerPage="10"/>"
		type="button" class="btn btn-default">10</a> 
		
	<a id="50entitiesPerPage"
		href="<cdb:link target="dashboard" offset="${ offset }" entitiesPerPage="50"/>"
		type="button" class="btn btn-default">50</a>
		 
	<a
		id="100entitiesPerPage"
		href="<cdb:link target="dashboard" offset="${ offset }" entitiesPerPage="100"/>"
		type="button" class="btn btn-default">100</a>
</div>