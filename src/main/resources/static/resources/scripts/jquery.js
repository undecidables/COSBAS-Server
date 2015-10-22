$(document).ready(function(e) {
    $("body").css("visibility", "visible");
    e.stopImmediatePropagation();
     $('.nav li').removeClass('active');//or $('.active').removeClass('active');
});