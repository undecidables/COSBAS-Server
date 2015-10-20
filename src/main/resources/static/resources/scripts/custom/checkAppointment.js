$(document).ready(function() {

      /**
      * Checks for the appointment ID in the URL, if present it fills it into the appointment ID input box
      */
      if(document.title == "Check Appointment Status"){
          $("#appointmentID").val(getUrlParameter('ID'));
        }

      /**
      * Called when checkStatus is clicked
      * It calls checkValidAppointmentID and checkValidUserChecker
      * If no errors detected it calls checkAppointment and returns the appointment details to the user
      * All feedback is shown in a lightbox
      */
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


/**
* Checks if a valid appointmentID was entered if not shows an error else it removes the error
* Checks if it is the first error to be displayed and then closes the light box saying that it is busy processing the data.
* All feedback is given via lightbox
*/
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

/**
* Checks if a valid user was entered if not shows an error else it removes the error
* Checks if it is the first error to be displayed and then closes the light box saying that it is busy processing the data.
* All feedback is given via lightbox
*/
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

/**
* Checks that no errors occured
* Sends data gotten from UI to server via ajax
* Returned data is shown to user via a lightbox
* Either it shows the error that occured or shows the appointemnt data
*/
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
    }
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