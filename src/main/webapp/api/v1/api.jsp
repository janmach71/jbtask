<%@ page import = "main.java.filemanager.*" %>
<%
    if (request.getParameter("dir") == null) {
        out.println("Please enter dir name.");
    } else {
        out.println(FileManager.getDirJson(request. getParameter("dir")));
    }
%>