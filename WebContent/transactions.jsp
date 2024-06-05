<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
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
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.2/css/dataTables.bootstrap5.min.css">

<!-- Sheet JS for Table Export -->
	<script src="http://code.jquery.com/jquery.min.js"></script>
	<script
	src="https://cdn.rawgit.com/eligrey/FileSaver.js/e9d941381475b5df8b7d7691013401e171014e89/FileSaver.min.js"></script>
	<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.15.6/xlsx.full.min.js"></script>
	
	<script src="https://cdn.datatables.net/1.11.0/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.11.0/js/dataTables.bootstrap5.min.js"></script>
	
	<script src="fah.js"></script>
</head>
<body>

	<input type="hidden" name="token" id="token" value="${Math.random()}"/>
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
                <a class="nav-link" aria-current="page" href="dashboard">Dashboard</a>
              </li>
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="transactions">Transactions</a>
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

	 <c:if test="${error != null}">
		<div class="alert alert-danger alert-dismissible fade show" role="alert">${error}
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</c:if>
				
	<div class="container mt-4">
	  <button class="btn btn-primary btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFilters" aria-expanded="false" aria-controls="collapseFilters">
	    Show/Hide Filters
	  </button>
	</div>

		
	<div class="collapse" id="collapseFilters">
		<div class="card card-body">
			<div class="container mt-4">
				<form class="row g-3" action="transactions" method="post">
					
					<div class="col-md-4">
						<label for="fdate" class="form-label">From Date</label> <input
							type="date" name="fdate" class="form-control" id="fdate"
							placeholder="dd/MM/yyyy"
							value="<c:out value="${conditions.fromDate}"/>">
					</div>
					<div class="col-md-4">
						<label for="tdate" class="form-label">To Date</label> <input
							type="date" name="tdate" class="form-control" id="tdate"
							placeholder="dd/MM/yyyy"
							value="<c:out value="${conditions.toDate}"/>">
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
						<button class="btn btn-primary" type="submit">Filter
							Results</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-3 offset-9">
	<form action="loadRecord" method="post">
		<button class="btn btn-primary btn-sm" type="submit">Add
			Record</button>
		<button class="btn btn-outline-primary btn-sm" id="button-a">Download
			Excel</button>
	</form>
	</div>
	
<form action="" method="post" id="trntableform">
	<div class="container mt-4">
		<div class="table-responsive">
			<table class="table table-striped table-hover table-sm" id="transactiontbl">
				<thead>
					<tr>
						<th scope="col">Date</th>
						<th scope="col">Income/Expense</th>
						<th scope="col">Category</th>
						<th scope="col">Sub-Category</th>
						<th scope="col">Description</th>
						<th scope="col">Amount</th>
						<th scope="col">Comments</th>
						<c:if test="${sessionScope.loggedinuserrole=='ADMIN'}">
							<th scope="col">Actions</th>
						</c:if>
						
					</tr>
				</thead>
				<tbody>
					<!-- Define counters -->
					<c:set value="0" var="income"/>
					<c:set value="0" var="expense"/>
					<c:set value="0" var="total"/>
					
					<c:forEach var="records" items="${records}">
					
						<tr>
							<td><c:out value="${records.expenseDt}" /></td>
							<td><c:out value="${records.incomeExpense}" /></td>
							<td><c:out value="${records.mainCategory}" /></td>
							<td><c:out value="${records.subCategory}" /></td>
							<td><c:out value="${records.descr}" /></td>
							<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${records.amount}"/></td>
							
							<c:if test="${records.incomeExpense=='Income'}">
								<c:set var="income" value="${income+records.amount}"></c:set>
								<c:set var="total" value="${total+records.amount}"></c:set>
							</c:if>
							
							<c:if test="${records.incomeExpense=='Expense'}">
								<c:set var="expense" value="${expense+records.amount}"></c:set>
								<c:set var="total" value="${total-records.amount}"></c:set>
							</c:if>
							
							<td><c:out value="${records.responsiblePerson}" /></td>
							<c:if test="${sessionScope.loggedinuserrole=='ADMIN'}">
							<td>
							  <a href="loadRecord?action=edit&id=<c:out value='${records.id}' />" title="Edit" 
							  style="text-decoration: none;">
									<img height="15px" width="15px" src="icons/pencil.png">
							 </a>
							 <a href="#" title="Delete" 
							  data-bs-toggle="modal" data-bs-target="#confirmationModal" data-bs-whatever="<c:out value='${records.id}' />"
							 style="text-decoration: none;">
									<img height="15px" width="15px" src="icons/dustbin.png">
							 </a>
							</td>
							</c:if>
						</tr>
					</c:forEach>
						<tfoot>
							<td><b>Total Income</b></td>
							<td><b><fmt:formatNumber type="number" maxFractionDigits="2" value="${income}"/><b></td>
							<td><b>Total Expenses</b></td>
							<td><b><fmt:formatNumber type="number" maxFractionDigits="2" value="${expense}"/><b></td>
							<td><b>Grand Total</b></td>
							<td><b><fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/><b></td>
							<td>&nbsp;</td>							
							<c:if test="${sessionScope.loggedinuserrole=='ADMIN'}">
								<td>&nbsp;</td>
							</c:if>
						</tfoot>
				</tbody>
			</table>
		</div>
	</div>

	<!-- This to be used later for setting the id of record being deleted -->
	<input type="hidden" name="rid" id="rid"/>
	
	<script>
        var wb = XLSX.utils.table_to_book(document.getElementById('transactiontbl'), {sheet:"Sheet JS"});
        var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:true, type: 'binary'});

        function s2ab(s) {

                        var buf = new ArrayBuffer(s.length);
                        var view = new Uint8Array(buf);
                        for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF;
                        return buf;
        }
        $("#button-a").click(function(){
        saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), 'transactions.xlsx');
        });

