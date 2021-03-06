$(document).ready(function() {
	 /*
    * Calls the controller function to get all registered users on the system
    */
     if(document.title == "Registered Users"){
     removeUserListener();
     $.ajax({
            type: "post",
            url: "/getRegisteredUsers"
          }).then(function(jsonReturned) {
            $("#users").empty();
            $("#users").append(jsonReturned);
            $(".deny").hide();

          	$.ajax({
            type: "post",
            data: {"authorized": "USER_DELETE"},
            url: "/authorizedToAccessRequests"
          	}).then(function(appovalReturned) {
          		if(appovalReturned == "authorized")
          		{
            	   $(".deny").show();
            	}
            });
          });
          window.scrollTo(0, 0);
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
        spawnBusyMessage("Removing User");
        e.preventDefault();
        var tempThis = $(this);
        var tempChildren = $(this).parent().parent().parent().parent().parent().children().length;
          $.ajax({
            type: "post",
            data: {"staffID" : $(this).parent().siblings('.userCol').find('.userID').text()},
            url: "/removeUser"
          }).then(function(jsonReturned) {
            if(jsonReturned != "User removed"){
              window.scrollTo(0, 0);
              $('.featherlight').click();
              spawnErrorMessage("Could not remove User: " + jsonReturned);
            } else {
              tempThis.parent().remove();
              if(tempChildren - 1 == 0){

                $("#users").append(getWarningMessage("No users on the system"));

              }
            }
          });
      }));
}
});