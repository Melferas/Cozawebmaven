<%-- 
    Document   : Logout
    Created on : 11-Feb-2018, 18:02:52
    Author     : Melferas
--%>

<%
    
  if (session.getAttribute("session") == null || session.getAttribute("session").equals("")) {
                String redirectURL = "Login.jsp";
                response.sendRedirect(redirectURL);
            } else {
      
      session.invalidate();
                String redirectURL = "Login.jsp";
                response.sendRedirect(redirectURL);
            }
%>