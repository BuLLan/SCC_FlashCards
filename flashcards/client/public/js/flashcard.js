$(document).ready(function () {
    var f = true;
    // set up hover panels
    // although this can be done without JavaScript, we've attached these events
    // because it causes the hover to be triggered when the element is tapped on a touch device
    $('.front').click(function () {
        if (f == true) {
            $('.hover').addClass('flip');
            $("#button").fadeIn("slow", function () {
            });
            f = false;
        }
        else {
            $('.hover').removeClass('flip');
            f = true;
        }
    });
    $('.btn').click(function () {
        $("#flash").fadeOut("slow", function () {
        });
        $('.hover').removeClass('flip');
        f = true;
        $("#button").fadeOut("slow", function () {
        });
        $("#flash").fadeIn("slow", function () {
        });
    });
});