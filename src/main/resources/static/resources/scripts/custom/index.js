$(document).ready(function() {

  /*
  * Get the month appointments from the calendar and adds the events to the calendar
  */
   if(document.title == "COSBAS Service"){
       var returned;
       $date = moment().format('YYYY-MM-DD');
        $.ajax({
           type: "post",
           url: "/getMonthAppointments"
         }).then(function(jsonReturned) {

          returned =  jQuery.parseJSON(jsonReturned);
          spawnCalendar(returned);

     });


    //Checks if a calendar has already been linked or not
     checkCalendarLinked();
     handleDynamicInputClicks();


     $('#todayAppointBtn').on("click", function(e)
     {
        e.preventDefault();
        var html = $('#dayAppointments').html();
         $.featherlight(html);
     });

    }
});

/*
* Sets up calendar
* Hides previous, next and today buttons
* No events are editable or draggable
* Sets date to the current day
*/
function spawnCalendar(returned)
{
     /****************Callendar**************************/
   $('#calendar').fullCalendar({
    header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
       defaultDate: $date,
       editable: false,
       draggable: false,
       theme: true,
       eventLimit: true, // allow "more" link when too many events
       events: returned,
        defaultView: 'month',
       viewRender: function(currentView){
         $('#loadingCalendar').remove();
         /*$(".fc-prev-button").hide();
         $(".fc-next-button").hide();
         $(".fc-today-button").hide();
         $(".fc-month-button").hide();
         $(".fc-agendaWeek-button").hide();
         $(".fc-agendaDay-button").hide();*/
       },
       eventRender: function (event, element) {
         element.attr('href', 'javascript:void(0);');
         element.click(function() {
             var htmlLightbox = "<h2 class=\"wow fadeIn\" data-wow-delay=\".2s\"><span>Meeting</span> Details</h2><br/>"+
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



 window.scrollTo(0, 0);
}

/*
* Calls server function to see if a calendar was already linked if not it shows a dialog letting the user link a calendar
* Gets the day's appointments add appends them to the html
*/
function checkCalendarLinked()
{
    if(document.title == "COSBAS Service"){
         $.ajax({
           type: "post",
           url: "/calendarLinked"
         }).then(function(jsonReturned) {
           if(jsonReturned != "Linked")
           {
             var html = "<h6 class=\"section-title\">Choose your calendar </h6><br/><a href = \"/redirect\"><center><img width =\"50px\" src = \"http://2.bp.blogspot.com/-i4O7-MJJJmQ/VFkuulhnkQI/AAAAAAAB_ig/1H6mmPz4Dy8/s1600/calendar-logo.png \" Link Google Calendar</a></center>";
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
}

function handleDynamicInputClicks()
{

     $(document.body).on('click', '.btnLightbox' ,function(){
                        $('.featherlight').click();
                    });
}
