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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
	<script src="fah.js"></script>
	
  </head>
  <body>
  	
  	<!-- Redirect to Login if user has not logged in -->
	<c:if test="${sessionScope.loggedinuser==null}">
    <c:redirect url="/index.jsp"/>
    </c:if>
    
    <div class="container-fluid">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
    
          <span class="navbar-brand">Feel@Home</span>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="dashboard">Dashboard</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="transactions">Transactions</a>
              </li>
            </ul>
             <form class="d-flex me-4" action="logout">
              <span class="navbar-text me-4"><c:out value="${sessionScope.loggedinuser}"/></span>
              <a href="javascript:printscreen()" class="navbar-text">
				<img class="rounded mx-auto me-3 text-center" height="20px" width="20px" src="icons/printer.png">
			 </a>
              <button class="btn btn-outline-dark btn-sm" type="submit">Logout</button>
            </form>
          </div>
        </nav>
      </div>

	<div class="container mt-4">
	  <button class="btn btn-primary btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFilters" aria-expanded="false" aria-controls="collapseFilters">
	    Show/Hide Filters
	  </button>
	</div>

	<div class="collapse" id="collapseFilters">
	  <div class="card card-body">
		<div class="container mt-4">
	        <form class="row g-3" action="dashboard" method="post">
	        <c:if test="${error != null}">
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
					 ${error}
					 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
				</c:if>
	          <div class="col-md-4">
	            <label for="fdate" class="form-label">From Date</label>
	            <input type="date" name="fdate" class="form-control" id="fdate" placeholder="dd/MM/yyyy" value="<c:out value="${conditions.fromDate}"/>">
	          </div>
	          <div class="col-md-4">
	            <label for="tdate" class="form-label">To Date</label>
	            <input type="date" name="tdate" class="form-control" id="tdate" placeholder="dd/MM/yyyy" value="<c:out value="${conditions.toDate}"/>">
	          </div>
	          <div class="col-md-4">
						<label for="incexp" class="form-label">Income/Expense</label> <select
							class="form-select" id="incexp" name="incexp">
							<option></option>
							 
							<option 
							<c:if test="${conditions.incomeExpense=='Income'}">	selected </c:if>
              				>Income</option>
							<option <c:if test="${conditions.incomeExpense=='Expense'}"> selected </c:if>
             				 >Expense</option>
						</select> 
					</div>
					<div class="col-md-4">
						<label for="category" class="form-label">Category</label> <select
							class="form-select firstList selectFilter" id="category"
							name="category" data-target="secondList">
							<option></option>
							<c:forEach var="mainCategory" items="${mainCategories}">
								<option 
									data-ref="<c:out value="${mainCategory.id}" />"
									value="<c:out value="${mainCategory.id}" />"
									<c:if test="${conditions.mainCategory==mainCategory.id}">	selected </c:if>>
									<c:out value="${mainCategory.name}" />
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-4">
						<label for="subcategory" class="form-label">Sub-Category</label> <select
							class="form-select secondList selectFilter" id="subcategory"
							name="subcategory">
							<option></option>
							<c:forEach var="subCategory" items="${subCategories}">
								<option 
								  data-belong="<c:out value="${subCategory.mainId}"/>"
								  value="<c:out value="${subCategory.id}" />"
								  <c:if test="${conditions.subCategory==subCategory.id}">	selected </c:if>>
								  <c:out value="${subCategory.name}" />
								 </option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-4">
						<label for="comments" class="form-label">Comments</label> <select
							class="form-select" id="comments" name="comments">
							<option></option>
							<c:forEach var="responsiblePerson" items="${responsiblePersons}">
								<option 
								  value="<c:out value="${responsiblePerson.id}" />"
								  <c:if test="${conditions.responsiblePerson==responsiblePerson.id}">	selected </c:if>>
								  <c:out value="${responsiblePerson.responsiblePerson}" />
								 </option>
							</c:forEach>
						</select>
					</div>
         
          <div class="col-12">
            <button class="btn btn-primary" type="submit">Generate Chart</button>
          </div>         
        </form>    
      </div>
  </div>
</div>
 
 <div class="container mt-4">
  <div class="row">
    <div class="col-6">
     <canvas id="chart1"></canvas>
    </div>
    <div class="col-6">
     <canvas id="chart3"></canvas>
    </div>
    </div>
    <div class="row mt-4">
    <div class="col-4">
     <canvas id="chart2"></canvas>
    </div>
    </div>
</div>
      
      
<script>
var dynamicColors = function() {
    var r = Math.floor(Math.random() * 255);
    var g = Math.floor(Math.random() * 255);
    var b = Math.floor(Math.random() * 255);
    return "rgb(" + r + "," + g + "," + b + ")";
 };
 
 var catX=[];
 var catY=[];
 var catColors=[];

 var catJson=${chartbycategoryjson};
 for (var i = 0; i < catJson.length; i++) {
	catX.push(catJson[i].mainCategory); 
	catY.push(catJson[i].finalAmt); 
	catColors.push(dynamicColors());
 }

 new Chart("chart1", {
   type: "bar",
   data: {
     labels: catX,
     datasets: [{
       backgroundColor: catColors,
       data: catY
     }]
   },
   options: {
     legend: {display: false},
     title: {
       display: true,
       text: "Spends by Category"
     }
   }
 });
</script>
      
<script>
var incX=[];
var incY=[];
var incColors=[];

var incJson=${chartbyincexpjson};
for (var i = 0; i < incJson.length; i++) {
	incX.push(incJson[i].incomeExpense); 
	incY.push(incJson[i].amount); 
	incColors.push(dynamicColors());
}

new Chart("chart2", {
  type: "doughnut",
  data: {
    labels: incX,
    datasets: [{
      backgroundColor: incColors,
      data: incY
    }]
  },
  options: {
    legend: {display: false},
    title: {
      display: true,
      text: "Income / Expense Visualization"
    }
  }
});

</script>   

<script>
var perX=[];
var perY=[];
var perColors=[];

var Jsonperson=${chartbypersonjson};
for (var i = 0; i < Jsonperson.length; i++) {
	perX.push(Jsonperson[i].responsiblePerson); 
	perY.push(Jsonperson[i].amount); 
	perColors.push(dynamicColors());
}

new Chart("chart3", {
  type: "bar",
  data: {
    labels: perX,
    datasets: [{
      backgroundColor: perColors,
      data: perY
    }]
  },
  options: {
    legend: {display: false},
    title: {
      display: true,
      text: "Spends by Person"
    }
  }
});

</script>   
      

    <footer class="footer mt-auto py-3 bg-light">
        <div class="container text-center">
          <span class="text-muted text-center">Mandeep &copy; 2024</span>
        </div>
      </footer>
    
    <!-- For Cascading Dropdowns -->
    <script src="http://code.jquery.com/jquery.min.js"></script>
    <script src="selectFilter.min.js"></script>
   
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>