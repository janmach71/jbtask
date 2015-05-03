<%@ page import = "main.filemanager.FileManager" %>
<%
    if (request.getParameter("dir") == null) {
        out.println("Please enter dir name.");
    } else {
        List<DirItem> items = FileManager.getDir(request. getParameter("dir"));
        if (items == null) {
            out.println("Null <b>"+request. getParameter("dir")+"</b>!");
        } else {
            out.println("Hello <b>"+request. getParameter("dir")+"</b>!");
        }
    }
%>