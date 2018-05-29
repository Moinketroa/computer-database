<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
				<h1>Add Computer</h1>
				<div id="errorMsg" hidden="true" class="alert alert-danger"></div>
				<form:form method="POST" modelAttribute="computerDto">
					<fieldset>
						<div class="form-group">
							<form:label path="name">Computer name *</form:label> 
							<form:input type="text" class="form-control" id="name" 
								path="name" placeholder="Computer name" onkeyup="verify()"/>
							<form:errors style="color : red;" path="name"/>
						</div>
						<div class="form-group">
							<form:label for="introduced" path="introduced">Introduced date</form:label> 
							<form:input type="date" class="form-control" id="introduced"
								path="introduced" placeholder="Introduced date" onchange="verify()"/>
							<form:errors style="color : red;" path="introduced"/>
						</div>
						<div class="form-group">
							<form:label for="discontinued" path="discontinued">Discontinued date</form:label> 
							<form:input type="date" class="form-control" id="discontinued"
								path="discontinued" placeholder="Discontinued date" onchange="verify()"/>
							<form:errors style="color : red;" path="discontinued"/>
						</div>
						<div class="form-group">
							<form:label path="company.id" for="companyId">Company</form:label> 
							<form:select class="form-control" id="companyId" path="company.id">
								<form:option value="0">No company</form:option>
								<c:forEach var="company" items="${ companies }">
									<form:option value="${ company.id }">${ company.name }</form:option>
								</c:forEach>
							</form:select>
							<form:errors style="color : red;" path="company.id"/>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" disabled="true" value="Add" id="addComputerButton" class="btn btn-primary">
						or <a href="<cdb:link target="dashboard"/>" class="btn btn-default">Cancel</a>
					</div>
				</form:form>
				
				<label>* : Mandatory fields</label>
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
			$('#addComputerButton').removeAttr('disabled');
		} else {
			$('#addComputerButton').attr('disabled', 'true');
		}
	}
	
	function toDate(formDate) {
		var dateArray = formDate.split('-');
		return new Date(dateArray[0], dateArray[1], dateArray[2]);
	}
	</script>
</body>
</html>