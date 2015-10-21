$(document).ready(function() {
	 /*
    * Calls the controller function to get all registered users on the system
    */
     if(document.title == "Registered Users"){
     changePermisionListener();
     removeUserListener();
     $.ajax({
            type: "post",
            url: "/getRegisteredUsers"
          }).then(function(jsonReturned) {
            $("#users").append(jsonReturned);
            //hide delete
            //hide permissions
          	/*$.ajax({
            type: "post",
            data: {"authorized": "USER_DELETE"},
            url: "/authorizedToAccessRequests"
          	}).then(function(appovalReturned) {
          		if(appovalReturned == "authorized")
          		{
            	   //unhide delete buttons

            	}
            });*/
          /*$.ajax({
            type: "post",
            data: {"authorized": "SUPER"},
            url: "/authorizedToAccessRequests"
            }).then(function(appovalReturned) {
              if(appovalReturned == "authorized")
              {
                 //unhide permission buttons  
                 //unhide permissions
              }
            });*/
          });
          window.scrollTo(0, 0);
	}

/*
* Listener for the changePermissions buttons
* Once it is clicked then userID is fetched from the UI and sent through
* Results from the server is shown via a lightbox
*/
function changePermisionListener()
{
     //Click accept
      $(document).on('click', '.change', (function(e) {
       $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Opening Permissions</span> Please Wait...</h6>");
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
              location.reload();
              tempThis.parent().remove();
              if(tempChildren - 1 == 0){
                $("#requests").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>");
              }
            }
          });
        }));
}

/*
* Listener for the removeUser buttons
* Once it is clicked then userID is fetched from the UI and sent through
* Results from the server is shown via a lightbox
*/
function removeUserListener()
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
              location.reload();
              tempThis.parent().remove();
              if(tempChildren - 1 == 0){
                $("#requests").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No registration requests pending</span></h4>");
              }
            }
          });
      }));
}
});