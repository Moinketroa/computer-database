<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
	<jsp:include page="header.jsp" />

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1 id="title">Edit Computer #${ computerId }</h1>
				<div id="errorMsg" hidden="true" class="alert alert-danger"></div>
				<form id="editComputerForm" action='<cdb:link target="editComputer" computerId="${ computerId }"/>' method="POST">
					<fieldset>
						<div class="form-group">
							<label for="name">Computer name</label> <input type="text"
								class="form-control" id="name" name="name"
								placeholder="Computer name" value="${ computer.name }" onkeyup="verify()">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" id="introduced"
								name="introduced" placeholder="Introduced date" value="${ computer.introduced }" onchange="verify()">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" id="discontinued"
								name="discontinued" placeholder="Discontinued date" value="${ computer.discontinued }" onchange="verify()">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select
								class="form-control" id="companyId" name="companyId">
								<option value="0">No company</option>
								<c:forEach var="company" items="${ companies }">
									<c:choose>
										<c:when test="${ company.id == computer.company.id }">
											<option value="${ company.id }" selected>${ company.name }</option>
										</c:when>
										<c:otherwise>
											<option value="${ company.id }">${ company.name }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" id="editComputerButton" class="btn btn-primary">
						or <a href='<cdb:link target="computer" computerId="${ computerId }"/>' class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</section>
	
	
	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	function verify() {
		var isCorrect = true;
		
		if (!$('#name').val().trim()) {
			isCorrect = false;
			$('#errorMsg').show();
			$('#errorMsg').text("Please enter a non-empty name for the computer.");
		}
		
		if ($('#introduced').val().trim() && $('#discontinued').val().trim()) {
			if (toDate($('#introduced').val()) > toDate($('#discontinued').val())) {
				isCorrect = false;
				$('#errorMsg').show();
				$('#errorMsg').text("The introduction date is greater than the discontinuation date. Please enter valid dates.");
			}
		}
		
		if (isCorrect) {
			$('#errorMsg').hide();
			$('#editComputerButton').removeAttr('disabled');
		} else {
			$('#editComputerButton').attr('disabled', 'true');
		}
	}
	
	function toDate(formDate) {
		var dateArray = formDate.split('-');
		return new Date(dateArray[0], dateArray[1], dateArray[2]);
	}
	</script>
</body>
</html>