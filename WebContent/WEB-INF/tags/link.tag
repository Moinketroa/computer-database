<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@attribute name="target" required="true"%>
<%@attribute name="offset" required="false"%>
<%@attribute name="entitiesPerPage" required="false"%>
<%@attribute name="computerId" required="false"%>

<c:set var="path" value="/computer-database/" />

<c:if test="${ not empty target }">
	<c:choose>
		<c:when test="${ target.equals('dashboard') }">
			<cdb:dashboard path="${ path }" offset="${ offset }"
				entitiesPerPage="${ entitiesPerPage }"></cdb:dashboard>
		</c:when>

		<c:when test="${ target.equals('addComputer') }">
			<cdb:addComputer path="${ path }"></cdb:addComputer>
		</c:when>

		<c:when test="${ target.equals('computer') }">
			<cdb:computer path="${ path }" computerId="${ computerId }"></cdb:computer>
		</c:when>

		<c:otherwise>
			<cdb:dashboard path="${ path }"></cdb:dashboard>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${ empty target }">
	<cdb:dashboard path="${ path }"></cdb:dashboard>
</c:if>