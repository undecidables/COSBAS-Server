$(document).ready(function() {
    /*
    * Calls the controller function to get all requested appointments and the appends it to the html
    */
    handleDynamicInputClicks();
     if(document.title == "Approve/Deny Appointment"){
     acceptAppointmentListener();
     denyAppointmentListener();
     $.ajax({
            type: "post",
            url: "/getApproveOrDeny"
          }).then(function(jsonReturned) {
            $("#fieldset").append(jsonReturned);
          });
          window.scrollTo(0, 0);
      }
    handleDenyAllAppointments();



});

/*
* Listener for the accept buttons
* Once it is clicked then the hidden input from UI - the appointmentID and the staff member ID  is send to the server
* Results from the server is shown via a lightbox
*/
function acceptAppointmentListener()
{
     //Click accept
      $(document).on('click', '.accept', (function(e) {
        e.preventDefault();
        spawnBusyMessage("Approving Appointment");
        var tempThis = $(this);
        var tempChildren = $(this).parent().parent().parent().parent().parent().children().length;
          $.ajax({
            type: "post",
            data: {"appointmentID" : $(this).parent().parent().siblings('.hiddenData').find('.appointmentID').val(),
                    "staffMember" : $(this).parent().parent().siblings('.hiddenData').find('.staffID').val()},
            url: "/approve"
          }).then(function(jsonReturned) {

            if(jsonReturned != "Appointment approved"){
              window.scrollTo(0, 0);
              spawnErrorMessage(jsonReturned);
            } else {

               location.reload();
               spawnSuccessMessage(jsonReturned);
              tempThis.parent().parent().parent().parent().remove();
              if(tempChildren - 1 == 0){
                $("#fieldset").append(getErrorMessage("No Appointments Pending"));
              }
            }
          });
        }));
}

/*
* Listener for the deny buttons
* Once it is clicked then the hidden input from UI - the appointmentID and the staff member ID  is send to the server
* Results from the server is shown via a lightbox
*/
function denyAppointmentListener()
{
    //Click deny
      $(document).on('click', '.deny', (function(e) {
        spawnBusyMessage("Denying Appointment");
        e.preventDefault();
        var tempThis = $(this);
        var tempChildren = $(this).parent().parent().parent().parent().parent().children().length;
          $.ajax({
            type: "post",
            data: {"appointmentID" : $(this).parent().parent().siblings('.hiddenData').find('.appointmentID').val(),
                    "staffMember" : $(this).parent().parent().siblings('.hiddenData').find('.staffID').val()},
            url: "/deny"
          }).then(function(jsonReturned) {
            if(jsonReturned != "Appointment denied"){
              window.scrollTo(0, 0);
               spawnErrorMessage(jsonReturned);
            } else {
               location.reload();
               spawnSuccessMessage(jsonReturned);
              tempThis.parent().parent().parent().parent().remove();
              $("#signIn").text("Approve or Deny Appointments");
              if(tempChildren - 1 == 0){
                $("#fieldset").append(getWarningMessage("No Appointments Pending"));
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
        //$("#fieldset").empty();
        //$("#fieldset").append(getWarningMessage("No Appointments Pending"));
        //window.location.reload(true);
      }));
}

function handleDynamicInputClicks()
{

     $(document.body).on('click', '.btnLightbox' ,function(){
                        $('.featherlight').click();
                    });
}