</script>

<script>
$(function() {
  $(document).ready(function() {
    $('#transactiontbl').DataTable({
        "ordering": false
    });
  });
});
</script>

<!-- Modal Confirmation Code -->

<!-- Modal -->
<div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModal" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmationModalLabel">Are you sure </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cancel"></button>
      </div>
      <div class="modal-body">
         You want to delete this entry ?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="confirm_cancel">Cancel</button>
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="confirm_ok">Confirm</button>
      </div>
    </div>
  </div>
</div>

<script>
var recordid=0;
 var exampleModal = document.getElementById('confirmationModal');
 exampleModal.addEventListener('show.bs.modal', function (event) {
  // Button that triggered the modal
  var button = event.relatedTarget
  // Extract info from data-bs-* attributes
  var recipient = button.getAttribute('data-bs-whatever')
      recordid = button.getAttribute('data-bs-whatever');
});
 
$('#confirm_ok').on("click", function() {
		$('#confirmationModal').modal('hide');
		
		var rid = document.getElementById("rid");
		rid.value = recordid;
		//alert(rid.value)
		
		// Submit a form
		var form = document.getElementById("trntableform")
				   form.action="deleteRecord";
			//alert(form.action)
				   form.submit();
});
</script>

	 <footer class="footer mt-auto py-3 bg-light">
        <div class="container text-center">
          <span class="text-muted text-center">Feel@home &copy; 2020-2022</span>
        </div>
      </footer>

	<!-- For Cascading Dropdowns -->
	<script src="selectFilter.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
		
		<!-- This has a dependency on bootsrap js , so needs to be defined below -->
		<!-- Show toast message if available -->
		
		<!-- Toast Block -->
		<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
			  <div id="liveToast" class="toast hide" role="alert" aria-live="assertive" aria-atomic="true">
			    <div class="toast-header text-dark bg-light">
			      <strong class="me-auto">Message</strong>
			      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
			    </div>
			    <div class="toast-body text-white bg-success border-0" >
			      ${msg}
			    </div>
			  </div>
			</div>

		<c:if test="${msg!=null}">
			<script>
					var toastElList = [].slice.call(document.querySelectorAll('.toast'))
			        var toastList = toastElList.map(function(toastEl) {
			        // Creates an array of toasts (it only initializes them)
			          return new bootstrap.Toast(toastEl) // No need for options; use the default options
			        });
			       toastList.forEach(toast => toast.show()); // This show them
			
			</script>
		</c:if>	
</form>
</body>
</html>