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
   <a class="navbar-brand" href="userpage.jsp">UCBusca</a>
   
   <form class="form-inline my-2 my-lg-0" action="searchurl" method="post">
      <input class="form-control mr-sm-2" type="search" placeholder="URL for search" aria-label="Search"  name="url">
      <input type="submit" value="Search" class="btn btn-outline-success my-2 my-sm-0">
    </form>
   
   
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