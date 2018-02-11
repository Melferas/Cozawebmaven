<%@page import="connector.LoginDao"%>  
<jsp:useBean id="obj" class="connector.LoginBean"/>  

<jsp:setProperty property="*" name="obj"/>  

<%
    boolean status = LoginDao.validate(obj);
    if (status) {
        String s = "You are successfully logged in";
        session.setAttribute("session", "TRUE");
        request.setAttribute("login_msg", s);
    } else {
        out.print("Sorry, email or password error");
%>  
<jsp:include page="index.jsp"></jsp:include>  
<%
    }
%>  
<jsp:forward page="siteView.jsp" /> 