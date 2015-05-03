<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Manager</title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

        <script type="text/javascript">
        $(document).ready(function () {
            $(document).ajaxStart(function () {
                //console.log("add");
                $(".ajax-loader").fadeIn("slow")
                $(".ajax-loader-background").fadeIn("slow")
                $(".ajax-loader-foreground").fadeIn("slow")
            });
            $(document).ajaxStop(function () {
                //console.log("remove");
                $(".ajax-loader").fadeOut("slow")
                $(".ajax-loader-background").fadeOut("slow")
                $(".ajax-loader-foreground").fadeOut("slow")
            });
        });
        </script>
        <style>
            .ajax-loader {
                display: none;
                position: fixed;
                left: 0px;
                top: 0px;
                width: 100%;
                height: 100%;
                z-index: 9990;
                background-color:gray;
                filter:alpha(opacity=80);
                opacity:.8;
                z-index: 9999;
                background: url(/img/ajax-loader-good.gif) center no-repeat #fff;
            }
        </style>

    </head>
    <body>
        <h1>Hello World!</h1>
        <img src="/img/ajax-loader-good.gif"/>
        <div class="ajax-loader"></div>
    </body>
</html>