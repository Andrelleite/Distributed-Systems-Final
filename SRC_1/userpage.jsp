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

<script type="text/javascript">
    
        var webSocket = new WebSocket("ws://192.168.1.12:8080/Hey/ws");
        //var echoText = document.getElementById("echoText");
    	//var message = document.getElementById("message");

        webSocket.onopen = function(message){ wsOpen(message);};
        webSocket.onmessage = function(message){ wsGetMessage(message);};
        webSocket.onclose = function(message){ wsClose(message);};
        webSocket.onerror = function(message){ wsError(message);};
        
        function wsOpen(){
            console.log("Conected");
            var permit="${session.username}"; 
            webSocket.send(permit);
        }
        
        function wsSendMessage(){
            webSocket.send(message.value);
            echoText.value += "Message sended to the server : " + message.value + "\n";
            message.value = "";
        }
        function wsCloseConnection(){
            webSocket.close();
        }
        function wsGetMessage(message){
        	Popup(message);
        	console.log(message);
        }
        function wsClose(message){
        	var permit="KILL|${session.username}"; 
            webSocket.send(permit);
        }
 
        function wsError(message){
            echoText.value += "Error ... \n";
        }
        
        function Popup(message) {
        	var x = document.getElementById("snackbar");
        	x.className = "show";
        	x.innerHTML = message.data;
        	setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
        } 
    
        
    </script>

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
   <a class="navbar-brand" href="userpage.jsp">UCBusca</a>
  <div class="collapse navbar-collapse" id="navbarSupportedContent-555">
    <ul class="navbar-nav mr-auto">
    
    <c:choose>
		<c:when test="${heyBean.allAdmin==true}">
			 <li class="nav-item">
        <a class="nav-link" href="showlistusers.jsp">User list</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="givepermissions.jsp">Give Permission</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="adminpage.jsp">Admin Page</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="urlindex.jsp">Index URL</a>
		</c:when>
		<c:otherwise>
			
		</c:otherwise>
	</c:choose>
      <li class="nav-item">
        <a class="nav-link" href="history.jsp">My history</a>
      </li>
	  <li class="nav-item">
        <a class="nav-link" href="searchkeyword.jsp">Search by Keyword</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="searchbyurl.jsp">Search by URL</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">LOGOUT</a>
      </li>
      
   </ul>
  </div>
    <span class="navbar-text white-text">
      <c:choose>
		<c:when test="${session.loggedin == true}">
			<c:out value = "Welcome ${session.username} !"/>
		</c:when>
		<c:otherwise>
			<c:out value = "Anonimous" />
		</c:otherwise>
	</c:choose>
    </span>
</nav>

	
    <div id="snackbar"></div>
 
		
</body>
</html>