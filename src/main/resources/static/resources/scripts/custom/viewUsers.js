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
       /*   $.ajax({
            type: "post",
            data: {"authorized": "SUPER"},
            url: "/authorizedToAccessRequests"
            }).then(function(appovalReturned) {
              if(appovalReturned == "authorized")
              {
                 $(".change").show(); 
                 $(".userPermissions").show();
              }
            });*/
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
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Removing user</span> Please Wait...</h6>");
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
              $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Error " + jsonReturned + " occured</span> could not remove user</h6>");
            } else {
              tempThis.parent().remove();
              if(tempChildren - 1 == 0){
                $("#users").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No users on the system</span></h4>");
              }
            }
          });
      }));
}
});