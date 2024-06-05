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

<!-- Sheet JS for Table Export -->
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="fah.js"></script>
</head>
<body>
	<!-- Redirect to Login if user has not logged in -->
	<c:if test="${sessionScope.loggedinuser==null}">
		<c:redirect url="/index.jsp" />
	</c:if>

	<div class="container-fluid">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">

			<span class="navbar-brand">Feel@Home</span>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="dashboard">Dashboard</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="transactions">Transactions</a></li>
				</ul>
				<form class="d-flex me-4" action="logout">
					<span class="navbar-text me-4"><c:out
							value="${sessionScope.loggedinuser}" /></span> <a
						href="javascript:printscreen()" class="navbar-text"> <img
						class="rounded mx-auto me-3 text-center" height="20px"
						width="20px" src="icons/printer.png">
					</a>
					<button class="btn btn-outline-dark btn-sm" type="submit">Logout</button>
				</form>
			</div>
		</nav>
	</div>

	<div class="container">
		<nav aria-label="breadcrumb" style="--bs-breadcrumb-divider: '>';">
		  <ol class="breadcrumb">
		    <li class="breadcrumb-item">Transactions</a></li>
		    <li class="breadcrumb-item active" aria-current="page">Add/Update Records</li>
		  </ol>
		</nav>
	</div>
		<c:if test="${record.id>0}">
			<c:set var="updateScenario" value="yes"></c:set>
		</c:if>

		<div class="container mt-4">
		<c:if test="${error != null}">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">${error}
				<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
		</c:if>
			
		<form class="row g-3" action="<c:out value="${updateScenario=='yes'?'updateRecord':'addRecord'}"/>" method="post">
		
		<input type="hidden" name="id" id="id" value="<c:out value="${record.id}"/>"/>
		<input type="hidden" name="landedFromAddRecord" id="landedFromAddRecord" value="landedFromAddRecord"/>

			<div class="col-md-4">
				<label for="fdate" class="form-label">Date</label> <input
					type="date" name="edate" class="form-control" id="edate"
					placeholder="dd/MM/yyyy"
					value="<c:out value="${record.expenseDt}"/>" required>
			</div>
			<div class="col-md-4">
				<label for="incexp" class="form-label">Income/Expense</label> <select
					class="form-select" id="incexp" name="incexp" required>
					<option></option>

					<option
						<c:if test="${record.incomeExpense=='Income'}">	selected </c:if>>Income</option>
					<option
						<c:if test="${record.incomeExpense=='Expense'}"> selected </c:if>>Expense</option>
				</select>
			</div>
			<div class="col-md-4">
				<label for="category" class="form-label">Category</label> <select
					class="form-select firstList selectFilter" id="category"
					name="category" data-target="secondList" required>
					<option></option>
					<c:forEach var="mainCategory" items="${mainCategories}">
						<option data-ref="<c:out value="${mainCategory.id}" />"
							value="<c:out value="${mainCategory.id}" />"
							<c:if test="${record.mainCategory==mainCategory.id}">	selected </c:if>>
							<c:out value="${mainCategory.name}" />
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-4">
				<label for="subcategory" class="form-label">Sub-Category</label> <select
					class="form-select secondList selectFilter" id="subcategory"
					name="subcategory" required>
					<option></option>
					<c:forEach var="subCategory" items="${subCategories}">
						<option data-belong="<c:out value="${subCategory.mainId}"/>"
							value="<c:out value="${subCategory.id}" />"
							<c:if test="${record.subCategory==subCategory.id}">	selected </c:if>>
							<c:out value="${subCategory.name}" />
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-4">
				<label for="amount" class="form-label">Amount</label> <input
					type="text" class="form-control" id="amount"
					name="amount" placeholder="0.00" maxlength="8" onkeypress="return restrictAlphabets(event)"
					value="<c:out value="${record.amount}" />"
					aria-label="INR amount (with dot and two decimal places)" required>
			</div>

			<div class="col-md-4">
				<label for="comments" class="form-label">Comments</label> <select
					class="form-select" id="comments" name="comments" required>
					<option></option>
					<c:forEach var="responsiblePerson" items="${responsiblePersons}">
						<option value="<c:out value="${responsiblePerson.id}" />"
							<c:if test="${record.responsiblePerson==responsiblePerson.id}">	selected </c:if>>
							<c:out value="${responsiblePerson.responsiblePerson}" />
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-floating col-md-4">
				<textarea class="form-control" maxlength="70"
					placeholder="Describe this record here" id="desc" name="desc"
					style="height: 100px"><c:out value="${record.descr}" /></textarea>
				<label for="desc">Description</label>
			</div>
			<div class="col-12">
					<button class="btn btn-primary" type="submit"><c:out value="${updateScenario=='yes'?'Update Record':'Add Record'}"/></button>
			</div>
		</form>

	</div>
	
<script type="text/javascript">
function restrictAlphabets(e){
    var x = e.which || e.keycode;
  	if((x>=48 && x<=57)||x==46)
   		return true;
   	else
   		return false;
}

</script>
	<footer class="footer mt-auto py-3 bg-light">
		<div class="container text-center">
			<span class="text-muted text-center">@mandeep &copy;
				2024</span>
		</div>
	</footer>

	<!-- For Cascading Dropdowns -->
	<script src="selectFilter.min.js"></script>
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>