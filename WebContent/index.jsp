<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Sign In</title>

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>

<!-- Custom styles for this template -->
<link href="signin.css" rel="stylesheet">
</head>

<body class="text-center">

	<main class="form-signin">
		<form action="login" method="post">
			<c:if test="${error != null}">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">Incorrect login
					credentials!
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</c:if>

			<h1 class="h3 mb-3 fw-normal">Sign In</h1>

			<div class="form-floating"> <!-- Removed type="email" -->
				<input type="text" class="form-control" id="txtEmail" maxlength="60"
					name="txtEmail" placeholder="username" required> <label
					for="txtEmail">Username</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="txtPassword" maxlength="60"
					name="txtPassword" placeholder="Password" required> <label
					for="txtPassword">Password</label>
			</div>

			<div class="checkbox mb-3">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
			</div>
			<button class="w-100 btn btn-lg btn-primary" type="submit">Sign
				in</button>
			<footer class="footer mt-auto py-3 bg-light">
				<div class="container text-center">
					<span class="text-muted text-center">Feel@home &copy; 2020-2022</span>
				</div>
			</footer>
		</form>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>

</html>