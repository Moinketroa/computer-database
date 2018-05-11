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
				entitiesPerPage="${ entitiesPerPage }"/>
		</c:when>

		<c:when test="${ target.equals('addComputer') }">
			<cdb:addComputer path="${ path }"/>
		</c:when>
		
		<c:when test="${ target.equals('editComputer') }">
			<cdb:editComputer path="${ path }" computerId="${ computerId }"/>
		</c:when>

		<c:when test="${ target.equals('computer') }">
			<cdb:computer path="${ path }" computerId="${ computerId }"/>
		</c:when>
		
		<c:when test="${ target.equals('deleteComputer') }">
			<cdb:deleteComputer path="${ path }" computerId="${ computerId }"/>
		</c:when>

		<c:otherwise>
			<cdb:dashboard path="${ path }"/>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${ empty target }">
	<cdb:dashboard path="${ path }"/>
</c:if>