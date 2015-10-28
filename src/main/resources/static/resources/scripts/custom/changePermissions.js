$(document).ready(function() {

 // getUsers();
  getPermissions();
  handleDynamicInputClicks();
  //$("#permissionForm").hide();
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
          $("#permissionAuthorization").hide();
          $("#permissionForm").show();
          $("#permissions").empty();
          getUserPermissions();
          removePermissionListener();
          $("#update").show();
        }
      });
      window.scrollTo(0, 0);
     }

    //Function that gets all users of the system
    /*function getUsers(){
        $("#users").click(function(){
        $("#users").prop('disabled', true);
        $("#users").val("Loading Staff Member List...");
          $.ajax({
                  type: "post",
                  url: "/getUsersForPermissionUpdate"
                }).then(function(jsonReturned) {
                  $.featherlight(jsonReturned,null);
                   $("#users").prop('disabled', false);
                });
                window.scrollTo(0, 0);
        });
    }*/

    //Function that gets all permissions possible
    function getPermissions(){

    $("#newPermissions").click(function(){
        $("#newPermissions").prop('disabled', true);
        $("#newPermissions").val("Loading Permissions...");
          $.ajax({
                  type: "post",
                  url: "/getAllPermissions"
                }).then(function(jsonReturned) {
                  $.featherlight(jsonReturned,null);
                   $("#newPermissions").prop('disabled', false);
                });
                window.scrollTo(0, 0);
        });
    }

    //Function that gets a users permissions
    function getUserPermissions(){
      if($("#users").val())
      {
        $.ajax({
          type: "post",
          data: {"staffID": $("#users").val()},
          url: "/getUserPermissions"
        }).then(function(jsonReturned) {
           // $("#permissionsHide").show();
            $("#permissions").empty();
            if(jsonReturned == ""){
              jsonReturned = "no permissions";
            }
            $("#permissions").append(jsonReturned);
          });
      } else {
      //  $("#permissionsHide").hide();
      }
    }
    

    //Listener for the update click event. Updates the selected user's permissions
    function addPermission(){
      $(document).on('click', '#update', (function(e) {
        e.preventDefault();
        if(!$("#users").val || !$("#newPermissions").val())
        {
          spawnErrorMessage("Please fill in all inputs");
        } else {
          spawnBusyMessage("Adding Permission");
          
          $.ajax({
            type: "post",
            data: {"permission" : $("#newPermissions").val(),
                   "staffID" : $("#users").val()},
            url: "/addPermission"
          }).then(function(jsonReturned) {
          if(jsonReturned == "Permission added")
            spawnSuccessMessage("Permission Added");
          else
            spawnErrorMessage(jsonReturned);
            getUserPermissions();
          });
        }
      }));
    }

    function removePermissionListener(){
      $(document).on('click', '.deny', (function(e) {
           spawnBusyMessage("Removing Permission");
          e.preventDefault();
          var tempThis = $(this);
          $.ajax({
            type: "post",
            data: {"permission" : tempThis.parent().parent().siblings('.userPermission').text(),
                   "staffID" : $("#users").val()},
            url: "/removePermission"
          }).then(function(jsonReturned) {
            if(jsonReturned == "Permission removed")
            {
                spawnSuccessMessage("Permission Removed");
                getUserPermissions();
            }
            else
                spawnErrorMessage(jsonReturned);


          });
      }));
    }

    //Calls functions each time a new user is selected
    $("#users").change(function () {
      getUserPermissions();
    });

    function handleDynamicInputClicks()
    {
        $(document.body).on('click', '#appointmentsubmit' ,function(){
                    $('.featherlight').click();
                    $('#users').val($('#appointmentData').val());
                    getUserPermissions();
                });
        $(document.body).on('click', '#permissionsubmit' ,function(){
                        $('.featherlight').click();
                        $('#newPermissions').val($('#appointmentData').val());
                    });
         $(document.body).on('click', '.btnLightbox' ,function(){
                            $('.featherlight').click();
                        });
    }
});

