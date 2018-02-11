<%-- 
    Document   : tempController
    Created on : 15-ene-2018, 18:38:35
    Author     : Melferas
--%>

<%@page import="com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil.KeyStorePasswordPair"%>
<%@page import="com.amazonaws.services.iot.client.AWSIotMqttClient"%>
<%@page import="com.amazonaws.services.iot.client.AWSIotException"%>
<%@page import="com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil"%>
<%@page import="com.amazonaws.services.iot.client.*"%>
<%@page import="java.io.File"%>


<!DOCTYPE html>
<html>
    <head>
        <title>Control Cozita</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="resources/css/controlstyle.css" type="text/css">
        <script type="text/javascript" src="resources/scripts/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="resources/scripts/controldynamic.js" ></script> 
    </head>
    <body>
        <%@ include file="index.jsp" %>  
        <%            if (session.getAttribute("session") == null || session.getAttribute("session").equals("")) {
                String redirectURL = "Login.jsp";
                response.sendRedirect(redirectURL);
            } else {
                String login_msg = (String) request.getAttribute("login_msg");
                if (login_msg != null) {
                    System.out.print(login_msg);
                }
            }
        %>
        <!-- Control de temperatura -->
        <div class="supercontainer">
            <div class="titulecontainer">
                <h1 class="temptext">Control de temperatura</h1>
            </div>
            <div>
                <p id="currentTemp" class="currentText" contenteditable></p>
            </div>
            <div class="buttoncontainer">
                <button id="controlTempplus" class="controlButton">
                    +
                </button>
                <button id="controlTempless" class="controlButton">
                    -
                </button>
            </div>
        </div>
        <!-- Espacios -->
        <br/>
        <br/>
        <br/>
        <br/>        
        <!-- Control de luz -->
        <div class="supercontainer">
            <div class="titulecontainer">
                <h1 class="lighttext"> Control de luz </h1>   
            </div>
            <div>
                <p id="currentLight" class="currentText" contenteditable></p>
            </div>
            <div class="buttoncontainer">
                <button id="controlLuzplus" class="controlButton">
                    +
                </button>
                <button id="controlLuzless" class="controlButton">
                    -
                </button>
            </div>
        </div>
        <!-- Espacios -->
        <br/>
        <br/>
        <br/>
        <br/>        
        <div class="supercontainer">
            <div class="titulecontainer">
                <h1 class="prestext"> Modo </h1>   
            </div>
            <div>
                <p id="currentMode" class="currentText"></p>
            </div>
            <div class="buttoncontainer">
                <button id="controlPresChange" class="controlButton2">
                    Cambiar
                </button>
            </div>
            <div class="titulecontainer">
                <h1 class="prestext"> Tiempo </h1>   
            </div>
            <div>
                <p id="currentTime" class="currentText" contenteditable></p>
            </div>
            <div class="titulecontainer">
                <h1 class="prestext"> Presencia </h1>   
            </div>
            <div>
                <p id="currentPres" class="currentText" ></p>
            </div>
        </div>
    </body>
</html>
