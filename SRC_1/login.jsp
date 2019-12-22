<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="mainpagestyle.css">
<title>Login</title>
</head>
<body>


<div class="wrapper fadeInDown">
  <div id="formContent">
    <!-- Tabs Titles -->

    <!-- Icon -->
    <div class="fadeIn first">
      <img src="https://upload.wikimedia.org/wikipedia/commons/b/bd/Logo_of_the_University_of_Coimbra%2C_Portugal.png" id="icon" alt="User Icon" />
    </div>

    <!-- Login Form -->
    
    <s:form action="login" method="post">
		<s:text name="Username:" />
		<s:textfield id="login" class="fadeIn second"  name="username" /><br>
		<s:text name="Password:" />
		<s:textfield id="password" class="fadeIn third" name="password" /><br>
		<s:submit class="fadeIn fourth"/>
	</s:form>


  </div>
</div>

</body>
</html>