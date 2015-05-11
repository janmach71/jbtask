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
            if (e.getMessage() == null) {
            //todo: format into JSON
                out.println("Unhandled exception: " + e.getClass().toString());
            } else {
                out.println(e.getMessage());
            }
        }
    }
%>