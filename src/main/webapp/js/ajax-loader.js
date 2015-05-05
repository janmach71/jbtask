$(document).ready(function () {
    $(document).ajaxStart(function () {
        //console.log("add");
        $(".ajax-loader").fadeIn("slow")
        $(".ajax-loader-background").fadeIn("fast")
        $(".ajax-loader-foreground").fadeIn("fast")
    });
    $(document).ajaxStop(function () {
        //console.log("remove");
        $(".ajax-loader").fadeOut("slow")
        $(".ajax-loader-background").fadeOut("100")
        $(".ajax-loader-foreground").fadeOut("100")
    });
});
