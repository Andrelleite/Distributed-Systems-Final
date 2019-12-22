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
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<title>UCBusca</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
    <a class="navbar-brand" href="#">UCBusca</a>
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
   	  
   	  <li class="nav-item">
        <a class="nav-link" href="login.jsp">Login</a>
      </li>
      

      <li class="nav-item">
        <a class="nav-link" href="register.jsp">Register</a>
      </li>
      
    </ul>
    
    
    
    <form class="form-inline my-2 my-lg-0" class = "meio" action="searchguest" method="post">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Index" name="search">
      <input type="submit" value="Search" class="btn btn-outline-success my-2 my-sm-0">
    </form>
    
  </div>
	</nav>

	<div>
	    <p class="head">Welcome to best browser in this computer.</p>
	
	</div>

	<div class="divonindexpage">
	<c:forEach items = "${heyBean.allTitles}" var="value" varStatus="i">
		<p/>
		<c:set var="j" value="${i.index}" />
		<a href="${heyBean.allUrl[j]}" class = "pagetitle"><c:out value = "${heyBean.allTitle[j]}" /><br></a>
		<a href="${heyBean.allUrl[j]}" class = "urlink" ><c:out value = "${heyBean.allUrl[j]}" /><br></a>
		<c:out value = " ${heyBean.allBody[j]}..." />
	</c:forEach>
   </div>

	
	
</body>
</html>