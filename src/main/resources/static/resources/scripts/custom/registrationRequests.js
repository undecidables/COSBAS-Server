$(document).ready(function() {
	/*
    * Calls the controller function to get all requested registrations for the system
    */
     if(document.title == "Registration Requests"){
     acceptReqistrationListener();
     denyRegistrationListener();
     $("#denyDiv").hide();
     $.ajax({
            type: "post",
            url: "/getRequestedUsersForRegistration"
          }).then(function(jsonReturned) {
          	$.ajax({
            type: "post",
            data: {"authorized": "REGISTRATION_APPROVE"},
            url: "/authorizedToAccessRequests"
          	}).then(function(approvalReturned) {
          		if(approvalReturned == "authorized")
          		{
            		$("#requests").empty();
            		$("#requests").append(jsonReturned);
            		$("#denyDiv").show();
            	} else {

                $("#requests").html(getErrorMessage("You are not authorized to view this content", "UNAUTHORIZED"));
              }
            });
          });
          window.scrollTo(0, 0);
    	  handleDenyAllAppointments();
	}

/*
* Listener for the accept buttons
* Once it is clicked then userID is fetched from the UI and sent through
* Results from the server is shown via a lightbox
*/
function acceptReqistrationListener()
{
     //Click accept
      $(document).on('click', '.accept', (function(e) {
      spawnBusyMessage("Approving Request");
        e.preventDefault();
        var tempThis = $(this);
        var tempChildren = $(this).parent().parent().parent().parent().parent().children().length;
          $.ajax({
            type: "post",
            data: {"staffID" : $(this).parent().siblings('.userCol').find('.userID').find('.actualID').text()},
            url: "/approveRequest"
          }).then(function(jsonReturned) {

            if(jsonReturned != "Request approved"){
              window.scrollTo(0, 0);
              $('.featherlight').click();
              spawnErrorMessage("Unable to Approve Request: " + jsonReturned);
            } else {
              $('.featherlight').click();
            //  location.reload();
              tempThis.parent().parent().parent().parent().remove();
              if(tempChildren - 1 == 0){
                $("#requests").append(getWarningMessage("No pending registration requests"));
              }
            }
          });
        }));
}

/*
* Listener for the deny buttons
* Once it is clicked then userID is fetched from the UI and sent through
* Results from the server is shown via a lightbox
*/
function denyRegistrationListener()
{
    //Click deny
      $(document).on('click', '.deny', (function(e) {
        spawnBusyMessage("Denying Request");
        e.preventDefault();
        var tempThis = $(this);
        var tempChildren = $(this).parent().parent().parent().parent().parent().children().length;
          $.ajax({
            type: "post",
            data: {"staffID" : $(this).parent().siblings('.userCol').find('.userID').find('.actualID').text()},
            url: "/denyRequest"
          }).then(function(jsonReturned) {
            if(jsonReturned != "Request denied"){
              window.scrollTo(0, 0);
              $('.featherlight').click();
             spawnErrorMessage("Unable to Deny Request: " + jsonReturned);
            } else {
              //location.reload();
              $('.featherlight').click();
              tempThis.parent().parent().parent().parent().remove();
              if(tempChildren - 1 == 0){
                $("#requests").append(getWarningMessage("No pending registration requests"));
              }
            }
          });
      }));
}

/*
* Once the denyAll button is clicked it calls the handler of each other deny button on the page
*/
function handleDenyAllAppointments()
{
    $(document).on('click', '#DenyAll', (function(e) {
        $('.deny').click();
      }));
}
});