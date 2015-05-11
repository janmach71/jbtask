$(document).ready(function () {
    $(document).ajaxStart(function () {
        //console.log("add");
        $(".ajax-loader").fadeIn("slow")
        $(".ajax-loader-background").fadeIn("0")
        $(".ajax-loader-foreground").fadeIn("0")
    });
    $(document).ajaxStop(function () {
        //console.log("remove");
        $(".ajax-loader").fadeOut("slow")
        $(".ajax-loader-background").fadeOut("50")
        $(".ajax-loader-foreground").fadeOut("50")
    });
});
