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
                $("#loadingRequests").html("<span>You are not authorized </span>to view this content");
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
       $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Approving Request</span> Please Wait...</h6>");
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
              $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Error " + jsonReturned + " occured</span> could not aprove request</h6>");
            } else {
              $('.featherlight').click();
            //  location.reload();
              tempThis.parent().parent().parent().parent().remove();
              if(tempChildren - 1 == 0){
                $("#requests").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>");
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
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Denying Request</span> Please Wait...</h6>");
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
              $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Error " + jsonReturned + " occured</span> could not deny request</h6>");
            } else {
              //location.reload();
              $('.featherlight').click();
              tempThis.parent().parent().parent().parent().remove();
              if(tempChildren - 1 == 0){
                $("#requests").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>");
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
        $("#requests").empty();
        $("#requests").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>");
      }));
}
});