$(document).ready(function() {
// initialize input widgets first
        $('#appointmentDate .time').timepicker({
            'showDuration': false,
            'timeFormat': 'H:i',
            'minTime' : '07:30',
            'maxTime' : '16:00',
            'step' : 30,
            'forceRoundTime': true,
            'disableTimeRanges': [
              ['00:00', '06:00'],
              ['17:00', '23:59']
            ],
            'scrollDefault': 'now'
        });

        $('#appointmentDate .end').timepicker({
          'showDuration': true,
            'timeFormat': 'H:i',
            'minTime' : '07:30',
            'maxTime' : '16:00',
            'step' : 30,
            'forceRoundTime': true,
            'disableTimeRanges': [
              ['00:00', '06:00'],
              ['17:00', '23:59']
            ],
            'scrollDefault': 'now'
        });

        $('#appointmentDate .date').datepicker({
            'format': 'mm/dd/yyyy',
            'autoclose': true,
            'minDate': 0,
            dateFormat: 'yy-mm-dd'
        });

        // initialize datepair
        $('#appointmentDate').datepair();
  /******************** Request Appointment ***********************************/

  if(document.title == "Make Appointment"){
     $.ajax({
        type: "post",
        url: "/getActiveUsers"
      }).then(function(jsonReturned) {
        $("#appointmentWith").append(jsonReturned);
      });
      window.scrollTo(0, 0);
  }

  var timeVar
  var currentdate = new Date(); 
  var dateVar = "";

  dateVar += currentdate.getFullYear() + "-";

  if((currentdate.getMonth()+1).toString().length == 1)
  {
    dateVar += "0";
  }
  
  dateVar += currentdate.getMonth()+1 + "-";

  if(currentdate.getDate().toString().length == 1)
  {
    dateVar += "0";
  }
  
  dateVar += currentdate.getDate();
  

  var time = currentdate.getHours() + ":" + currentdate.getMinutes();

  timeVar = time;
  

  $('#numMembers').change(function() {
    
    if($("#numMembers").val() >= 1)
    {
      $prevInputs = $('.appointmentDetails').length;

      if($prevInputs < $("#numMembers").val())
      {
        for($i = $prevInputs; $i < $("#numMembers").val(); $i++)
        {
          var element = $('<div class="appointmentDetails"><p class="text-left">Appointment made by (your name/team members names): </p><input class="form-control appointmentBy" type="text" name="appointmentBy[]"/><p class="text-left">Your/team members email:</p><input class="form-control email" type="email" id="email" name="email"/></div>');
          //var element = $('<input class="form-control appointmentBy" type="text" name="appointmentBy[]"/>');
          $('#appointmentMadeBy').append(element);
        }
      } 
      else if($prevInputs > $("#numMembers").val())
      {
        for($i = $prevInputs; $i > $("#numMembers").val(); $i--)
        {
          $($(".appointmentDetails")[$i-1]).remove();
        }
      }

      $('#errorNumbers').remove();
      $('#madeByError').remove();
    }
    else 
    {
      $prevInputs = $('.appointmentDetails').length;
      $("#numMembers").val(1);
      for($i = $prevInputs; $i > $("#numMembers").val(); $i--)
      {
        $($(".appointmentDetails")[$i-1]).remove();
      }
      $element = $('<p class="error" id="errorNumbers">The minimum of people making an appointment is one. </p>');
      $('#errorNumbers').remove();
      $('#membersNum').append($element);
    }
  });

  $('#makeAppointmentRequest').click(function(e) {
    e.preventDefault(); 

    if($("#numMembers").val() >= 1)
    {
      $('#errorNumbers').remove();
    }

    //Check for errors
    $noError = true;

    //check time
    if($("#requestedDateTime").val() == dateVar && $("#timeStart").val() <= timeVar)
    {
      $element = $('<p class="error" id="timeError">The appointments must be in the future</p>');
      $('#timeError').remove();
      $('#appointmentDate').append($element);
      $noError = false;
    }
    else
    {
      $('#timeError').remove();
    }

    //check date and time
    if($("#requestedDateTime").val() == "" || $("#timeStart").val() == "")
    {
      $element = $('<p class="error" id="dateError">Please select a date and time</p>');
      $('#dateError').remove();
      $('#appointmentDate').append($element);
      $noError = false;
    }
    else
    {
      $('#dateError').remove();
    }

    //check duration
    if($("#timeEnd").val() == $("#timeStart").val())
    {
      $element = $('<p class="error" id="errorDuration">Your appointment duration must be atleast 30 minutes.</p>');
      $('#errorDuration').remove();
      $('#appointmentDate').append($element);
      $noError = false;
    } 
    else
    {
      $('#errorDuration').remove();
      $duration = ($('#appointmentDate').datepair('getTimeDiff') / 1000 / 60);
    }

    //check appointmentBy
    $allFilledIn = true;

    $inputs = $(".appointmentBy");
    $temp = [];
    for($i = 0; $i < $inputs.length; $i++){
      if($($inputs[$i]).val() == "")
      {
        $element = $("<p class='error' id='madeByError'>All members' names must be entered. </p>");
         $("#madeByError").remove();
         $('#appointmentMadeBy').append($element);
        $allFilledIn = false;
        $noError = false;
      }
      else 
      {
        if($i == $inputs.length-1 && $allFilledIn == true)
        {
          $("#madeByError").remove();
        }
        $temp[$i] = $($inputs[$i]).val();
      }
    }
    $temp = $temp.join(", ");
    
    //check emails
    $allEmailsFilledIn = true;

    $inputs = $(".email");
    $tempEmail = [];
    for($i = 0; $i < $inputs.length; $i++){
      var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
      if($($inputs[$i]).val() == "" || !regex.test($($inputs[$i]).val()))
      {
        $element = $("<p class='error' id='emailError'>All members' emails must be entered. </p>");
        $("#emailError").remove();
        $('#appointmentMadeBy').append($element);
        $allEmailsFilledIn = false;
        $noError = false;
      }
      else 
      {
        if($i == $inputs.length-1 && $allEmailsFilledIn == true)
        {
          $("#emailError").remove();
        }
        $tempEmail[$i] = $($inputs[$i]).val();
      }
    }
    $tempEmail = $tempEmail.join(", ");

    //check reason
    if($("#appointmentReason").val() == "")
    {
      $element = $('<p class="error" id="errorReason">A reason for the appointment must be given. </p>');
      $('#errorReason').remove();
      $('#reason').append($element);
      $noError = false;
    } else
    {
      $('#errorReason').remove();
    }

    //appointmentWith error checking not yet done

    //send data if no errors
    if($noError == true)
    {
      $.ajax({
        type: "post",
        data: {"appointmentWith" : $('#appointmentWith').val(),
               "requestedDateTime" : ($("#requestedDateTime").val()+"T"+$("#timeStart").val()+":30+02:00"),
               "appointmentBy" : $temp,
               "appointmentDuration" : $duration,
               "appointmentReason" : $('#appointmentReason').val(),
               "appointmentEmails" : $tempEmail},
        url: "/requestAppointment"
      }).then(function(jsonReturned) {
        $("#signIn").text(jsonReturned);

          //cleanup user input on successfull appointment request
          $( ":text" ).val("");
          $( "input[type=email]" ).val("");
          $("#appointmentWith").prop('selectedIndex', 0);

          for($i = $("#numMembers").val(); $i > 1; $i--)
          {
            $($(".appointmentDetails")[$i-1]).remove();
          }
          $("#numMembers").val(1);
          $("#appointmentDuration").val(15);
      });
      window.scrollTo(0, 0);
     
    } else {
      $("#signIn").text("Request an appointment");
      window.scrollTo(0, 0);
    }
  });
  /***************************************************************************/

  /******************** Cancel Appointment ***********************************/

  $('#cancelAppointment').click(function(e) {
    e.preventDefault(); 

    //Check for errors
    $noError = true;

    //add errorID
    if($("#appointmentID").val() != "")
    {
      $('#errorID').remove();
    }
    else
    {
      $noError = false;
      $element = $('<p class="error" id="errorID">A valid appointment ID has to be entered. </p>');
      $('#errorID').remove();
      $('#appointmentCancel').append($element);
    }

    //add cancellee error
    if($("#cancelBy").val() != "")
    {
      $('#cancelError').remove();
    }
    else
    {
      $noError = false;
      $element = $('<p class="error" id="cancelError">You must enter who it is that wants to cancel the appointment. </p>');
      $('#cancelError').remove();
      $('#appointmentCancellee').append($element);
    }

    //send data if no errors
    if($noError == true)
    {
      $.ajax({
        type: "post",
        data: {"cancellee" : $('#cancelBy').val(),
               "appointmentID" : $('#appointmentID').val()},
        url: "/cancelAppointment"
      }).then(function(jsonReturned) {
        $("#signIn").text(jsonReturned);
        $("#appointmentID").val("");
        $("#cancelBy").val("");
      });
      window.scrollTo(0, 0);
    } else {
      $("#signIn").text("Cancel an appointment");
      window.scrollTo(0, 0);
    }
  });

  /***************************************************************************/

  /******************** Check Appointment Status ***********************************/

  $('#checkStatus').click(function(e) {
    e.preventDefault(); 

    //Check for errors
    $noError = true;

    //add errorID
    if($("#appointmentID").val() != "")
    {
      $('#errorID').remove();
    }
    else
    {
      $noError = false;
      $element = $('<p class="error" id="errorID">A valid appointment ID has to be entered. </p>');
      $('#errorID').remove();
      $('#appointmentStatusRequest').append($element);
    }

    //add cancellee error
    if($("#requestedBy").val() != "")
    {
      $('#statusError').remove();
    }
    else
    {
      $noError = false;
      $element = $('<p class="error" id="statusError">You must enter who it is that wants to check the appointment status. </p>');
      $('#statusError').remove();
      $('#statusRequest').append($element);
    }

    //send data if no errors
    if($noError == true)
    {
      $.ajax({
        type: "post",
        data: {"requester" : $('#requestedBy').val(),
               "appointmentID" : $('#appointmentID').val()},
        url: "/status"
      }).then(function(jsonReturned) {
        var obj =  $("#signIn").text(jsonReturned);
        obj.html(obj.html().replace(/\n/g,'<br/>'));
        $("#appointmentID").val("");
        $("#requestedBy").val("");
      });
      window.scrollTo(0, 0);
    } else {
      $("#signIn").text("Check Appointment Status");
      window.scrollTo(0, 0);
    }
  });

  /***************************************************************************/

  /*****************************Approve/Deny**********************************/
  if(document.title == "Approve/Deny Appointment"){
     $.ajax({
        type: "post",
        url: "/getApproveOrDeny"
      }).then(function(jsonReturned) {
        $("#fieldset").append(jsonReturned);
      });
      window.scrollTo(0, 0);
  }

  //Click accept
  $(document).on('click', '.accept', (function(e) {
    e.preventDefault();
    var tempThis = $(this);
      $.ajax({
        type: "post",
        data: {"appointmentID" : $(this).siblings('.appointmentID').val(),
                "staffMember" : $(this).siblings('.staffID').val()},
        url: "/approve"
      }).then(function(jsonReturned) {
        if(jsonReturned != "Appointment approved"){
          window.scrollTo(0, 0);
          $("#signIn").text(jsonReturned);
        } else {
          tempThis.parent().remove();
          $("#signIn").text("Approve or Deny Appointments");
          if(tempThis.parent().parent().children().length == 0){
            $("#fieldset").append("<p>No appointments pending</p>");
          }
        }
      }); 
    }));

  //Click deny
  $(document).on('click', '.deny', (function(e) {
    e.preventDefault();
    var tempThis = $(this);
      $.ajax({
        type: "post",
        data: {"appointmentID" : $(this).siblings('.appointmentID').val(),
                "staffMember" : $(this).siblings('.staffID').val()},
        url: "/deny"
      }).then(function(jsonReturned) {
        if(jsonReturned != "Appointment denied"){
          window.scrollTo(0, 0);
          $("#signIn").text(jsonReturned);
        } else {
          tempThis.parent().remove();
          $("#signIn").text("Approve or Deny Appointments");
          if(tempThis.parent().parent().children().length == 0){
            $("#fieldset").append("<p>No appointments pending</p>");
          }
        }
      }); 
  }));
  /***************************************************************************/
});