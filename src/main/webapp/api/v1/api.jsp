<%@ page import = "main.java.filemanager.*" %>
<%
    if (request.getParameter("dir") == null) {
        out.println("Please enter dir name.");
    } else {
        FileManager.getDirJson(out,request.getParameter("dir"));
    }
%>