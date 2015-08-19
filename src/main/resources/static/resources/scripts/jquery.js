$(document).ready(function() {
       
  $('#makeAppointmentRequest').click(function(e) {
    e.preventDefault(); 
    $.ajax({
      type: "post",
      data: {"appointmentWith" : $('#appointmentWith').val(),
             "requestedDateTime" : $('#requestedDateTime').val(),
             "appointmentBy" : $('#appointmentBy').val()},
      url: "http://localhost:8080/requestAppointment"
    }).then(function(jsonReturned) {
      //console.log(jsonReturned);
    });
  });
});