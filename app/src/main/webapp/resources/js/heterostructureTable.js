// code to search and scroll

$(document).ready(function () {
    $("#search").keyup(function () {
        _this = this;
        var stringToSearch = $(_this).val().toLowerCase().trim();
        var firstRow = true;
        $.each($("#tableOfHeterostructures tbody tr"), function () {
            if (($(this).text().toLowerCase().indexOf(stringToSearch) === -1) | (!stringToSearch)) {
                $(this).removeClass("table-success");
            } else {
                $(this).addClass("table-success");
                if (firstRow) {
                    var positionOfRowTop = $(this).position().top;
                    var positionOfTableTop = $('#tableBody').position().top;
                    $('.tableFixHead').scrollTop(positionOfRowTop - positionOfTableTop);
                    firstRow = false;
                } 
            }
        });
    });
});

/*$(window).on('load', function () {
    var tableHeadBottom = $('.tableHead').offset().top + $('.tableHead').outerHeight(true);
    var footerTop = $('footer').offset().top;
    var padding = $('#content').css('padding-bottom').replace("px", "");
    var height = footerTop - tableHeadBottom - 2 * padding;
    $('.main-window-table').outerHeight(height, true);
});*/

$(window).on('load', function () {
    var tableHeight = $('footer').offset().top - $('.tableFixHead').offset().top;
    $('.tableFixHead').outerHeight(tableHeight, true);
});

function clearSearch() {
    $("#search").val("");
    $("#search").keyup();
    $('.tableFixHead').scrollTop(0);
  }

