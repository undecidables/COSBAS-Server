$(document).ready(function() {

  /**
  * function used to read and return the appointment out of the URL if it is present
  * If the appointment ID is not present in the URL it returns an empty string
  * The parameter must be named ID
  * URL example: serverURL/PageURL?ID=theAppoitmentID
  */

  //Read URL parameter from URL
  function getUrlParameter(parameter) {
    $pageURL = decodeURIComponent(window.location.search.substring(1));
    $variables = $pageURL.split('&');

    for ($i = 0; $i < $variables.length; $i++) {
        $parameterName = $variables[$i].split('=');

        if ($parameterName[0] === parameter) {
            return $parameterName[1];
        } 
    }

    return "";
  };


});