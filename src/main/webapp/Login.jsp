<%@ include file="index.jsp" %>  
<head>
    <link rel="stylesheet" href="resources/css/controlstyle.css" type="text/css">
</head>
<body>


    <%    if (session.getAttribute("session") == null || session.getAttribute("session").equals("")) {

        } else {
            String redirectURL = "siteView.jsp";
            response.sendRedirect(redirectURL);
        }
    %>  
    <br/>  

    <div class="supercontainer">
            <h3>Entrar</h3>  
        <form action="LoginControl.jsp" method="post">  
            Email:<input class ="controlButton4" type="text" name="email"/><br/><br/>  
            Passw:<input class="controlButton4" type="password" name="pass"/><br/><br/>  
            <input type="submit" class="controlButton4" value="AUTENTICARSE"/>
        </form>  
            <a id="RegisterLink" href="Register.jsp">Registrarse</a>
    </div>>
</body>