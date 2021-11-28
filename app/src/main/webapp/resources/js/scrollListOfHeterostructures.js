// code to search and scroll

$(document).ready(function () {
    $(".name").keyup(function () {
        _this = this;
        var stringToSearch = $(_this).val().toLowerCase().trim();
        $.each($("#tableOfHeterostructures tbody tr"), function () {
            if (($(this).text().toLowerCase().indexOf(stringToSearch) === -1) | (!stringToSearch)) {
                $(this).removeClass("active");
            } else {
                $(this).addClass("active");
                var positionOfRowTop = $(this).position().top;
                var positionOfTableTop = $('#tableOfHeterostructures').position().top;
                $('.main-window-table').scrollTop(positionOfRowTop - positionOfTableTop);
            }
        });
    });
});

$("#closeFilter").click(function () {
    $(".name").val("");
    $(".name").keyup();
});

$(window).on('load', function () {
    var tableHeadBottom = $('.tableHead').offset().top + $('.tableHead').outerHeight(true);
    var footerTop = $('footer').offset().top;
    var padding = $('#content').css('padding-bottom').replace("px", "");
    var height = footerTop - tableHeadBottom - 2 * padding;
    $('.main-window-table').outerHeight(height, true);
});