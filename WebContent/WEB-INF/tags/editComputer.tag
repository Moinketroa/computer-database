<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@attribute name="path" required="true"%>
<%@attribute name="computerId" required="false"%>

<c:set var="path" value="${ path.concat('editComputer?') }" />

<c:if test="${ (computerId != null) && computerId.matches('[0-9]+') }">
	<c:set var="path"
		value="${path.concat('&computerId=').concat(computerId)}" />
</c:if>

<c:out value="${path}" escapeXml="false" />