<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="PSG Admin">
	<meta name="author" content="PSG Admin">
	<meta name="keywords" content="PSG Admin">
	<meta name="_csrf" content="${_csrf.token}" />
	<meta name="_csrf_header" content="${_csrf.headerName}" />

	<link rel="shortcut icon" href="<c:url value='/images/logo.png'/> " />

	<title>PSG</title>

	<link href="<c:url value='/css/app.css'/> " rel="stylesheet">
	
	<script src="<c:url value='/js/jquery.min.js'/> "></script>
	
	<script>
	/* function slackValidChkFn(slack_name) {
		var reg = /^(?=.*[a-zA-Z]).{5,15}$/;
		
		if(!reg.test(slack_name)) {
			show_info("슬랙 사용자명이 유효하지 않습니다");
			
			return false;
		}
		
		return true;
	}
	
	function githubValidChkFn(github_name) {
		var reg = /^(?=.*[a-zA-Z]).{5,15}$/;
		
		if(!reg.test(github_name)) {
			show_info("깃헙 사용자명이 유효하지 않습니다");
			
			return false;
		}
		
		return true;
	}
	
	duplicateSlackChkFn : function() {
		var slack_name = $('#slack_name').val();
		
		var token=$("input[name='_csrf']").val();
		var header = "X-CSRF-TOKEN";
		    				
		if(!this.slackValidChkFn(slack_name))
			return false;
		
		$.ajax({
			url : "<c:url value='/DupSlackChk.do'/>",
			method : "get",
			data : {"slack_name" : slack_name},
			async : false,
			
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			
			success : function(data) {
				var dupCnt = data;
				
				if(dupCnt > 0) {
					show_info(slack_name + "은/는 사용하실 수 없습니다.");
				}
				else {
					show_info(slack_name + "은/는 사용가능 합니다.");
					$("#slack_name").attr("readonly",true);
				}
				    		
			},
			error : function(request, status, error) {
				show_info("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
			
		})
	},
	
	duplicateGithubChkFn : function() {
		var github_name = $('#github_name').val();
		
		var token=$("input[name='_csrf']").val();
		var header = "X-CSRF-TOKEN";
		    				
		if(!this.githubValidChkFn(github_name))
			return false;
		
		$.ajax({
			url : "<c:url value='/DupGithubChk.do'/>",
			method : "get",
			data : {"github_name" : github_name},
			async : false,
			
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			
			success : function(data) {
				var dupCnt = data;
				
				if(dupCnt > 0) {
					
					show_info(info);
				}
				else {
					
					show_info(info);
					$("#github_name").attr("readonly",true);
				}
				    		
			},
			error : function(request, status, error) {
				show_info("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
			
		})
	},  */
	
	/* alert(github_name + "은/는 사용하실 수 없습니다."); */
	/* alert(github_name + "은/는 사용가능 합니다."); */
	
	$(document).ready(function(){
		$('#slack_table').on("click", ".delete_slack", function() {		
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var tr = $(this).parent().parent();
			var slack_name = tr.children().eq(0).text();
			
			alert(slack_name);
						
			$.ajax({
				url : "<c:url value='/Admin/Slack_Delete.do'/>",
				method : "get",
				data : {"slack_name" : slack_name},
				async : false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},				
				success : function(data) {
					alert("Success");
					slack_delete_row(tr);
				},
				error : function(request, status, error) {
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
				
			});

			
		});
	}); 
	
	$(document).ready(function(){
		$('#append_slack_row').on("click", function() {		
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var slack_name = $('#slack_info').val();
			
			alert(slack_name);
						
			$.ajax({
				url : "<c:url value='/Admin/Slack_Append.do'/>",
				method : "get",
				data : {"slack_name" : slack_name},
				async : false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},				
				success : function(data) {
					if(data == true) {
						alert("Success");
						slack_append_row();
					} else {
						alert("데이터가 존재합니다.");
					}					
				},
				error : function(request, status, error) {
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
				
			});

			
		});
	});
	
	$(document).ready(function(){
		$('#github_table').on("click", ".delete_github", function() {		
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var tr = $(this).parent().parent();
			var github_name = tr.children().eq(0).text();
			
			alert(github_name);
						
			$.ajax({
				url : "<c:url value='/Admin/Github_Delete.do'/>",
				method : "get",
				data : {"github_name" : github_name},
				async : false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},				
				success : function(data) {
					alert("Success");
					github_delete_row(tr);
				},
				error : function(request, status, error) {
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
				
			});

			
		});
	});
	
	$(document).ready(function(){
		$('#append_github_row').on("click", function() {		
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			var github_name = $('#github_info').val();
			
			alert(github_name);
						
			$.ajax({
				url : "<c:url value='/Admin/Github_Append.do'/>",
				method : "get",
				data : {"github_name" : github_name},
				async : false,
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},				
				success : function(data) {
					if(data == true) {
						alert("Success");
						github_append_row();
					} else {
						alert("데이터가 존재합니다.");
					}
				},
				error : function(request, status, error) {
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
				
			});

			
		});
	});
	
	function slack_append_row() {
		$('#slack_table').append(
			$('<tr>').append(
				$('<td>').append($('#slack_info').val()),
				$('<td>').append($('<a>').addClass('delete_slack').append('Delete'))
			)
			
		);
		
	}
	
	function slack_delete_row(obj) {
		obj.remove();
	}
	
	
	
	function github_append_row() {
		$('#github_table').append(
			$('<tr>').append(
				$('<td>').append($('#github_info').val()),
				$('<td>').append($('<a>').addClass('delete_github').append('Delete'))
			)
			
		);
		
	}
	
	function github_delete_row(obj) {
		obj.remove();
	}
		
	
	</script>
