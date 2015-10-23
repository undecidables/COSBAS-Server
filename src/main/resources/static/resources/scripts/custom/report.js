
$(document).ready(function() {
    $( '#createReport' ).click(function( event ) {
        event.preventDefault();
    });
    initializeTimePicker();
    loadStaffMembers();
    loadReportTypes();
    loadReportFormats();
    handleDynamicInputClicks();
    hideInputs();
    getReport();
});

/*
 * This function will be called onload to hide all input forms, once the user has selected the report she/he want they the necessary inputs will appear
 */

function hideInputs()
{
    $("#staffIDcontainer").hide();
    $("#dateContainer").hide();
    $("#statusContainer").hide();
}

function initializeTimePicker()
{

    $('#report .date').datepicker({
        'beforeShowDay': $.datepicker.noWeekends,
        'format': 'mm/dd/yyyy',
        'autoclose': true,
        'dateFormat': 'yy-mm-dd',

    });
    $('#report').datepair();
}


function loadStaffMembers()
{
    $("#staffID").click(function(){
        $("#staffID").prop('disabled', true);
        $("#staffID").val("Loading Staff Member List...");
        $.ajax({
            type: "post",
            url: "/getListOfUsers"
        }).then(function(jsonReturned) {

            var output = '';
            output+= '<select class="contact_input" id="staffList" name="staffList">';
            output+= jsonReturned;
            output+= '</select>';
            output+= '<button type="submit" id="submitStaffId" class="btnLightbox btn-common">Select Staff Member</button>';


            $.featherlight(output,null);
            $("#staffID").prop('disabled', false);
        });
        window.scrollTo(0, 0);
    });
}

function loadReportTypes()
{
    $("#reportType").click(function(){
        $("#reportType").prop('disabled', true);
        $("#reportType").val("Loading Report Types...");
        $.ajax({
            type: "post",
            url: "/getListOfReports"
        }).then(function(jsonReturned) {

            var output = '';
            output+= '<select class="contact_input" id="reportTypes" name="reportTypes">';
            output+= jsonReturned;
            output+= '</select>';
            output+= '<button type="submit" id="submitReportType" class="btnLightbox btn-common">Select Report Type</button>';


            $.featherlight(output,null);
            $("#reportType").prop('disabled', false);
        });
        window.scrollTo(0, 0);
    });
}

function loadReportFormats()
{
    $("#reportFormat").click(function(){
        $("#reportFormat").prop('disabled', true);
        $("#reportFormat").val("Loading Report Types...");
        $.ajax({
            type: "post",
            url: "/getListOfFormats"
        }).then(function(jsonReturned) {

            var output = '';
            output+= '<select class="contact_input" id="reportFormats" name="reportFormats">';
            output+= jsonReturned;
            output+= '</select>';
            output+= '<button type="submit" id="submitReportFormat" class="btnLightbox btn-common">Select Report Format</button>';


            $.featherlight(output,null);
            $("#reportFormat").prop('disabled', false);
        });
        window.scrollTo(0, 0);
    });
}

function showInputs()
{
    var selection = $("#reportType").val();

    switch (selection)
    {
        case "ALL_APPOINTMENTS":
            break;
        case "ALL_APPOINTMENTS_BY_STAFFID":
            $("#staffIDcontainer").show();
            break;
        case "ALL_APPOINTMENTS_BY_STATUS":
            $("#statusContainer").show();
            break;
        case "ALL_APPOINTMENTS_BETWEEN_DATETIME":
            $("#dateContainer").show();
            break;
        case "ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME":
            $("#staffIDcontainer").show();
            $("#dateContainer").show();
            break;
        case "ALL_ACCESS_RECORDS":
            break;
        case "ALL_ACCESS_RECORDS_BETWEEN_DATETIME":
            $("#dateContainer").show();
            break;
        case "ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME":
            $("#staffIDcontainer").show();
            $("#dateContainer").show();
            break;
        case "ALL_ACCESS_RECORDS_BY_STAFFID":
            $("#staffIDcontainer").show();
            break;
        default:
            break;

    }
}

