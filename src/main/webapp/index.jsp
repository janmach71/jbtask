<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Manager</title>
        <script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="/js/ajax-loader.js"></script>
        <link rel="stylesheet" href="/styles/ajax-loader.css">
        <script src="/js/file-manager.js"></script>
        <link rel="stylesheet" href="/styles/file-manager.css">

        <script type="text/javascript">
        </script>
        <style>
        </style>

    </head>
    <body>
        <h1>File Manager</h1>
        <!-- <p>The context path is: ${pageContext.request.contextPath}.</p> -->
        <a onclick="tf('/','X2F')"><div id="ima_X2F_ge"><img src="/img/folder.png"/>&nbsp;/</div></a>
        <div id="X2F"></div>
        <div class="ajax-loader"></div>
    </body>
</html>