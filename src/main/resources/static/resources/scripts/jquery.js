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
            'beforeShowDay': $.datepicker.noWeekends,
            'format': 'mm/dd/yyyy',
            'autoclose': true,
            'minDate': 0,
            'dateFormat': 'yy-mm-dd',

        });

        // initialize datepair
        $('#appointmentDate').datepair();
  /******************** Request Appointment ***********************************/

  if(document.title == "Make Appointment"){
    $("#appointmentWith").click(function(){
    $("#appointmentWith").prop('disabled', true);
    $("#appointmentWith").val("Loading Staff Member List...");
      $.ajax({
              type: "post",
              url: "/getActiveUsers"
            }).then(function(jsonReturned) {
              $.featherlight(jsonReturned,null);
               $("#appointmentWith").prop('disabled', false);
            });
            window.scrollTo(0, 0);
    });

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
  

  var time =  currentdate.getHours() + ":" + currentdate.getMinutes();

  timeVar = time;
  
    $('.appointmentBy').focus(function() {

        if($("#numMembers").val() >= 1)
        {

          //$prevInputs = $('.appointmentDetails').length+1;
            var amountMembers = $("#numMembers").val();

          //if($prevInputs < $("#numMembers").val())
         // {
            var lightbox = '<div id="lightbox">' +
                                '<div id="content">';

            for($i = 0; $i < $("#numMembers").val(); $i++)
            {
              lightbox += '<p class="text-left">Your team member\'s name:</p><input class="form-control nameinput" type="text" id="appointmentBy" name="appointmentBy"/>';
            }
            lightbox += "<br/>"
            lightbox +=  '<p class="text-left">' +
                            '<button type="submit" id="namesubmit" class="btnLightbox btn-common">Confirm Names</button>' +
                         '</p>';
            lightbox += '</div>' +
                      '</div>';

            $.featherlight(lightbox, null);
        }
      });

      var emailString;
      $temp = [];
        $(document.body).on('click', '#namesubmit' ,function(){
               //check appointmentBy
               $allFilledIn = true;

               $inputs = $(".nameinput");
               
               for($i = 0; $i < $inputs.length; $i++){
                 if($($inputs[$i]).val() == "")
                 {
                   $element = $("<p class='error' id='madeByError'>All members' names must be entered. </p>");
                   $.featherlight($element);
                   $allFilledIn = false;
                   $noError = false;
                   return;
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
            $('.featherlight').click();
            $('.appointmentBy').val($temp);
        });
  $('#email').focus(function() {

    if($("#numMembers").val() >= 1)
    {

      //$prevInputs = $('.appointmentDetails').length+1;
        var amountMembers = $("#numMembers").val();

      //if($prevInputs < $("#numMembers").val())
     // {
        var lightbox = '<div id="lightbox">' +
                            '<div id="content">';

        for($i = 0; $i < $("#numMembers").val(); $i++)
        {
          lightbox += '<p class="text-left">Your team member\'s email:</p><input class="form-control email emailinput" type="email" id="email" name="email"/>';
        }
        lightbox += "<br/>"
        lightbox +=  '<p class="text-left">' +
                        '<button type="submit" id="emailsubmit" class="btnLightbox btn-common">Confirm Emails</button>' +
                     '</p>';
        lightbox += '</div>' +
                  '</div>';
        $.featherlight(lightbox, null);
    }
  });
    $(document.body).on('click', '#appointmentsubmit' ,function(){
        $('.featherlight').click();
        $('#appointmentWith').val($('#appointmentData').val());
    });
    $(document.body).on('click', '#calendarOk' ,function(){
            $('.featherlight').click();
        });


  var emailString;
  $tempEmail = [];
  $(document.body).on('click', '#emailsubmit' ,function(){
     //check emails
     $allEmailsFilledIn = true;
     $inputs = $(".emailinput");
      for($i = 0; $i < $inputs.length; $i++){
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if($($inputs[$i]).val() == "" || !regex.test($($inputs[$i]).val()))
        {
          $element = $("<p class='error' id='emailError'>Invalid E-Mail/All members' emails must be entered. </p>");
          $.featherlight($element);
          $allEmailsFilledIn = false;
          $noError = false;
          return;
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
      $('.featherlight').click();
      $('.email').val($tempEmail);
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
      $element = $('<p class="error" id="timeError">The appointments must be in the future.</p>');
      $.featherlight($element);
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
      $.featherlight($element);
      $noError = false;
    }
    else
    {
      $('#dateError').remove();
    }

    //check duration
    if($("#timeEnd").val() == $("#timeStart").val())
    {
      $element = $('<p class="error" id="errorDuration">Your appointment duration must be at least 30 minutes.</p>');
      $.featherlight($element);
      $noError = false;
    } 
    else
    {
      $('#errorDuration').remove();
      $duration = ($('#appointmentDate').datepair('getTimeDiff') / 1000 / 60);
    }

    //check reason
    if($("#appointmentReason").val() == "")
    {
      $element = $('<p class="error" id="errorReason">A reason for the appointment must be given. </p>');
      $.featherlight($element);
      $noError = false;
    } else
    {
      $('#errorReason').remove();
    }

    //appointmentWith error checking not yet done
    if($tempEmail.length == 0)
    {
      $element = $('<p class="error" id="errorEmails">All member emails must be given. </p>');
      $.featherlight($element);
      $noError = false;
    } else 
    {
      $("#errorEmails").remove();
    }


    if($temp.length == 0)
    {
      $element = $('<p class="error" id="errorNames">All member names must be given. </p>');
      $.featherlight($element);
      $noError = false;
    } else 
    {
      $("#errorNames").remove();
    }

    if($('#appointmentWith').val() == "")
    {
      $element = $('<p class="error" id="appointmentWithError">Must make an appointment with someone. </p>');
      $.featherlight($element);
      $noError = false;
    } else {
      $("#appointmentWithError").remove();
    }

    //send data if no errors
    if($noError == true)
    {
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Requesting Appointment</span> Please Wait...(this may take a while)</h6>");
      $.ajax({
        type: "post",
        data: {"appointmentWith" : $('#appointmentWith').val(),
               "requestedDateTime" : ($("#requestedDateTime").val()+"T"+$("#timeStart").val()+":30+02:00"),
               "appointmentBy" : $temp,
               "appointmentDuration" : $duration,
               "appointmentReason" : $('#appointmentReason').val(),
               "appointmentEmails" : $tempEmail},
        url: "/requestAppointment"
      }). then(function(jsonReturned) {
            $('.featherlight').click();
            $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Requested Appointment</span> Successfully! An email has been sent.</h6>");
          $("#signIn").text(jsonReturned);
          if(jsonReturned != "Time not available"){

            //cleanup user input on successfull appointment request
            $( ":text" ).val("");
            $( "input[type=email]" ).val("");
            $("#appointmentWith").prop('selectedIndex', 0);

            for($i = $("#numMembers").val(); $i > 1; $i--)
            {
              $($(".appointmentDetails")[$i-1]).remove();
            }
            $("#numMembers").val(1);
          }
          else
          {
            $('.featherlight').click();
            //var html = "<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><p>"+jsonReturned+"!</p><br/></h6>";
            $.ajax({
              type: "post",
              url: "/dayAvailable"
            }). then(function(jsonReturned) {
                var html = "<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><p>Time Not Available!</p><p>The Following Times Are Unavailable</p></h6>" + jsonReturned;
                 $.featherlight(html);
            });
            
            //$.featherlight(html);
          }
      window.scrollTo(0, 0);
     });
    } else {
      $("#signIn").text("Request an appointment");
      window.scrollTo(0, 0);
    }
  });
  /***************************************************************************/

  //Read URL parameter from URL
  function getUrlParameter(parameter) {
    $pageURL = decodeURIComponent(window.location.search.substring(1));
    $variables = $pageURL.split('&');

    for ($i = 0; $i < $variables.length; $i++) {
        $parameterName = $variables[$i].split('=');

        if ($parameterName[0] === parameter) {
            return $parameterName[1];
        } 
    }

    return "";
  };

  /******************** Cancel Appointment ***********************************/

  if(document.title == "Cancel Appointment"){
      $("#appointmentID").val(getUrlParameter('ID'));
  }

  $('#cancelAppointment').click(function(e) {
    e.preventDefault(); 
    $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Cancelling Appointment</span> Please Wait...</h6>");
    //Check for errors
    $noError = true;
    $errorNum = 0;

    //add errorID
    if($("#appointmentID").val() != "")
    {
      $('#errorID').remove();
    }
    else
    {
      $errorNum++;
      if($errorNum == 1){
        $('.featherlight').click();
      }
      $noError = false;
      $element = $('<p class="error" id="errorID">A valid appointment ID has to be entered. </p>');
      $.featherlight($element);
    }

    //add cancellee error
    if($("#cancelBy").val() != "")
    {
      $('#cancelError').remove();
    }
    else
    {
      $errorNum++;
      if($errorNum == 1){
        $('.featherlight').click();
      }

      $noError = false;
      $element = $('<p class="error" id="cancelError">You must enter who it is that wants to cancel the appointment. </p>');
      $.featherlight($element);
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
        $('.featherlight').click();
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><p>"+jsonReturned+"</p>!</h6>");
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
    if(document.title == "Check Appointment Status"){
      $("#appointmentID").val(getUrlParameter('ID'));
    }

  $('#checkStatus').click(function(e) {
    e.preventDefault(); 
     $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Checking Appointment Status</span> Please Wait...</h6>");
    //Check for errors
    $noError = true;
    $errorNum = 0;

    //add errorID
    if($("#appointmentID").val() != "")
    {
      $('#errorID').remove();
    }
    else
    {
      $errorNum++;
      if($errorNum == 1){
        $('.featherlight').click();
      }
      $noError = false;
      $element = $('<p class="error" id="errorID">A valid appointment ID has to be entered. </p>');
      $.featherlight($element);
    }

    //add status error
    if($("#requestedBy").val() != "")
    {
      $('#statusError').remove();
    }
    else
    {
      $errorNum++;
      if($errorNum == 1){
        $('.featherlight').click();
      }
      $noError = false;
      $element = $('<p class="error" id="statusError">You must enter who it is that wants to check the appointment status. </p>');
      $.featherlight($element);
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
        var obj =  $("<p>"+jsonReturned+"</p>");
        obj.html(obj.html().replace(/\n/g,'<br/>'));

        $('.featherlight').click();
        $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>"+obj.html()+"</span></h6>");

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
     $(document.body).on('click', '.approveBtn' ,function(){
            $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Approving Appointment</span> Please Wait...</h6>");
         });
        $(document.body).on('click', '.denyBtn' ,function(){
            $.featherlight("<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Denying Appointment</span> Please Wait...</h6>");
         });
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

  //Click deny
  $(document).on('click', '.deny', (function(e) {
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
  /***************************************************************************/
  /*****************************Home page**********************************/
  if(document.title == "COSBAS Service"){
    var returned;
    $date = moment().format('YYYY-MM-DD');
     $.ajax({
        type: "post",
        url: "/getMonthAppointments"
      }).then(function(jsonReturned) {

       returned =  jQuery.parseJSON(jsonReturned);
    
        /****************Callendar**************************/
        $('#calendar').fullCalendar({
            defaultDate: $date,
            editable: false,
            draggable: false,
            theme: true,
            eventLimit: true, // allow "more" link when too many events
            events: returned,
            viewRender: function(currentView){
              $('#loadingCalendar').remove();
              $(".fc-prev-button").hide();    
              $(".fc-next-button").hide();
              $(".fc-today-button").hide();
            },
            eventRender: function (event, element) {
              element.attr('href', 'javascript:void(0);');
              element.click(function() {
                  var htmlLightbox = "<h6 class=\"page-header wow fadeIn\" data-wow-delay=\".2s\"><span>Meeting</span> Details</h6><br/>"+
                                    "<p><b>Meeting Members:</b> "+event.withWho+"</p>" +
                                    "<p><b>Starting Time:</b> "+moment(event.start).format('MMM Do h:mm A')+"</p>"+
                                    "<p><b>Ending Time:</b> "+((moment(event.start).add(moment.duration(parseInt(event.duration), 'minutes')).format('MMM Do h:mm A'))) + " (" + event.duration + " minutes)"+"</p>";
                   htmlLightbox +=  '<br/><p class="text-left">' +
                                           '<button type="submit" id="calendarOk" class="btnLightbox btn-common">Return</button>' +
                                        '</p>';
                  $.featherlight(htmlLightbox,null);
                  /*$("#eventInfo").html(event.withWho);
                  $("#startTime").html(moment(event.start).format('MMM Do h:mm A'));
                  $("#endTime").html(((moment(event.start).add(moment.duration(parseInt(event.duration), 'minutes')).format('MMM Do h:mm A'))) + " (" + event.duration + " minutes)");
                  $("#eventContent").dialog({ modal: true, title: event.title, width:350});*/
                  return false;
              });
          }
          });
      });

       
      window.scrollTo(0, 0);
  }

  if(document.title == "COSBAS Service"){
      $.ajax({
        type: "post",
        url: "/calendarLinked"
      }).then(function(jsonReturned) {
        if(jsonReturned != "Linked")
        {
          var html = "<h8 class=\"page-header\">Choose your calendar </h8><br/><a href = \"/redirect\"><center><img width =\"50px\" src = \"http://2.bp.blogspot.com/-i4O7-MJJJmQ/VFkuulhnkQI/AAAAAAAB_ig/1H6mmPz4Dy8/s1600/calendar-logo.png \" Link Google Calendar</a></center>";
          $.featherlight(html);
        }
      });

     $.ajax({
        type: "post",
        url: "/getDayAppointments"
      }).then(function(jsonReturned) {
        $("#dayAppointments").append(jsonReturned);
      });
      window.scrollTo(0, 0);
  }
  /***************************************************************************/

  $(document).on('click', '#DenyAll', (function(e) {
    $('.deny').click();
    $("#fieldset").empty();
    $("#fieldset").append("<p>No appointments pending</p>");
    //window.location.reload(true);
  }));
});