<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cdb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
	<jsp:include page="header.jsp" />

	<c:if test="${ totalNumberOfComputers > 0 }">
		<section id="main">
		<div class="container">
			<h1 id="homeTitle">${ totalNumberOfComputers } Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="<cdb:link target="addComputer"/>">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="<cdb:link target="deleteComputer"/>" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<c:if test="${ not empty computers }">
			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall"/> <span
								style="vertical-align: top;"><span id="trashCan" hidden> - <a href='#'
									id="deleteSelected" onclick="$.fn.deleteSelected();"><i class="fas fa-trash-alt"></i>
								</a></span>
							</span></th>
							<th>Computer name</th>
							<th>Introduced date</th>
							<!-- Table header for Discontinued Date -->
							<th>Discontinued date</th>
							<!-- Table header for Company -->
							<th>Company</th>

						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach var="computer" items="${ computers }">
							<tr id="computerRow">
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${ computer.id }"></td>
								<td id="computerName"><a
									href="<cdb:link target="computer"	computerId="${ computer.id }"/>">${ computer.name }</a></td>
								<td id="computerIntroduced"><fmt:parseDate pattern="yyyy-MM-dd"
										value="${ computer.introduced }" var="parsedDate" />
									<fmt:formatDate value="${ parsedDate }" pattern="dd/MM/yyyy" />
								</td>
								<td id="computerDiscontinued"><fmt:parseDate pattern="yyyy-MM-dd"
										value="${ computer.discontinued }" var="parsedDate" />
									<fmt:formatDate value="${ parsedDate }" pattern="dd/MM/yyyy" />
								</td>
								<td id="computerCompany">${ computer.company.name }</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if> </section>

		<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<cdb:page />

			<cdb:entitiesPerPage />
		</div>
		</footer>
	</c:if>

	<c:if test="${ totalNumberOfComputers <= 0 }">
		<section id="main">
		<div class="container">
			<h1 id="homeTitle">No Computer found</h1>
		</div>
		</section>
	</c:if>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
</body>
</html>