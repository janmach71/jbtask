
var storedFoldersToOpen = [];

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
                var obj = JSON.parse(data);
                if (obj.dir ) {
                    displayFolderContent(obj,element_id);
                } else if ( obj.error ) {
                    alert(obj.error);
                } else {
                    throw "JSON data does contain expected content.";
                }
            },
            error: function () {
              alert("Error loading directory content..." );
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


var displayFolderContent = function(dir,element_id) {
    html = generateHTML(dir);
    $("#"+element_id)[0].innerHTML=html;
    html = $("#ima_"+element_id+"_ge")[0].innerHTML;
    html = html.replace(".png\"","_o.png\"");
    $("#ima_"+element_id+"_ge")[0].innerHTML = html;
    loadStoredFoldersOneByOne();
}

var closeStoredFolder = function(element_id) {
    sessionStorage.associateStoredFolders(undefined,element_id);
    sessionStorage.removeFromOrderOfOpenedFolders(element_id);
    var orders = sessionStorage.getOrderOfOpenedFolders();
    var i = 0;
    while ( i <  orders.length ) {
        var el_id = orders[i];
        if (el_id.indexOf(element_id+"X2F") == 0 ) {
            closeStoredFolder(el_id);
            orders = sessionStorage.getOrderOfOpenedFolders();
        } else {
            i++;
        }
    }
}

var closeFolder = function(element_id) {
    $("#"+element_id)[0].innerHTML="";
    html = $("#ima_"+element_id+"_ge")[0].innerHTML;
    html = html.replace("_o.png\"",".png\"");
    $("#ima_"+element_id+"_ge")[0].innerHTML = html;
    closeStoredFolder(element_id);
}

var openFolder = function(path,element_id) {
    sessionStorage.associateStoredFolders(path,element_id);
    sessionStorage.removeFromOrderOfOpenedFolders(element_id);
    sessionStorage.addToOrderOfOpenedFolders(element_id);
    loadFolder(path,element_id);
}

var isFolderOpened = function(element_id) {
    var folders=sessionStorage.getOpenedFolders();
    if (typeof folders[element_id] === "undefined") {
        return false;
    }
    return true;
}

var loadStoredFolders = function(root,element_id) {
    var orders = sessionStorage.getOrderOfOpenedFolders();
    if ( !orders.length ) {
        openFolder(root,element_id);
        return;
    }
    storedFoldersToOpen = [];
    for (var i in orders) {
        var el_id = orders[i];
        storedFoldersToOpen.push(el_id);
    }
    loadStoredFoldersOneByOne();
}

var loadStoredFoldersOneByOne = function() {
    if (storedFoldersToOpen.length) {
        var folders = sessionStorage.getOpenedFolders();
        var el_id = storedFoldersToOpen[0];
        var path = folders[el_id];
        storedFoldersToOpen.shift();
        openFolder(path,el_id);
    }
}

var tf = function(path,element_id) {
    if ( isFolderOpened(element_id)) {
        closeFolder(element_id);
    } else {
        openFolder(path,element_id);
    }
}

/*
function to generate (inner) html from list of dir items
*/
var generateHTML = function(dir) {
    //console.log(dir.dir);
    var i;
    var html = "";
    html +="<ul class='fl'>";
    for ( index in dir.dir ) {
        html +="<li>";
        var i = dir.dir[index].i;
        var name ;
        if ( i.n=="/") {
            name = i.n;
        } else {
            var splits = i.n.split("/");
            if (splits.length) {
                name = splits[splits.length-1];
            } else {
                name = i.n;
            }
        }
        //console.log(i);
        var element_id = encodeURIComponent(i.n);
        //todo: in some cases this replacement will not be enough
        element_id = element_id.split("%").join("X25");
        element_id = element_id.split("#").join("X23");
        element_id = element_id.split(".").join("X27");
        element_id = element_id.split("-").join("X2D");
        //console.log(element_id);
        switch(i.t) {
        case "image":
            html +="<img src=\"/img/image.png\"/>&nbsp;";
            html +=name;
            break;
        case "text":
            html +="<img src=\"/img/text.png\"/>&nbsp;";
            html +=name;
            break;
        case "archive":
            html +="<a onclick=\"tf('"+i.n+"','"+element_id+"')\">"
            //todo: in some cases this replacement will not be enough
            html +="<div id=\"ima_"+element_id+"_ge\"><img src=\"/img/archive.png\"/>&nbsp;";
            html +=name +"</div></a>";
            html +="<div id=\""+element_id+"\"></div>";
            break;
        case "folder":
            html +="<a onclick=\"tf('"+i.n+"','"+element_id+"')\">"
            //todo: in some cases this replacement will not be enough
            html +="<div id=\"ima_"+element_id+"_ge\"><img src=\"/img/folder.png\"/>&nbsp;";
            html +=name +"</div></a>";
            html +="<div id=\""+element_id+"\"></div>";
            break;
        default:
        case "unknown":
            html +="<img src=\"/img/unknown.png\"/>&nbsp;";
            html +=name;
            break;
        }
        html +="</li>";
    }
    html +="</ul>";
    return html;
}
