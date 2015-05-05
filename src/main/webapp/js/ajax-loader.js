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
