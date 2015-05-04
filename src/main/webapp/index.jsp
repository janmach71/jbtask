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
        	loadFolder("/","root");
        });
        var loadFolder = function(path,element_id) {
                var url = "http://env-6068157.unicloud.pl/api/v1/api.jsp?dir=" + encodeURIComponent(path);
                $.ajax({
                    url: url,
                    cache: false,
                    contentType: false,
                    processData: false,
                    type: "GET",
                    success: function(data) {
                      displayFolderContent(data,element_id);
                    },
                    error: function () {
                      alert("Error loading folder content..." );
                    }
                });
        }
        var displayFolderContent = function(data,element_id) {
            html = generateHTML(data);
            $("#"+element_id)[0].innerHTML=html;
        }
        var generateHTML = function(data) {
            //console.log(data);
            var dir = jQuery.parseJSON( data );
            //console.log(dir.dir);
            var i;
            var html = "";
            for ( i in dir.dir ) {
                console.log(i.n + " " + i.t);
                switch(i.t) {
                case "image":
                    html +="<img src=\"/img/image.png\" />&nbsp;";
                    html +=i.n;
                    html +="<br>";
                    break;
                case "text":
                    html +="<img src=\"/img/text.png\" />&nbsp;";
                    html +=i.n;
                    html +="<br>";
                    break;
                case "archive":
                    html +="<img src=\"/img/archive.png\" />&nbsp;";
                    html +="<a onclick=\"loadFolder('"+i.n+"')\">" + i.n +"</a>";
                    html +="<br>";
                    html +="<div id=\""+i.n+"\"></div>";
                    break;
                case "folder":
                    html +="<img src=\"/img/folder.png\" />&nbsp;";
                    html +="<a onclick=\"loadFolder('"+i.n+"')\">" + i.n +"</a>";
                    html +="<br>";
                    html +="<div id=\""+i.n+"\"></div>";
                    break;
                case "unknown":
                    html +="<img src=\"/img/unknown.png\" />&nbsp;";
                    html +=i.n;
                    html +="<br>";
                    break;
                }
            }
            return html;
        }
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
        <h1>File Manager</h1>
        <!-- <p>The context path is: ${pageContext.request.contextPath}.</p> -->
        <div id="root"></div>
        <div class="ajax-loader"></div>
    </body>
</html>