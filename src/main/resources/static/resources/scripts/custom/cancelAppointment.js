$(document).ready(function() {

    /**
    * Checks for the appointment ID in the URL, if present it fills it into the appointment ID input box
    */
    if(document.title == "Cancel Appointment"){
          $("#appointmentID").val(getUrlParameter('ID'));
      }

    /**
      * Called when cancelAppointment is clicked
      * It calls cancelAppointment
      */
    $('#cancelAppointment').click(function(e) {
      e.preventDefault();

        //Check for errors
      cancelAppointment();
    });
});


/**
* Calls checkValidAppointmentID and checkValidAppointmentCancellee
* Checks that no errors occured
* Sends data gotten from UI to server via ajax if no errors occured
* Returned data is shown to user via a lightbox
* Either it shows the error that occured or shows the appointemnt data
* Cleans data once data was cleared
*/
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
    }
}

/*
* Checks for a valid user, not empty
* Shows or removes an error via lightbox depending on the validity of the user
*/
function checkValidAppointmentCancellee()
{
     //add cancellee error
    if($("#cancelBy").val() != "")
    {
      return true;
    }
    else
    {
      var html = "<p class='error col-md-8 col-md-offset-2 section-title okay' id=''><i class=\"fa fa-exclamation-circle\"></i> You must enter who it is that wants to check the appointment status</p>";
                              html +=  '<p class="text-left">' +
                                          '<button type="submit" id="" class="btnLightbox btn-common">Okay</button>' +
                                        '</p>';
            $.featherlight(html);
      return false;
    }
}

/*
* Checks for a valid appointmentID, not empty
* Shows or removes an error via lightbox depending on the validity of the appointmentID
* If it is the first error the lightbox stating that it is busy processing the data
*/
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
      var html = "<p class='error col-md-8 col-md-offset-2 section-title okay' id=''><i class=\"fa fa-exclamation-circle\"></i> A valid appointment ID has to be entered.</p>";
                        html +=  '<p class="text-left">' +
                                    '<button type="submit" id="" class="btnLightbox btn-common">Okay</button>' +
                                  '</p>';
            $.featherlight(html);
    }
    return $noError;
}

/**
  * function used to read and return the appointment out of the URL if it is present
  * If the appointment ID is not present in the URL it returns an empty string
  * The parameter must be named ID
  * URL example: serverURL/PageURL?ID=theAppoitmentID
  */
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