<%@page import="connector.AmazonRaspberry"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%

    AmazonRaspberry arp = new AmazonRaspberry();

    String temp = request.getParameter("temp");
    String light = request.getParameter("light");
    String press = request.getParameter("pres");

    if (temp != null) {
        if (temp.equals("subir")) {
            arp.upTemp();
            out.print(arp.getTemp());
        } else if (!temp.isEmpty() && temp.equals("bajar")) {
            arp.downTemp();
            out.print(arp.getTemp());
        } else {
            out.print(arp.getTemp());
        }
    } else if (light != null) {
        if (light.equals("subir")) {
            arp.upLight();
            out.print(arp.getLightLvl());
        } else if (light != null && light.equals("bajar")) {
            arp.downLight();
            out.print(arp.getLightLvl());
        } else {
            out.print(arp.getLightLvl());
        }
    } else if (press != null) {
        if (press.equals("cambiar")) {
            arp.switchMode();
            out.print(arp.getState());
        } else {
            out.print(arp.getState());
        }
    }

%>

