$(document).ready(function() {
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




});


function acceptAppointmentListener()
{
     //Click accept
      $(document).on('click', '.accept', (function(e) {
       $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Approving Appointment</span> Please Wait...</h6>");
        e.preventDefault();
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
              $("#signIn").text(jsonReturned);
            } else {
               location.reload();
              tempThis.parent().remove();
              $("#signIn").text("Approve or Deny Appointments");
              if(tempChildren - 1 == 0){
                $("#fieldset").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No Appointments</span> Pending</h4>");
              }
            }
          });
        }));
}

function denyAppointmentListener()
{
    //Click deny
      $(document).on('click', '.deny', (function(e) {
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Denying Appointment</span> Please Wait...</h6>");
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
              $("#signIn").text(jsonReturned);
            } else {
               location.reload();
              tempThis.parent().remove();
              $("#signIn").text("Approve or Deny Appointments");
              if(tempChildren - 1 == 0){
                $("#fieldset").append("<h4 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>No Appointments</span> Pending</h4>");
              }
            }
          });
      }));
}