$(function(){
    (function(){
        var userId = $('#userId').val();
        //去掉dwz的loading
        $.ajaxSettings.global=false;
        //1、身份证归属地
        $.ajax({
            url: $('#APP_ROOT_PATH').val() + '/channelWithdraw/auditWithdrawDetailInfo.htm',
            type: 'post',
            dataType: 'json',
            data: {
                userId: userId
            },
            success: function (data) {
                $('.id_cardAttribution').text(data.idCardAttribution);
            }
        });
        //2、注册手机归属地
        $.ajax({
            url: $('#APP_ROOT_PATH').val() + '/channelWithdraw/auditWithdrawDetailRegMobile.htm',
            type: 'post',
            dataType: 'json',
            data: {
                userId: userId
            },
            success: function (data) {
                $('.registered_mobileAttribution').text(data.registeredMobileAttribution);
            }
        });
        //3、预留手机归属地
        $.ajax({
            url: $('#APP_ROOT_PATH').val() + '/channelWithdraw/auditWithdrawDetailResMobile.htm',
            type: 'post',
            dataType: 'json',
            data: {
                userId: userId
            },
            success: function (data) {
                $('.reserved_mobileAttribution').text(data.reservedMobileAttribution);
            }
        });
        //4、银行卡归属地
        $.ajax({
            url: $('#APP_ROOT_PATH').val() + '/channelWithdraw/auditWithdrawDetailCardNo.htm',
            type: 'post',
            dataType: 'json',
            data: {
                userId: userId
            },
            success: function (data) {
                $('.cardNo_attribution').text(data.cardNoAttribution);
            }
        });
    })();
});
