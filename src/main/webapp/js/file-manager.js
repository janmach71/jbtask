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

Storage.prototype.setObj = function(key, obj) {
    return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function(key) {
    return JSON.parse(this.getItem(key))
}

Storage.prototype.initStoredFolders = function() {
    if (typeof this.getObj("openedFolders") === "undefined") {
        this.setObj("openedFolders", new Array());
    }
    if (typeof this.getObj("orderOfOpenedFolders") === "undefined") {
        this.setObj("orderOfOpenedFolders", new Array());
    }
}

Storage.prototype.associateStoredFolders = function(path,element_id) {
    this.initStoredFolders();
    var arr = this.getObj("openedFolders");
    arr[element_id]  = path;
    this.setObj("openedFolders",arr);
}

Storage.prototype.addToOrderOfOpenedFolders = function(element_id) {
    this.initStoredFolders();
    var arr = this.getObj("orderOfOpenedFolders");
    arr[element_id]  = path;
    this.setObj("orderOfOpenedFolders",arr);
}

Storage.prototype.removeFromOrderOfOpenedFolders = function (element_id) {
    this.initStoredFolders();
    var arr = this.getObj("orderOfOpenedFolders");
    var i = arr.indexOf(element_id);
    if (i > -1 ) {
        arr=arr.splice(i,1);
    }
    this.setObj("orderOfOpenedFolders",arr);
}

Storage.prototype.getOrderOfOpenedFolders = function() {
    this.initStoredFolders();
    var arr = this.getObj("orderOfOpenedFolders");
    return arr;
}

Storage.prototype.getOpenedFolders = function() {
    this.initStoredFolders();
    var arr = this.getObj("openedFolders");
    return arr;
}



var displayFolderContent = function(data,element_id) {
    html = generateHTML(data);
    $("#"+element_id)[0].innerHTML=html;
}

var closeFolder = function(element_id) {
    $("#"+element_id)[0].innerHTML="";
    localStorage.associateStoredFolders(path,undefined);
    localStorage.removeFromOrderOfOpenedFolders(element_id);
}

var openFolder = function(path,element_id) {
    localStorage.associateStoredFolders(path,element_id);
    localStorage.removeFromOrderOfOpenedFolders(element_id);
    localStorage.addToOrderOfOpenedFolders(element_id);
    loadFolder(path,element_id);
}

var isFolderOpened = function(element_id) {
    var folders=localStorage.getOpenedFolders();
    if (typeof folders[element_id] === "undefined") {
        return false;
    }
    return true;
}

var loadStoredFolders = function(root,element_id) {
    var orders = localStorage.getOrderOfOpenedFolders();
    if ( !orders.length ) {
        openFolder(root,element_id);
        return;
    }
    var folders = localStorage.getOpenedFolders();
    for (var i in orders) {
        var el_id = orders[i];
        var path = folders[el_id];
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
    //var dir = jQuery.parseJSON( data );
    var dir = JSON.parse(data);
    //console.log(dir.dir);
    var i;
    var html = "";
    html +="<ul class='filelist'>";
    for ( index in dir.dir ) {
        html +="<li class='filelistitem'>";
        var i = dir.dir[index].i;
        //console.log(i);
        var element_id = encodeURIComponent(i.n).split("%").join("X").split("-").join("Y").split("+").join("Z");
        //console.log(element_id);
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
