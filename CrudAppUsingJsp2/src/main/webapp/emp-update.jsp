<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div align="center">
				<a class="navbar-brand"> Employee Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link"></a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${emp != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${emp == null}">
					<form>
				</c:if>

				<caption>
					<h2>
						<c:if test="${emp != null}">
            			Edit User
            		    </c:if>
						<c:if test="${emp == null}">
            			Add New User
            		    </c:if>
					</h2>
				</caption>

				<c:if test="${emp != null}">
					<input type="hidden" id="id" name="id"
						value="<c:out value='${emp.id}' />" />
					<%-- <input type="hidden" name="id1" value="<c:out value='${emp.skillid}' />" /> --%>
				</c:if>

				<fieldset class="form-group">
					<label>Name</label> <input type="text" id="name"
						value="<c:out value='${emp.name}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Age</label> <input type="number" id="age"
						value="<c:out value='${emp.age}' />" class="form-control"
						name="age" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Salary</label> <input type="Number" step="0.01" id="salary"
						value="<c:out value='${emp.salary}' />" class="form-control"
						name="salary" required="required">
				</fieldset>


				<fieldset class="form-group">
					<label>Skills: </label>${emp.skills}<input type="text" id="skills"
						value="<c:out value='${emp.skills}' />" class="form-control"
						name="skills" required="required">

				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
		<div class="row">
			<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

			<div class="container">
				<h3 class="text-center">List of Employee</h3>
				<hr>


				<br>

				<table id="employee-table" class="table table-bordered">
					<thead>
						<tr>
							<th>Name</th>
							<th>Age</th>
							<th>Salary</th>
							<th>Skills</th>
							<th>Actions</th>
						</tr>
					<tbody>

						<c:forEach var="emp" items="${listemp}">
							<tr>
								<td><c:out value="${emp.name}" /></td>
								<td><c:out value="${emp.age}" /></td>
								<td><c:out value="${emp.salary}" /></td>
								<td><c:out value="${emp.skills}" /></td>


								<td><a class="edit-link" onclick="onclickedit(this)"
									data-id="<c:out value='${emp.id}' />">Edit</a> <a
									class="delete-link" onclick="deleteEmployee()"
									data-id="<c:out value='${emp.id}' />">Delete</a></td>
							</tr>
						</c:forEach>

					</tbody>

				</table>
			</div>

		</div>
	</div>

</body>
</html>
<script>
	var selectedrow = null;
	function deleteEmployee() {
		$(document).ready(function() {

			$(".delete-link").click(function(e) {
				e.preventDefault();

				var empId = $(this).data("id");
				console.log(empId);

				$.ajax({
					url : "/CrudAppUsingJsp2/delete",
					type : "GET",
					headers : {
						"X-HTTP-Method-Override" : "DELETE"
					},
					data : {
						id : empId
					},

					success : function(response) {

						$("#employee-table").load(" #employee-table > *");
					},
					error : function(error) {
						console.log("Error:", error);
					}
				});

			});
		});
	}

	$(document).ready(function() {

		$("form").submit(function(e) {
			e.preventDefault();

			var formData = $(this).serialize();
			if (selectedrow == null) {

				$.ajax({
					url : "/CrudAppUsingJsp2/insert",
					type : "POST",
					data : formData,
					success : function(response) {
						console.log(formData);
						console.log("in if condition");
						$("#employee-table").load(" #employee-table > *");
					},
					error : function(error) {
						console.log("Error:", error);
					}
				});
			} else {
				var empId = $("#id").val();
				formData += "&id=" + globalId;
				$.ajax({
					url : "/CrudAppUsingJsp2/update",
					type : "POST",
					data : formData,
					success : function(response) {
						console.log(formData);
						console.log("in else condition");
						$("#employee-table").load(" #employee-table > *");
					},
					error : function(error) {
						console.log("Error:", error);
					}
				});
			}
			selectedrow = null;
		});
	});
	var globalId;
	function onclickedit(td) {
		selectedrow = td.parentElement.parentElement;
		$(document)
				.ready(
						function() {
							$(".edit-link")
									.click(
											function(e) {
												e.preventDefault();
												var empId = $(this).data("id");
												globalId = empId;
												var name = this.parentElement.parentElement.cells[0].innerHTML;
												var age = this.parentElement.parentElement.cells[1].innerHTML;
												var salary = this.parentElement.parentElement.cells[2].innerHTML;

												var skills = this.parentElement.parentElement.cells[3].innerHTML;

												console.log(skills);

												$
														.ajax({
															url : "/CrudAppUsingJsp2/edit",
															type : "GET",
															data : {
																id : empId
															},
															success : function(
																	response) {
																$("#id").val(
																		empId);
																$("#name").val(
																		name);
																$("#age")
																		.val(
																				age);
																$("#salary")
																		.val(
																				salary);
																$("#skills")
																		.val(
																				skills);

															},
															error : function(
																	error) {
																console
																		.log(
																				"Error:",
																				error);
															}
														});
											});

						});
	}
</script>



