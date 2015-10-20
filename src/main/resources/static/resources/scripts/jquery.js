$(document).ready(function() {

  /***************************************************************************/

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