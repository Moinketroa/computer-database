<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>

<%@attribute name="path" required="true"%>

<c:set var="path" value="${ path.concat('logout') }" />

<c:out value="${path}" escapeXml="false" />