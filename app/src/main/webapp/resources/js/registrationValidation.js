/*
$('form').on('submit', function(e){
    e.preventDefault();
    var em = $("#e-mail").val();
    var pw = $("#password").val();
    if ((em == '') && (pw == '')){
        alert('error');
    } else {
        alert('done')
    }
});
*/

jQuery(function ($) {
    var $inputs = $('input[name=password],input[name=email]');
    $inputs.on('input', function () {
        var total = $('input[name=password]').val().length + $('input[name=email]').val().length;
        $inputs.not(this).prop('required', !total);

    });
});
