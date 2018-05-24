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

	<c:if test="${ page.totalNumberOfComputers > 0 }">
		<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:if test="${ not empty keyword }">
					Results for "${ keyword }" : 
				</c:if>
				${ page.totalNumberOfComputers } Computers found</h1>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="<cdb:link target="dashboard"/>" method="GET" class="form-inline">

						<input type="keyword" id="searchbox" name="keyword"
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

		<c:if test="${ not empty page.computers }">
			<div class="container" style="margin-top: 10px;">
				<table id="computerTable" class="table table-striped table-bordered">
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
							<th>
								<a id="nameHeaderA" href="<cdb:link target="dashboard" order="name" mode="asc"/>" hidden> </a>
								<a id="nameHeaderD" href="<cdb:link target="dashboard" order="name" mode="desc"/>" hidden> </a>
							</th>
							<th>
								<a id="introducedHeaderA" href="<cdb:link target="dashboard" order="introduced" mode="asc"/>" hidden> </a>
								<a id="introducedHeaderD" href="<cdb:link target="dashboard" order="introduced" mode="desc"/>" hidden> </a>
							</th>
							<th> 
								<a id="discontinuedHeaderA" href="<cdb:link target="dashboard" order="discontinued" mode="asc"/>" hidden> </a>
								<a id="discontinuedHeaderD" href="<cdb:link target="dashboard" order="discontinued" mode="desc"/>" hidden> </a>
							</th>
							<th> 
								<a id="companyHeaderA" href="<cdb:link target="dashboard" order="company" mode="asc"/>" hidden> </a>
								<a id="companyHeaderD" href="<cdb:link target="dashboard" order="company" mode="desc"/>" hidden> </a>
							</th>

						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach var="computer" items="${ page.computers }">
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

	<c:if test="${ page.totalNumberOfComputers <= 0 }">
		<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:if test="${ not empty keyword }">
					Results for "${ keyword }" : 
				</c:if>
				No Computer found
			</h1>
		</div>
		</section>
	</c:if>

	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
	
	<script type="text/javascript">
	$(function() {
	    var newMode;
	    var arrow;
	    
	    var nameH;
	    var introducedH;
	    var discontinuedH;
	    var companyH;
	    
	    if (${ mode.equals('asc') }) {
	    	newMode = 'desc';
	    	arrow = '<i class="fas fa-arrow-up"></i>';
	    	
	    	nameH = $('#nameHeaderD');
	    	introducedH = $('#introducedHeaderD');
	    	discontinuedH = $('#discontinuedHeaderD');
	    	companyH = $('#companyHeaderD');
	    } else {
	    	newMode = 'asc';
	    	arrow = '<i class="fas fa-arrow-down"></i>';
	    	
	    	nameH = $('#nameHeaderA');
	    	introducedH = $('#introducedHeaderA');
	    	discontinuedH = $('#discontinuedHeaderA');
	    	companyH = $('#companyHeaderA');
	    }
	   
	    nameH.html("Computer Name " + arrow);
	    nameH.show();
	    
	    introducedH.html("Introduced date " + arrow);
	    introducedH.show();
	    
	    discontinuedH.html("Discontinued date " + arrow);
	    discontinuedH.show();
	    
	    companyH.html("Company " + arrow);
	    companyH.show();
	});
	</script>
</body>
</html>