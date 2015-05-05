$(document).ready(function () {
    loadStoredFolders("/","X2F");
});

var loadFolder = function(path,element_id) {
        var url = "/api/v1/api.jsp?dir=" + encodeURIComponent(path);
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
    return this.setItem(key, JSON.stringify(obj));
}
Storage.prototype.getObj = function(key) {
    return JSON.parse(this.getItem(key));
}

Storage.prototype.initStoredFolders = function() {
    if (! this.getObj("openedFolders") ) {
        this.setObj("openedFolders", {});
    }
    if (! this.getObj("orderOfOpenedFolders") ) {
        this.setObj("orderOfOpenedFolders", []);
    }
}

Storage.prototype.associateStoredFolders = function(path,element_id) {
    this.initStoredFolders();
    var obj = this.getObj("openedFolders");
    obj[element_id]  = path;
    this.setObj("openedFolders",obj);
}

Storage.prototype.addToOrderOfOpenedFolders = function(element_id) {
    this.initStoredFolders();
    var arr = this.getObj("orderOfOpenedFolders");
    arr.push(element_id);
    this.setObj("orderOfOpenedFolders",arr);
}

Storage.prototype.removeFromOrderOfOpenedFolders = function (element_id) {
    this.initStoredFolders();
    var arr = this.getObj("orderOfOpenedFolders");
    var i = arr.indexOf(element_id);
    if (i > -1 ) {
        arr.splice(i,1);
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
    localStorage.associateStoredFolders(undefined,element_id);
    localStorage.removeFromOrderOfOpenedFolders(element_id);
    html = $("#ima_"+element_id+"_ge")[0].innerHTML;
    html = html.replace("_o.png\"",".png\"");
    $("#ima_"+element_id+"_ge")[0].innerHTML = html;
}

var openFolder = function(path,element_id) {
    localStorage.associateStoredFolders(path,element_id);
    localStorage.removeFromOrderOfOpenedFolders(element_id);
    localStorage.addToOrderOfOpenedFolders(element_id);
    loadFolder(path,element_id);
    html = $("#ima_"+element_id+"_ge")[0].innerHTML;
    html = html.replace(".png\"","_o.png\"");
    $("#ima_"+element_id+"_ge")[0].innerHTML = html;
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
        var element_id = encodeURIComponent(i.n);
        element_id = element_id.split("%").join("X");
        element_id = element_id.split("#").join("X");
        element_id = element_id.split(".").join("X");
        element_id = element_id.split("-").join("X");
        //console.log(element_id);
        switch(i.t) {
        case "image":
            html +="<img src=\"/img/image.png\"/>&nbsp;";
            html +=i.n;
            break;
        case "text":
            html +="<img src=\"/img/text.png\"/>&nbsp;";
            html +=i.n;
            break;
        case "archive":
            html +="<div id=\"ima_"+element_id+"_ge\"><img src=\"/img/archive.png\"/>&nbsp;";
            html +="<a onclick=\"toggleFolder('"+i.n+"','"+element_id+"')\">" + i.n +"</a></div>";
            html +="<div id=\""+element_id+"\"></div>";
            break;
        case "folder":
            html +="<div id=\"ima_"+element_id+"_ge\"><img src=\"/img/folder.png\"/>&nbsp;";
            html +="<a onclick=\"toggleFolder('"+i.n+"','"+element_id+"')\">" + i.n +"</a></div>";
            html +="<div id=\""+element_id+"\"></div>";
            break;
        default:
        case "unknown":
            html +="<img src=\"/img/unknown.png\"/>&nbsp;";
            html +=i.n;
            break;
        }
        html +="</li>";
    }
    html +="</ul>";
    return html;
}
