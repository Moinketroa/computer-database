<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@attribute name="path" required="true"%>
<%@attribute name="offset" required="false"%>
<%@attribute name="entitiesPerPage" required="false"%>
<%@attribute name="keyword" required="false"%>
<%@attribute name="order" required="false"%>
<%@attribute name="mode" required="false"%>

<c:set var="path" value="${ path.concat('dashboard?') }" />

<c:if test="${ offset != null && offset > 0 }">
	<c:set var="path" value="${path.concat('&offset=').concat(offset)}" />
</c:if>

<c:if test="${ entitiesPerPage != null && entitiesPerPage > 0 }">
	<c:set var="path"
		value="${path.concat('&entitiesPerPage=').concat(entitiesPerPage)}" />
</c:if>

<c:if test="${ keyword != null && not empty keyword }">
	<c:set var="path" value="${path.concat('&keyword=').concat(keyword)}" />
</c:if>

<c:if test="${ order != null && not empty order }">
	<c:set var="path" value="${path.concat('&order=').concat(order)}" />
</c:if>

<c:if test="${ mode != null && not empty mode }">
	<c:set var="path" value="${path.concat('&mode=').concat(mode)}" />
</c:if>

<c:out value="${path}" escapeXml="false" />