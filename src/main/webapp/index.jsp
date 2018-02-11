<head>
       <link rel="stylesheet" href="resources/css/controlstyle.css" type="text/css">
</head>
<a id="Prinlink" href="siteView.jsp">Principal</a>  
<div id="linkpos">
<%
  if (session.getAttribute("session") == null || session.getAttribute("session").equals("")) {
%>
<a id="Loginlink" href="Login.jsp">Autenticarse</a>
<%
            } else {
%>
<a id="Logoutlink" href="Logout.jsp">Cerrar sesión</a>
<%
            }
  %>
</div>
<hr/>  

 
