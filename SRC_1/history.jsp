<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="indediv.css">

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<title>This is your space.</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
   
	<form  action="returntomain" method="post" >
  		<button  type="submit" class="btn btn-danger">Voltar</button>
	</form>
	
	<div class="centro">
    <form class="form-inline" action="userhistory" method="post" >
  		<div class="form-group mx-sm-3 mb-2" >
    		<label for="inputPassword2" class="sr-only">Password</label>
    		<input type="password" class="form-control" id="inputPassword2" placeholder="Password" name="password">
  		</div>
  		<button type="submit" class="btn btn-primary mb-2">Confirm identity</button>
	</form>
    </div>

    <span class="navbar-text white-text meio">
      <c:choose>
		<c:when test="${session.loggedin == true}">
			<c:out value = "${session.username} " />
		</c:when>
		<c:otherwise>
			<c:out value = "Anonimous" />
		</c:otherwise>
	</c:choose>
    </span>
</nav>

	<c:choose>
		<c:when test="${session.certificate == session.password}">
		<s:form action="userhistory" method="get">
		<s:submit value="Request history"/>
		</s:form>
		<div class="divonindexpage">
		<c:forEach items = "${heyBean.allHistory}" var="value">
		<p/>
		<c:out value = "->  ${value} " /><br>
		</c:forEach>
  		 </div>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>		
		
	
		
</body>
</html>