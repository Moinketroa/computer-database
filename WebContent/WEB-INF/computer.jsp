<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.min.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
	<jsp:include page="header.jsp" />

	<c:if test="${ not empty computer }">
		<section id="main">
		<div class="container">
			<h1 id="homeTitle">Details for Computer #${ computerId }</h1>
			<div class="pull-right">
				<a class="btn btn-default" id="editComputer" href='<cdb:link target="editComputer" computerId="${ computerId }"/>'>Edit <i class="far fa-edit"></i></a>
			</div>
		</div>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Table header for Computer Name -->
						<th>Computer name</th>
						<!-- Table header for Introduced Date -->
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<tr>
						<td id="computerName"><span>${ computer.name }</span></td>
						<td id="computerIntroduced"><fmt:parseDate pattern="yyyy-MM-dd"
								value="${ computer.introduced }" var="parsedDate" /> <fmt:formatDate
								value="${ parsedDate }" pattern="dd/MM/yyyy" /></td>
						<td id="computerDiscontinued"><fmt:parseDate pattern="yyyy-MM-dd"
								value="${ computer.discontinued }" var="parsedDate" />
							<fmt:formatDate value="${ parsedDate }" pattern="dd/MM/yyyy" />
						</td>
						<td id="computerCompany">${ computer.company.name }</td>
					</tr>
				</tbody>
			</table>
		</div>
		</section>
	</c:if>

	<c:if test="${ empty computer }">
		<section id="main">
		<div class="container">
			<h1 id="homeTitle">Computer not found</h1>
		</div>
		</section>
	</c:if>
</body>
</html>