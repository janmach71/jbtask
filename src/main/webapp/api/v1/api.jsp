<%@ page import = "main.java.filemanager.*" %>
<%
    //todo: refactor to servlet
    if (request.getParameter("dir") == null) {
        out.println("Please enter dir name.");
    } else {
        try {
            FileManager.getDirJson(out,request.getParameter("dir"));
        }
        catch (Exception e)
        {
            if (e.getMessage() == null) {
                out.println("{\"error\" : \"Unhandled java exception: " + e.getClass().toString() + "\"}");
            } else {
                out.println("{\"error\" : \"" + e.getMessage() + "\"}");
            }
        }
    }
%>