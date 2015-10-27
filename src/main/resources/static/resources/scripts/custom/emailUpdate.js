$(document).ready(function() {

    $('#updateEmail').click(function (event) {
        event.preventDefault();
        updateEmail();
    });

    $(document.body).on('click', '#emailUpdateSubmit', function () {
        //do stuff to process the email update.
        var newEmail = $('#emailUpdate').val();
        $('.featherlight').click();

        $.ajax({
            type: "post",
            data: {"email": newEmail},
            url: "/updateEmail"
        }).then(function (jsonReturned) {
            if(jsonReturned == 'true')
            {
                spawnSuccessMessage("Successfully updated your email address.");
            }
            else
            {
                spawnErrorMessage("An error occured updating your email address, please try again later or contact your administrator.");
            }
        });

    });
});


function updateEmail()
{
    var output = '';
    output += '<p class="text-left">Your new email address:</p><input class="form-control email emailinput" type="email" id="emailUpdate" name="emailUpdate"/>';
    output += '<br/>';
    output +=  '<p class="text-left"><button type="submit" id="emailUpdateSubmit" class="btnLightbox btn-common">Submit email update</button></p>';
    $.featherlight(output,null);
    $('#emailUpdate').focus();
    return;
}

