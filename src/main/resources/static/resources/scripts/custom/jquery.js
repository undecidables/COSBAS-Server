$(document).ready(function(e) {
       $("body").css("visibility", "visible");


       $('.dropdown').on('shown.bs.dropdown', function () {
         $('.active').removeClass('active');
       })



   });


function spawnErrorMessage(message, jsonReturned)
{

    alert("asd");
    var msg = "<div class=\"alert alert-danger\">"+
                            "<strong><i class=\"fa fa-exclamation-circle\"></i>  INVALID INPUT </strong><br/> " + message + jsonReturned +
                          "</div>";
    msg +=  '<p class="text-left">' +
           '<button type="submit" id="emailErrorOkay" class="btnLightbox btn-common">Okay</button>' +
         '</p>';
    $.featherlight(msg);


}

function getErrorMessage(message, type)
{
    if(type == "")
        type = "INVALID INPUT";
    var html = "<div class=\"alert alert-danger\">"+
                  "<strong><i class=\"fa fa-exclamation-circle\"></i>  " + type + " </strong><br/>" + message +
                "</div>";
    return html;
}

function spawnBusyMessage(message)
{
    var html = "<div class=\"alert alert-info \">"+
                  "<strong><i class=\"fa fa-circle-o-notch fa-spin\"></i>  PLEASE WAIT: </strong><br/> " + message +
                "</div>";
    $.featherlight(html);
}

function spawnSuccessMessage(message)
{
        var html = "<div class=\"alert alert-success \">"+
                      "<strong><i class=\"fa fa-check-circle\"></i>  SUCCESS! </strong><br/> " + message +
                    "</div>";

            html +=  '<p class="text-left">' +
                '<button type="submit" id="emailErrorOkay" class="btnLightbox btn-common">Okay</button>' +
             '</p>';
        $.featherlight(html);
}


function getWarningMessage(message)
{
    var html = "<div class=\"wow fadeIn alert alert-warning\" data-wow-delay=\".2s\"><strong><i class=\"fa fa-exclamation-triangle\"></i>  INFORMATION: </strong><br/> " + message+" </div>";
        return html;
}