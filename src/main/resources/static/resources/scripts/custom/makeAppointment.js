/*
* Sets up page
*/
$(document).ready(function() {
        initializeTimePicker();
        loadStaffMembers();

        $('.appointmentBy').focus(function() {
        createNameInput()
        });
        $('#email').focus(function() {
            createEmailInput();
        });
        handleDynamicInputClicks();
        validateGlobalSubmit();
});

/*
* Initializes start timer so the min time is 7:30 and max time is 16:00 and it doesn't show the duration
* Initializes end timer so the min time is 7:30 and max time is 16:00 and it shows the duration
* Links the end and start time so the duration is shown for the end time
* Sets up the dat picker so only future dates can be picked and no weekend dates can be picked
*/
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

/*
* Function to load registered staffmemers from the db and display them in the drop down
*/
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

/*
* Gets the current date in a YYYY-MM-DD format
*/
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

/*
* Gets the time in a HH:MM format
*/
function calculateTime()
{
    var currentdate = new Date();
    var timeVar;
    var time =  currentdate.getHours() + ":" + currentdate.getMinutes();
    timeVar = time;
    return timeVar;
}

/*
* Creates a lightbox with the number of attendees' name input fields
*/
function createNameInput()
{

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
}

/*
* Checks that no name was left out
*/
function validateNameInput()
{
        var emailString;
        $tempNames = [];
        $(document.body).on('click', '#namesubmit' ,function(){
               //check appointmentBy
               $allFilledIn = true;
               $inputs = $(".nameinput");
               for($i = 0; $i < $inputs.length; $i++){
                 if($($inputs[$i]).val() == "")
                 {
                   $allFilledIn = false;
                 }
                 else
                 {
                   if($i == $inputs.length-1 && $allFilledIn == true)
                   {
                     $("#madeByError").remove();
                   }
                   $tempNames[$i] = $($inputs[$i]).val();
                   var arr = $tempNames.join(", ");
                   $tempNames = arr;

                 }
                 $('.appointmentBy').val(arr);
               }
               if(!$allFilledIn)
               {
                    spawnErrorMessage("All members' names must be entered.");
               }

        });
}

/*
* Creates a lightbox with the number of attendees' email input fields
*/
function createEmailInput()
{

    validateEmailInput();
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

}

/*
* Closes lightbox if ok button is clicked
*/
function handleDynamicInputClicks()
{
    $(document.body).on('click', '#appointmentsubmit' ,function(){
                $('.featherlight').click();
                $('#appointmentWith').val($('#appointmentData').val());
            });
    $(document.body).on('click', '#calendarOk' ,function(){
            $('.featherlight').click();
        });
     $(document.body).on('click', '#emailErrorOkay' ,function(){
                 $('.featherlight').click();
             });
     $(document.body).on('click', '#nameErrorOkay' ,function(){
                   $('.featherlight').click();
               });

     $(document.body).on('click', '.btnLightbox' ,function(){
                        $('.featherlight').click();
                    });
}