function handleDynamicInputClicks()
{
    $(document.body).on('click', '#submitReportType' ,function(){

        $('#reportType').val($('#reportTypes').val());
        hideInputs();
        showInputs();
        $('.featherlight').click();
    });
    $(document.body).on('click', '#submitStaffId' ,function(){
        $('#staffID').val($('#staffList').val());
        $('.featherlight').click();
    });
    $(document.body).on('click', '#submitReportFormat' ,function(){
        $('#reportFormat').val($('#reportFormats').val());
        $('.featherlight').click();
    });
}

function getReport()
{
    $("#createReport").click(function(){
        var selection = $("#reportType").val();
        var data;
        var url = "";
        switch (selection)
        {
            case "ALL_APPOINTMENTS":
                data = {"format" : $('#reportFormat').val()};
                url = "/createAllAppointmentsReport";
                break;
            case "ALL_APPOINTMENTS_BY_STAFFID":
                data = {"format" : $('#reportFormat').val(),
                    "staffID":$('#staffID').val()
                };
                url = "/createAllAppointmentsByStaffIdReports";
                break;
            case "ALL_APPOINTMENTS_BY_STATUS":
                data = {"format" : $('#reportFormat').val(),
                    "status":$('#status').val()
                };
                url = "/createAllAppointmentsByStatusReports";
                break;
            case "ALL_APPOINTMENTS_BETWEEN_DATETIME":
                data = {"format" : $('#reportFormat').val(),
                    "dateTimeS":$('#dateTimeS').val(),
                    "dateTimeE":$('#dateTimeE').val()
                };
                url = "/createAllAppointmentsBetweenDateTimeReports";
                break;
            case "ALL_APPOINTMENTS_BY_STAFFID_AND_BETWEEN_DATETIME":
                data = {"format" : $('#reportFormat').val(),
                    "dateTimeS":$('#dateTimeS').val(),
                    "dateTimeE":$('#dateTimeE').val(),
                    "staffID":$('#staffID').val()
                };
                url = "/createAllAppointmentsByStaffIdAndDateTimeBetweenReports";
                break;
            case "ALL_ACCESS_RECORDS":
                url = "/createAllAccessRecordReports";
                data = {"format" : $('#reportFormat').val()};
                break;
            case "ALL_ACCESS_RECORDS_BETWEEN_DATETIME":
                data = {"format" : $('#reportFormat').val(),
                    "dateTimeS":$('#dateTimeS').val(),
                    "dateTimeE":$('#dateTimeE').val()
                };
                url = "/createAccessRecordBetweenDateTimeReports";
                break;
            case "ALL_ACCESS_RECORDS_BY_STAFFID_AND_BETWEEN_DATETIME":
                data = {"format" : $('#reportFormat').val(),
                    "dateTimeS":$('#dateTimeS').val(),
                    "dateTimeE":$('#dateTimeE').val(),
                    "staffID":$('#staffID').val()
                };
                url = "/createAccessRecordByStaffIdAndBetweenDateTimeReports";
                break;
            case "ALL_ACCESS_RECORDS_BY_STAFFID":
                data = {"format" : $('#reportFormat').val(),
                    "staffID":$('#staffID').val()
                };
                url = "/createAccessRecordByUserIdReports";
                break;
            default:
                break;

        }

        open('POST', url, data, '_blank');
    });
}

function open(verb, url, data, target)
{
    var form = document.createElement("form");
    form.action = url;
    form.method = verb;
    form.target = target || "_sefl";
    if(data)
    {
        for(var key in data)
        {
            var input = document.createElement("textarea");
            input.name = key;
            input.value = typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
            form.appendChild(input);
        }
    }
    form.style.display = 'none';
    document.body.appendChild(form);
    form.submit();
}