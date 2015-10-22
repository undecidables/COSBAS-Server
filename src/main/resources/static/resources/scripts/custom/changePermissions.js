$(document).ready(function() {

  getUsers();
  getPermissions();
  $("#permissionForm").hide();
	 /*
    * Calls the controller function to get all users and their permissions
    */
    if(document.title == "Change Permissions"){
      addPermission();
     $.ajax({
        type: "post",
        data: {"authorized": "SUPER"},
        url: "/authorizedToAccessRequests"
      }).then(function(appovalReturned) {
      if(appovalReturned == "authorized")
        {
          $("#permissionForm").show();
          $("#permissions").empty();
          getUserPermissions();
          removePermissionListener();
          if(($("#users").val() != "no users") && ($("#permissions").val() != "no permissions"))
          {
            $("#update").show();
          } else {
            $("#update").hide();
          }
        }
      });
      window.scrollTo(0, 0);
     }

    //Function that gets all users of the system
    function getUsers(){
      $.ajax({
        type: "post",
        url: "/getUsersForPermissionUpdate"
      }).then(function(jsonReturned) {
          $("#users").empty();
          $("#users").append(jsonReturned);
      });
    }

    //Function that gets all permissions possible
    function getPermissions(){
      $.ajax({
        type: "post",
        url: "/getAllPermissions"
      }).then(function(jsonReturned) {
        $("#newPermissions").empty();
        $("#newPermissions").append(jsonReturned);
      });
    }

    //Function that gets a users permissions
    function getUserPermissions(){
      if($("#users").val() != "no users")
      {
        $.ajax({
          type: "post",
          data: {"staffID": $("#users").val()},
          url: "/getUserPermissions"
        }).then(function(jsonReturned) {
            $("#permissions").empty();
            $("#permissions").append(jsonReturned);
          });
        }
      }
    

    //Listener for the update click event. Updates the selected user's permissions
    function addPermission(){
      $(document).on('click', '#update', (function(e) {
          e.preventDefault();

          $.ajax({
            type: "post",
            data: {"permission" : $("#newPermissions").val(),
                   "staffID" : $("#users").val()},
            url: "/addPermission"
          }).then(function(jsonReturned) {
            getUserPermissions();
          });
      }));
    }

    function removePermission(){
      $(document).on('click', '.deny', (function(e) {
          e.preventDefault();
          var tempThis = $(this);
          $.ajax({
            type: "post",
            data: {"permission" : tempThis.parent().parent().siblings('.userPermission').text(),
                   "staffID" : $("#users").val()},
            url: "/removePermission"
          }).then(function(jsonReturned) {
            getUserPermissions();
          });
      }));
    }
});