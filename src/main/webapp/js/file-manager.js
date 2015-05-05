$(document).ready(function () {
    loadStoredFolders("/","root");
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

var removeFromOrderOfOpenedFolders(element_id) {
    var i = localStorage.orderOfOpenedFolders.indexOf(element_id);
    if (i > -1 ) {
        localStorage.orderOfOpenedFolders=splice(i,1);
    }
}

var closeFolder = function(element_id) {
    $("#"+element_id)[0].innerHTML="";
    initStoredFolders();
    localStorage.openedFolders[element_id]=undefined;
    removeFromOrderOfOpenedFolders(element_id);
}

var initStoredFolders = function() {
    if (typeof (localStorage.openedFolders) === "undefined") {
        localStorage.openedFolders = new Array();
    }
    if (typeof (localStorage.orderOfOpenedFolders) === "undefined") {
        localStorage.orderOfOpenedFolders = new Array();
    }
}

var openFolder = function(path,element_id) {
    initStoredFolders();
    localStorage.openedFolders[element_id]=path;
    removeFromOrderOfOpenedFolders(element_id);
    localStorage.orderOfOpenedFolders.push(element_id);
    loadFolder(path,element_id);
}

var isFolderOpened = function(element_id) {
    initStoredFolders();
    if (typeof (localStorage.openedFolders[element_id]) === "undefined") {
        return false;
    }
    return true;
}

var loadStoredFolders = function(root,element_id) {
    initStoredFolders();
    if ( localStorage.orderOfOpenedFolders.length ) {
        openFolder(root,element_id);
        return;
    }
    for (var i in localStorage.orderOfOpenedFolders) {
        var el_id = localStorage.orderOfOpenedFolders[i];
        var path = localStorage.openedFolders[el_id];
        openFolder(path,el_id);
    }
}

var toggleFolder = function(path,element_id) {
    if ( isFolderOpened(element_id)) {
        closeFolder(element_id);
    } else {
        openFolder(path,element_id);
    }
}

var generateHTML = function(data) {
    //console.log(data);
    var dir = jQuery.parseJSON( data );
    //console.log(dir.dir);
    var i;
    var html = "";
    html +="<ul class='filelist'>";
    for ( index in dir.dir ) {
        html +="<li class='filelistitem'>";
        var i = dir.dir[index].i;
        //console.log(i);
        var element_id = encodeURIComponent(i.n).split("%").join("X").split("-").join("Y").split("+").join("Z");
        console.log(encoded);
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
            html +="<a onclick=\"toggleFolder('"+i.n+"','"+element_id+"')\">" + i.n +"</a>";
            html +="<br>";
            html +="<div id=\""+element_id+"\"></div>";
            break;
        case "folder":
            html +="<img src=\"/img/folder.png\" />&nbsp;";
            html +="<a onclick=\"toggleFolder('"+i.n+"','"+element_id+"')\">" + i.n +"</a>";
            html +="<br>";
            html +="<div id=\""+element_id+"\"></div>";
            break;
        default:
        case "unknown":
            html +="<img src=\"/img/unknown.png\" />&nbsp;";
            html +=i.n;
            html +="<br>";
            break;
        }
        html +="</li>";
    }
    html +="</ul>";
    return html;
}
