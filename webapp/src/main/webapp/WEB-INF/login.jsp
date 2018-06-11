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
<link href="static/css/bootstrap.css" rel="stylesheet"
	media="screen">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.13/css/all.css">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Welcome to Computer Database</h1>
				<h4>Please log in</h4>
				<c:if test="${ not empty error }">
					<div id="errorMsg" class="alert alert-danger">Login failed ! Please re-try</div>
				</c:if>

				<form action="<cdb:link target="dashboard"/>" method="POST">
					<div class="form-group">
						<div>Username</div>
						<input class="form-control" type="text" name="username">
					</div>
					<div class="form-group">
						<div>Password</div>
						<input class="form-control" type="password" name="password">
					</div>
					<div class="actions pull-right">
						<button class="btn btn-primary" type="submit">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</section>

</body>
</html>