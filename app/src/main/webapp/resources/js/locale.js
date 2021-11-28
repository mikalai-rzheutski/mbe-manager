$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != '') {
            var queryParams = new URLSearchParams(location.search);
            queryParams.set("lang", selectedOption);
            window.location.replace(location.protocol + '//' + location.host + location.pathname + "?" +queryParams.toString());
        }
    });
});