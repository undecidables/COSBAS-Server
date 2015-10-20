$(document).ready(function() {
        initializeTimePicker();
        loadStaffMembers();

        createNameInput()
        createEmailInput();
        handleDynamicInputClicks();
        validateGlobalSubmit();
});


function initializeTimePicker()
{
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
    $('#appointmentDate').datepair();
}

function loadStaffMembers()
{
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
}

function calculateDate()
{
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

      return dateVar;
}

function calculateTime()
{
    var currentdate = new Date();
    var timeVar;
    var time =  currentdate.getHours() + ":" + currentdate.getMinutes();
    timeVar = time;
    return timeVar;
}

function createNameInput()
{
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
    validateNameInput();
  });
}

function validateNameInput()
{
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
}

function createEmailInput()
{
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
    validateEmailInput();
  });
}

function handleDynamicInputClicks()
{
    $(document.body).on('click', '#appointmentsubmit' ,function(){
                $('.featherlight').click();
                $('#appointmentWith').val($('#appointmentData').val());
            });
    $(document.body).on('click', '#calendarOk' ,function(){
            $('.featherlight').click();
        });
}

function validateEmailInput()
{
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
}


function validateGlobalSubmit()
{
    $('#makeAppointmentRequest').click(function(e) {
    e.preventDefault();

    if($("#numMembers").val() >= 1)
    {
      $('#errorNumbers').remove();
    }
    var dateVar = calculateDate();
    var timeVar = calculateTime();
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
              data: {"staffID": $("#appointmentWith").val()},
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
}