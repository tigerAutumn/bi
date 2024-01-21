$(function () {
    // ============================ 全局数据 ====================================
    var global = {
        root_url: $('#APP_ROOT_PATH').val(),
        bind_index_url: $('#APP_ROOT_PATH').val() + '/micro2.0/bankcard/index'
    };

    // ============================ 初始化数据 ====================================
    function dataInit() {
        $('.oneTop').each(function() {
            var oneTop = parseFloat($(this).attr('oneTop'));
            $(this).text(oneTop / 10000);
        });
        $('.dayTop').each(function() {
            var dayTop = parseFloat($(this).attr('dayTop'));
            $(this).text(dayTop / 10000);
        })
    }
    dataInit();

    // ============================ 事件绑定 ====================================
    $('.bank_list').on('click', function () {
        $('#bankId').val($(this).attr('bank_id'));
        $('#bankName').val($(this).attr('bank_name'));
        $('#oneTop').val(parseFloat($(this).attr('oneTop')) / 10000);
        $('#dayTop').val(parseFloat($(this).attr('dayTop')) / 10000);
        $('#notice').val($(this).attr('notice'));
        $('#go_bind_card').attr('action', global.bind_index_url);
        $('#go_bind_card').submit();
    });
});