/*
* Checks that no emial was left out
*/
function validateEmailInput()
{
    var emailString;
  $tempEmail = [];
  $(document.body).on('click', '#emailsubmit' ,function(){
     //check emails
     $allEmailsFilledIn = true;
     $noError = true;
     $inputs = $(".emailinput");

      for($i = 0; $i < $inputs.length; $i++){
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if($($inputs[$i]).val() == "" || !regex.test($($inputs[$i]).val()))
        {
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
      if($allEmailsFilledIn == false || $noError == false)
      {
        spawnErrorMessage("Invalid E-Mail/All E-Mails must be entered");
        return;
      }
      $tempEmail = $tempEmail.join(", ");
      $('.featherlight').click();
      $('.email').val($tempEmail);
  });
}


function checkTime()
{
    var dateVar = calculateDate();
    var timeVar = calculateTime();

    //check time
    if($("#requestedDateTime").val() == dateVar && $("#timeStart").val() <= timeVar)
    {
      spawnErrorMessage("The appointment must be in the future.");
      return false;
    }
    else
    {
      return true;
    }
}


function checkDateAndTime()
{
    //check date and time
        if($("#requestedDateTime").val() == "" || $("#timeStart").val() == "")
        {
          spawnErrorMessage("Please select a date and time.");
          return false;
        }
        else
        {
          return true;
        }
}

function checkDuration()
{
     //check duration
        if($("#timeEnd").val() == $("#timeStart").val())
        {
          spawnErrorMessage("The appointment duration must be at least 30 minutes.");
          return false;
        }
        else
        {
          $duration = ($('#appointmentDate').datepair('getTimeDiff') / 1000 / 60);
          return true;
        }
}

function checkReason()
{
      //check reason
        if($("#appointmentReason").val() == "")
        {
          spawnErrorMessage("A reason for the appointment must be given.");
          return false;
        } else
        {
          return true;
        }
}

function checkAppointmentWith()
{
     if($('#appointmentWith').val() == "")
        {
          spawnErrorMessage("Must make an appointment with someone.");
          return false;
        } else {
          return true;
        }
}


function checkEmail()
{
    if($("#email").val() == "")
    {
      spawnErrorMessage("Please fill in you/your team members' e-mail.");
      return false;
    } else
    {
      return true;
    }
}

function checkNames()
{
    if($("#appointmentBy").val() == "")
    {
      spawnErrorMessage("Please enter your team/team members' names.");
      return false;
    }
    else
    {
      return true;
    }
}
function validateGlobalSubmit()
{
    $('#makeAppointmentRequest').click(function(e)
    {
        e.preventDefault();

        if($("#numMembers").val() >= 1)
        {
          $('#errorNumbers').remove();
        }

        //Check for errors
        $noError = true;

        if(checkAppointmentWith() && checkTime() && checkDateAndTime() && checkDuration() && checkReason()  &&  checkNames() && checkEmail())
        {
            spawnBusyMessage("Requesting Appointment");
            requestAppointment();
        }
    })

}

function requestAppointment()
{
    //send data if no errors
       var appointmentWith = $('#appointmentWith').val();
       var requestedDateTime = ($("#requestedDateTime").val()+"T"+$("#timeStart").val()+":30+02:00");
       var appointmentBy = $tempNames;
      $.ajax({
        type: "post",
        data: {"appointmentWith" : appointmentWith,
               "requestedDateTime" : requestedDateTime,
               "appointmentBy" : appointmentBy,
               "appointmentDuration" : $duration,
               "appointmentReason" : $('#appointmentReason').val(),
               "appointmentEmails" : $tempEmail},
        url: "/requestAppointment"
      }). then(function(jsonReturned) {
            $('.featherlight').click();

          $("#signIn").text(jsonReturned);
          if(jsonReturned != "Time not available"){

            spawnSuccessMessage("Requested Appointment Successfully! You will receive an e-mail when the staff member approves the appointment.")
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

            //var html = "<h6 class=\"section-title wow fadeIn\" data-wow-delay=\".2s\"><p>"+jsonReturned+"!</p><br/></h6>";
            $.ajax({
              type: "post",
              data: {"staffID": $("#appointmentWith").val()},
              url: "/dayAvailable"
            }). then(function(jsonReturned) {
               /* var html = "<h6 class=\"section-title wow fadeIn\" data-wow-delay=\".2s\"><p>Time Not Available!</p></h6>";//<p>The Following Times Are Unavailable</p></h6>"+ jsonReturned;
                                        html +=  '<p class="text-left">' +
                                                    '<button type="submit" id="" class="btnLightbox btn-common">Okay</button>' +
                                                  '</p>';
                 $.featherlight(html);*/

                //spawnErrorMessage("Time Unavailable - The following times are unavailable:", jsonReturned);
                spawnErrorMessage("Time Unavailable", null);
            });

            //$.featherlight(html);
          }
      window.scrollTo(0, 0);
     });

}