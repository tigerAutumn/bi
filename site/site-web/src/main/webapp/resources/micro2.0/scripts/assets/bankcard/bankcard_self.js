/**
 * Created by Icker on 2016/9/1.
 */
$(function () {
    var global = {
        root_url: $('#APP_ROOT_PATH').val(),
        bind_card_index_url: $('#APP_ROOT_PATH').val() + '/micro2.0/bankcard/index?entry=self',
        qianbao: $('#qianbao').val()
    };

    $(".cardclick").on('click', function () {
        if(global.qianbao) {
            location.href = global.bind_card_index_url + '&qianbao=qianbao';
        } else {
            location.href = global.bind_card_index_url;
        }
    });
});
