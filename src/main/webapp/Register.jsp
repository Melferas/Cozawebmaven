<%@ include file="index.jsp" %>  
<head>
    <link rel="stylesheet" href="resources/css/controlstyle.css" type="text/css">
    <script type="text/javascript" src="resources/scripts/jquery-3.3.1.min.js"></script>
    <script>
        $(document).ready(function(){
        $('#regform').submit(function (ev) {

        if ($("input[name=pass]").val().length === 0 || $("input[name=passfirm]").val().length === 0 || $("input[name=email]").val().length === 0)
        {
        ev.preventDefault(); // to stop the form from submitting
                alert("Complete todos los campos");
        } else if ($("input[name=pass]").val() !== $("input[name=passfirm]").val())
        {
        ev.preventDefault(); // to stop the form from submitting
                alert("Las password no son iguales");
        } else
        {
        $(this).submit();
        }

        });
        }
        );
    </script>
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
        <h3>Registrarse</h3>  
        <form action="RegControl.jsp" id="regform" method="post">  
            Email:<input class ="controlButton4" type="text" name="email"/><br/><br/>  
            Passw:<input class ="controlButton4" type="password" name="pass"/><br/><br/> 
            Confirma:<input class="controlButton4" type="password" name="passfirm"/><br/><br/>  
            <input type="submit" class="controlButton4" value="Registrarse"/>
        </form>  
    </div>>
</body>