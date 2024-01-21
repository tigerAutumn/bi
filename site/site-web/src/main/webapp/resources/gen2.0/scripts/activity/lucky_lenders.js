/**
 * Created by Administrator on 2018/5/8.
 */

$(function () {
    var countdown = $('#countdown').val();
    var participate = $('#participate').val();

    if (participate == 'notBegin' || participate == 'processing'){
        setTimeout(function () {
            location.reload();
        },countdown)
    }
});