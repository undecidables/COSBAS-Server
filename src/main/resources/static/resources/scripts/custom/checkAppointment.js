$(document).ready(function() {
      if(document.title == "Check Appointment Status"){
          $("#appointmentID").val(getUrlParameter('ID'));
        }

      $('#checkStatus').click(function(e) {
        e.preventDefault();

        //Check for errors
        $noError = true;
        $errorNum = 0;

        if( checkValidAppointmentID() && checkValidUserChecker())
        {
            $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Checking Appointment Status</span> Please Wait...</h6>");
            checkAppointment();
        }
        });
});


function checkValidAppointmentID()
{
     //add errorID
    if($("#appointmentID").val() != "")
    {
      $('#errorID').remove();
    }
    else
    {
      $errorNum++;
      if($errorNum == 1){
        $('.featherlight').click();
      }
      $noError = false;
      $element = $('<p class="error" id="errorID">A valid appointment ID has to be entered. </p>');
      $.featherlight($element);
    }
}
function checkValidUserChecker()
{
     //add status error
    if($("#requestedBy").val() != "")
    {
      $('#statusError').remove();
    }
    else
    {
      $errorNum++;
      if($errorNum == 1){
        $('.featherlight').click();
      }
      $noError = false;
      $element = $('<p class="error" id="statusError">You must enter who it is that wants to check the appointment status. </p>');
      $.featherlight($element);
    }
}

function checkAppointment()
{
     //send data if no errors
    if($noError == true)
    {
      $.ajax({
        type: "post",
        data: {"requester" : $('#requestedBy').val(),
               "appointmentID" : $('#appointmentID').val()},
        url: "/status"
      }).then(function(jsonReturned) {
        var obj =  $("<p>"+jsonReturned+"</p>");
        obj.html(obj.html().replace(/\n/g,'<br/>'));

        $('.featherlight').click();
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>"+obj.html()+"</span></h6>");

        $("#appointmentID").val("");
        $("#requestedBy").val("");
      });
      window.scrollTo(0, 0);
    } else {
      $("#signIn").text("Check Appointment Status");
      window.scrollTo(0, 0);
    }
}

function getUrlParameter(parameter)
{
    $pageURL = decodeURIComponent(window.location.search.substring(1));
    $variables = $pageURL.split('&');

    for ($i = 0; $i < $variables.length; $i++) {
        $parameterName = $variables[$i].split('=');

        if ($parameterName[0] === parameter) {
            return $parameterName[1];
        }
    }
    return "";
}