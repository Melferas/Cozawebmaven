<%@page import="connector.RegisterDao"%>  
<jsp:useBean id="obj" class="connector.LoginBean"/>  

<jsp:setProperty property="*" name="obj"/>  

<%
    boolean status = RegisterDao.validate(obj);

    if (status) {
        String s = "Has sido registrado";
        session.setAttribute("session", "TRUE");
        request.setAttribute("login_msg", s);
    } else {
        out.print("Hubo un error de registro");
        System.out.print("Hubo un error de registro");
%>  
<jsp:include page="index.jsp"></jsp:include>  
<%
    }
%>  
<jsp:forward page="siteView.jsp" /> 