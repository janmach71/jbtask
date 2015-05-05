<%@ page import = "main.java.filemanager.*" %>
<%
    if (request.getParameter("dir") == null) {
        out.println("Please enter dir name.");
    } else {
        try {
            FileManager.getDirJson(out,request.getParameter("dir"));
        }
        catch (Exception e)
        {
            out.println(e.getMessage());
        }
    }
%>