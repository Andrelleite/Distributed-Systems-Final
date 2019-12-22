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
<link rel="stylesheet" type="text/css" href="style.css">

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
        
        function Popup(text) {
        	var history = document.getElementById('history');
            var line = document.createElement('p');
            line.style.wordWrap = 'break-word';
            line.innerHTML = text.data;
            history.appendChild(line);
            history.scrollTop = history.scrollHeight;
        } 
    
        
    </script>



</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="collapse navbar-collapse" id="navbarSupportedContent-555">

	<form  action="returntomain" method="post" >
  		<button  type="submit" class="btn btn-danger">Voltar</button>
	</form>

	<c:choose>
		<c:when test="${session.certificate == session.password}">
			<i>Welcome to the admin page. This is magical.</i>
		</c:when>
		<c:otherwise>
		<div class="centro">
   			 <form class="form-inline" action="adminpage" method="post" >
  			<div class="form-group mx-sm-3 mb-2" >
    		<label for="inputPassword2" class="sr-only">Password</label>
    		<input type="password" class="form-control" id="inputPassword2" placeholder="Password" name="password">
  			</div>
  			<button type="submit" class="btn btn-primary mb-2">Confirm identity</button>
			</form>
   		 </div>
		</c:otherwise>
	</c:choose>

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
    
    <c:choose>
		<c:when test="${session.certificate == session.password}">
		    <div id="container"><div id="history"></div></div>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>	
    
    <div id="snackbar"></div>
 	
		
</body>
</html>