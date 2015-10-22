$(document).ready(function(e) {
       $("body").css("visibility", "visible");


       $('.dropdown').on('shown.bs.dropdown', function () {
         $('.active').removeClass('active');
       })



   });