</head>

<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar">
			<div class="sidebar-content js-simplebar">
				<a class="sidebar-brand" href="<c:url value='/Admin/Management.do'/> ">
          <span class="align-middle">PSG Admin</span>
        </a>

				<ul class="sidebar-nav">
					<li class="sidebar-header">
						Pages
					</li>

					<li class="sidebar-item">
						<a class="sidebar-link" href="<c:url value='/Admin/Management.do' />">
              <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Dashboard</span>
            </a>
					</li>
					
					<li class="sidebar-item active">
						<a class="sidebar-link" href="<c:url value='/Admin/Settings.do' />">
              <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Settings</span>
            </a>
					</li>
					
					<li class="sidebar-item">
						<a class="sidebar-link" href="<c:url value='/Admin/Role.do' /> ">
              <i class="align-middle" data-feather="book"></i> <span class="align-middle">Role</span>
            </a>
					</li>
					
					<li class="sidebar-item">
						<a class="sidebar-link" href="<c:url value='/Admin/Rest.do' /> ">
              <i class="align-middle" data-feather="book"></i> <span class="align-middle">Rest</span>
            </a>
					</li>
			</div>
		</nav>

		<div class="main">
			<nav class="navbar navbar-expand navbar-light navbar-bg">
				<a class="sidebar-toggle d-flex">
          <i class="hamburger align-self-center"></i>
        </a>

				<form class="d-none d-sm-inline-block">
					<div class="input-group input-group-navbar">
						<input type="text" class="form-control" placeholder="Search…" aria-label="Search">
						<button class="btn" type="button">
              <i class="align-middle" data-feather="search"></i>
            </button>
					</div>
				</form>

				<div class="navbar-collapse collapse">
					<ul class="navbar-nav navbar-align">
						<li class="nav-item dropdown">
							<a class="nav-icon dropdown-toggle" href="#" id="alertsDropdown" data-toggle="dropdown">
								<div class="position-relative">
									<i class="align-middle" data-feather="bell"></i>
									<span class="indicator">4</span>
								</div>
							</a>
							<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right py-0" aria-labelledby="alertsDropdown">
								<div class="dropdown-menu-header">
									4 New Notifications
								</div>
								<div class="list-group">
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-danger" data-feather="alert-circle"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">Update completed</div>
												<div class="text-muted small mt-1">Restart server 12 to complete the update.</div>
												<div class="text-muted small mt-1">30m ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-warning" data-feather="bell"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">Lorem ipsum</div>
												<div class="text-muted small mt-1">Aliquam ex eros, imperdiet vulputate hendrerit et.</div>
												<div class="text-muted small mt-1">2h ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-primary" data-feather="home"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">Login from 192.186.1.8</div>
												<div class="text-muted small mt-1">5h ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<i class="text-success" data-feather="user-plus"></i>
											</div>
											<div class="col-10">
												<div class="text-dark">New connection</div>
												<div class="text-muted small mt-1">Christina accepted your request.</div>
												<div class="text-muted small mt-1">14h ago</div>
											</div>
										</div>
									</a>
								</div>
								<div class="dropdown-menu-footer">
									<a href="#" class="text-muted">Show all notifications</a>
								</div>
							</div>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-icon dropdown-toggle" href="#" id="messagesDropdown" data-toggle="dropdown">
								<div class="position-relative">
									<i class="align-middle" data-feather="message-square"></i>
								</div>
							</a>
							<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right py-0" aria-labelledby="messagesDropdown">
								<div class="dropdown-menu-header">
									<div class="position-relative">
										4 New Messages
									</div>
								</div>
								<div class="list-group">
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<img src="img/avatars/avatar-5.jpg" class="avatar img-fluid rounded-circle" alt="Vanessa Tucker">
											</div>
											<div class="col-10 pl-2">
												<div class="text-dark">Vanessa Tucker</div>
												<div class="text-muted small mt-1">Nam pretium turpis et arcu. Duis arcu tortor.</div>
												<div class="text-muted small mt-1">15m ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<img src="img/avatars/avatar-2.jpg" class="avatar img-fluid rounded-circle" alt="William Harris">
											</div>
											<div class="col-10 pl-2">
												<div class="text-dark">William Harris</div>
												<div class="text-muted small mt-1">Curabitur ligula sapien euismod vitae.</div>
												<div class="text-muted small mt-1">2h ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<img src="img/avatars/avatar-4.jpg" class="avatar img-fluid rounded-circle" alt="Christina Mason">
											</div>
											<div class="col-10 pl-2">
												<div class="text-dark">Christina Mason</div>
												<div class="text-muted small mt-1">Pellentesque auctor neque nec urna.</div>
												<div class="text-muted small mt-1">4h ago</div>
											</div>
										</div>
									</a>
									<a href="#" class="list-group-item">
										<div class="row g-0 align-items-center">
											<div class="col-2">
												<img src="img/avatars/avatar-3.jpg" class="avatar img-fluid rounded-circle" alt="Sharon Lessman">
											</div>
											<div class="col-10 pl-2">
												<div class="text-dark">Sharon Lessman</div>
												<div class="text-muted small mt-1">Aenean tellus metus, bibendum sed, posuere ac, mattis non.</div>
												<div class="text-muted small mt-1">5h ago</div>
											</div>
										</div>
									</a>
								</div>
								<div class="dropdown-menu-footer">
									<a href="#" class="text-muted">Show all messages</a>
								</div>
							</div>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-icon dropdown-toggle d-inline-block d-sm-none" href="#" data-toggle="dropdown">
                <i class="align-middle" data-feather="settings"></i>
              </a>                          
							<a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#" data-toggle="dropdown">
                				<sec:authorize access="isAuthenticated()">
            						<sec:authentication property="principal" var="user" />            
                						<img src="<c:url value='/images/rabbit.png'/> " class="avatar img-fluid rounded mr-1" alt="Charles Hall" /> <span class="text-dark">${user.username}</span>
                				</sec:authorize>
              				</a>
							<div class="dropdown-menu dropdown-menu-right">
								<a class="dropdown-item" href="pages-profile.html"><i class="align-middle mr-1" data-feather="user"></i> Profile</a>
								<a class="dropdown-item" href="#"><i class="align-middle mr-1" data-feather="pie-chart"></i> Analytics</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="pages-settings.html"><i class="align-middle mr-1" data-feather="settings"></i> Settings & Privacy</a>
								<a class="dropdown-item" href="#"><i class="align-middle mr-1" data-feather="help-circle"></i> Help Center</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#">Log out</a>
							</div>
						</li>
					</ul>
				</div>
			</nav>

			<main class="content">
				<div class="container-fluid p-0">

					<h1 class="h3 mb-3">Settings</h1>

					<div class="row">
						<div class="col-md-3 col-xl-2">

							<div class="card">
								<div class="card-header">
									<h5 class="card-title mb-0">Profile Settings</h5>
								</div>

								<div class="list-group list-group-flush" role="tablist">
									<a class="list-group-item list-group-item-action active" data-toggle="list" href="#account" role="tab">
          								Account
        							</a>
									<a class="list-group-item list-group-item-action" data-toggle="list" href="#password" role="tab">
          								Password
        							</a>
									<a class="list-group-item list-group-item-action" data-toggle="list" href="#slack" role="tab">
          								Slack Settings
        							</a>
									<a class="list-group-item list-group-item-action" data-toggle="list" href="#github" role="tab">
          								Github Settings
       			 					</a>
								</div>
							</div>
						</div>

						<div class="col-md-9 col-xl-10">
							<div class="tab-content">
								<div class="tab-pane fade show active" id="account" role="tabpanel">

									<div class="card">
										<div class="card-header">

											<h5 class="card-title mb-0">PSG Info</h5>
										</div>
										<div class="card-body">
											<form>
												<div class="row">
													<div class="col-md-8">
														<div class="mb-3">
															<label class="form-label" for="inputUsername">Username: </label>
															<sec:authorize access="isAuthenticated()">
            													<sec:authentication property="principal" var="user" />            
                													<label class="form-label" for="inputUsername">${user.username}</label>
                											</sec:authorize>
																														
															<input type="text" class="form-control" id="inputUsername" placeholder="Username">
														</div>													
													</div>							
												</div>

												<button type="submit" class="btn btn-primary">Save changes</button>
											</form>

										</div>
									</div>

									<div class="card">
										
									</div>

								</div>
								<div class="tab-pane fade" id="password" role="tabpanel">
									<div class="card">
										<div class="card-body">
											<h5 class="card-title">Password</h5>

											<form>
												<div class="mb-3">
													<label class="form-label" for="inputPasswordCurrent">Current password</label>
													<input type="password" class="form-control" id="inputPasswordCurrent">
													<small><a href="#">Forgot your password?</a></small>
												</div>
												<div class="mb-3">
													<label class="form-label" for="inputPasswordNew">New password</label>
													<input type="password" class="form-control" id="inputPasswordNew">
												</div>
												<div class="mb-3">
													<label class="form-label" for="inputPasswordNew2">Verify password</label>
													<input type="password" class="form-control" id="inputPasswordNew2">
												</div>
												<button type="submit" class="btn btn-primary">Save changes</button>
											</form>

										</div>
									</div>
								</div>
								
								<div class="tab-pane fade" id="slack" role="tabpanel">
									<div class="card">
										<div class="card-header">

											<h5 class="card-title mb-0">Slack</h5>
										</div>
										<div class="card-body">
											<table class="table table-hover my-0" id="slack_table">
									<thead>
										<tr>
											<th>Slack Username</th>											
											<th>Settings</th>
										</tr>
									</thead>
									<c:forEach items="${slack_info}" var="slack">
									<c:choose>
									<c:when test="${not empty slack}">
									<tbody>
										<tr>
											<td class="d-none d-xl-table-cell">${slack.slack_name}</td>
											<td>
												<a class="delete_slack">Delete</a>
											</td>
										</tr>										
									</tbody>
									</c:when>
									</c:choose>									
									</c:forEach>
								</table>
								
								

										</div>
										
										<div class="card-body">
											<table class="table table-hover my-0" id="slack_append_table">
									<thead>
										<tr>
											<th>Slack Info 추가</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="text" class="form-control" id="slack_info" placeholder="Slack Username"></td>
											<td><button type="button" class="btn btn-primary" id="append_slack_row">추가</button>
										</tr>										
									</tbody>
								</table>
										</div>
									</div>
								</div>
								
								<div class="tab-pane fade" id="github" role="tabpanel">
									<div class="card">
										<div class="card-header">

											<h5 class="card-title mb-0">Github</h5>
										</div>
										<div class="card-body">
											<table class="table table-hover my-0" id="github_table">
									<thead>
										<tr>
											<th>Github Username</th>											
											<th>Settings</th>
										</tr>
									</thead>
									<c:forEach items="${github_info}" var="github">
									<c:choose>
									<c:when test="${not empty github}">
									<tbody>
										<tr>
											<td class="d-none d-xl-table-cell">${github.github_name}</td>
											<td>
												<a class="delete_github">Delete</a>
											</td>
										</tr>										
									</tbody>
									</c:when>
									</c:choose>									
									</c:forEach>
								</table>

										</div>
										
										<div class="card-body">
										
								
								<table class="table table-hover my-0" id="github_append_table">
									<thead>
										<tr>
											<th>Github Info 추가</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="text" class="form-control" id="github_info" placeholder="Github Username"></td>
											<td><button type="button" class="btn btn-primary" id="append_github_row">추가</button></td>
										</tr>										
									</tbody>
								</table>
										
										
										</div>
										
										
									</div>
								</div>
								
							</div>
						</div>
					</div>

				</div>
			</main>

			<footer class="footer">
				<div class="container-fluid">
					<div class="row text-muted">
						<div class="col-6 text-left">
							<p class="mb-0">
								<a href="<c:url value='/Admin/Management.do' /> " class="text-muted"><strong>PSG Admin Page</strong></a>
							</p>
						</div>
						<div class="col-6 text-right">
							<ul class="list-inline">
								<li class="list-inline-item">
									<a class="text-muted" href="#">Support</a>
								</li>
								<li class="list-inline-item">
									<a class="text-muted" href="#">Help Center</a>
								</li>
								<li class="list-inline-item">
									<a class="text-muted" href="#">Privacy</a>
								</li>
								<li class="list-inline-item">
									<a class="text-muted" href="#">Terms</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>

	<script src="<c:url value='/js/app.js'/> "></script>

</body>

</html>
