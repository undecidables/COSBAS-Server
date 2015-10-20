$(document).ready(function() {
    if(document.title == "Cancel Appointment"){
          $("#appointmentID").val(getUrlParameter('ID'));
      }

      $('#cancelAppointment').click(function(e) {
        e.preventDefault();

        //Check for errors
       cancelAppointment();
       });
});



function cancelAppointment()
{
     //send data if no errors
    if(checkValidAppointmentID() && checkValidAppointmentCancellee())
    {
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Cancelling Appointment</span> Please Wait...</h6>");
      $.ajax({
        type: "post",
        data: {"cancellee" : $('#cancelBy').val(),
               "appointmentID" : $('#appointmentID').val()},
        url: "/cancelAppointment"
      }).then(function(jsonReturned) {
        $('.featherlight').click();
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><p>"+jsonReturned+"</p>!</h6>");
        $("#appointmentID").val("");
        $("#cancelBy").val("");
      });
      window.scrollTo(0, 0);
    } else {
      $("#signIn").text("Cancel an appointment");
      window.scrollTo(0, 0);
    }
}

function checkValidAppointmentCancellee()
{
     //add cancellee error
    if($("#cancelBy").val() != "")
    {
      return true;
    }
    else
    {
      $element = $('<p class="error" id="cancelError">You must enter who it is that wants to cancel the appointment. </p>');
      $.featherlight($element);
      return false;
    }
}

function checkValidAppointmentID()
{
    $noError = true;
    $errorNum = 0;

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
    return $noError;
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