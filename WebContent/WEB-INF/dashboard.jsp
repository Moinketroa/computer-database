<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
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
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<c:if test="${ not empty computers }">
			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 65px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
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
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="0"></td>
								<td><a href="./computer?computerId=${ computer.computer.id }">${ computer.computer.name }</a></td>
								<td><fmt:parseDate pattern="yyyy-MM-dd"
										value="${ computer.computer.introduced }" var="parsedDate" />
									<fmt:formatDate value="${ parsedDate }" pattern="dd/MM/yyyy" />
								</td>
								<td><fmt:parseDate pattern="yyyy-MM-dd"
										value="${ computer.computer.discontinued }" var="parsedDate" />
									<fmt:formatDate value="${ parsedDate }" pattern="dd/MM/yyyy" />
								</td>
								<td>${ computer.company.name }</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if> </section>

		<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${ isPreviousPageAvailable }">
					<li><a
						href="./dashboard?offset=${ previousPageOffset }&entitiesPerPage=${ entitiesPerPage }"
						aria-label="Previous"> <span aria-hidden="true">&laquo;
								Previous Page</span>
					</a></li>
				</c:if>
				<c:if test="${ isNextPageAvailable }">
					<li><a
						href="./dashboard?offset=${ nextPageOffset }&entitiesPerPage=${ entitiesPerPage }"
						aria-label="Next"> <span aria-hidden="true">Next Page
								&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="./dashboard?offset=${ offset }&entitiesPerPage=10"
					type="button" class="btn btn-default">10</a> <a
					href="./dashboard?offset=${ offset }&entitiesPerPage=50"
					type="button" class="btn btn-default">50</a> <a
					href="./dashboard?offset=${ offset }&entitiesPerPage=100"
					type="button" class="btn btn-default">100</a>
			</div>
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