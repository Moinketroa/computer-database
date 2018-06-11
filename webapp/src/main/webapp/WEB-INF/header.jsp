<%@ taglib tagdir="/WEB-INF/tags" prefix="cdb"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div>
			<a class="navbar-brand"
				href='<cdb:link target="dashboard" offset="0"/>'> Application -
				Computer Database </a>
		</div>

		<div class="navbar-nav" style="float: right;">
			<c:if test="${ not empty username }">
				<div class="navbar-brand">Hello ${ username } !</div>
			</c:if>
			<a class="navbar-brand navbar-link" href='<cdb:link target="logout"/>'>Logout</a>
		</div>
	</div>
</header>