/**
 * 平台数据由于数据量过多，采取异步加载形式查询数据
 * Created by cyb on 2018/1/24.
 */
var global = {
    base_url: $('#APP_ROOT_PATH_GEN').val() + '/gen178/platform/',
    url_1: 1, // 累计出借额（平台自成立以来出借的金额总和，仅统计本金）
    url_2: 2, // 自成立以来累计借贷金额（平台成立以来出借的金额总和，仅统计本金）
    url_3: 3, // 自成立以来累计借贷笔数（平台成立以来出借的总笔数）
    url_4: 4, // 当前待还借贷金额（借款人未还款的出借金额总和，仅统计本金）
    url_5: 5, // 当前待还借贷笔数（借款人未还款的出借总笔数）
    url_6: 6, // 累计出借人数（平台成立以来，累计的出借总人数，排除VIP理财人）
    url_7: 7, // 当期出借人数（目前有待回款的出借人数，排除VIP理财人）
    url_8: 8, // 前十大出借人出借余额占比（按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人）
    url_9: 9, // 最大单一出借人出借余额占比（按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人）
    url_10: 10, // 累计借款人数（平台成立以来，累计的借款人数）
    url_11: 11, // 当期借款人数（目前处于借款状态的借款人数）
    url_12: 12, // 前十大借款人待还金额占比（按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金）
    url_13: 13, // 最大单一借款人待还金额占比（按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金）
    url_14: 14, // 借款人逾期金额（当前对投资人已经处于逾期状态的所有借款的金额总和，仅统计本金）
    url_15: 15, // 借款人逾期笔数（当前对投资人处于逾期状态的所有借款的笔数之和）
    url_16: 16, // 借款人逾期90天以上金额（当前对投资人逾期＞90天的借款金额总和，仅统计本金）
    url_17: 17, // 借款人逾期90天以上笔数（当前对投资人逾期＞90天的借款笔数之和）
    url_18: 18, // 累计代偿金额（平台自成立以来，累计的代偿金额，包括本金和利息）
    url_19: 19, // 累计代偿笔数（平台自成立以来，累计代偿的笔数之和）
    operate_start_time: new Date(Date.parse('2015-03-17'.replace(/-/g, "/"))), //平台开始运营时间
    now: new Date(), //当前时间
    currTime: $('#currTime').val() //统计截止时间
};
var pageDta = {
    url_num2: "",//累计借贷金额
    url_num6: "",//累计出借人数
    url_num8: "",//前十大出借人出借余额总和
    url_num4: "",//当前待还借贷金额
    url_num9: "",//最大单一出借人出借余额总和
    url_num10: "",//累计借款人数
    url_num12: "",//前十大借款人待还金额总和
    url_num13: ""//最大单一借款人待还金额总和
};
var IsOK2=false,
    IsOK6=false,
    IsOK4=false,
    IsOK8=false,
    IsOK9=false,
    IsOK10=false,
    IsOK12=false,
    IsOK13=false;

// 统计截止时间数据处理
function currTime(time) {
    if(time){
        //console.log(time);
        var currTime_year = time.substr(0 ,4);
        var currTime_month = time.substr(5,2);
        var currTime_day = time.substr(8,2);
        var currTime_hour_left = time.substr(11,1);
        var currTime_hour_right = time.substr(12,1);
        var currTime_minute_left = time.substr(14,1);
        var currTime_minute_right = time.substr(15,1);
        var currTime_second_left = time.substr(17,1);
        var currTime_second_right = time.substr(18,1);
        $('.currtime_year').html(currTime_year);
        $('.currtime_month').html(currTime_month);
        $('.currtime_day').html(currTime_day);
        $('.currtime_hour_left').html(currTime_hour_left);
        $('.currtime_hour_right').html(currTime_hour_right);
        $('.currtime_minute_left').html(currTime_minute_left);
        $('.currtime_minute_right').html(currTime_minute_right);
        $('.currtime_second_left').html(currTime_second_left);
        $('.currtime_second_right').html(currTime_second_right);
    }
}
currTime(global.currTime);
/*

// 运营天数
function operate_date() {
    var ts = global.now - global.operate_start_time;//计算剩余的毫秒数
    var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数
    $("#operate_days").text(dd);
}
operate_date();
function httpPost(url, params, success_callback, success_data, error_callback, error_data) {
    $.ajax({
        url: url,
        data: params,
        type: 'post',
        success: function (data) {
            success_callback(data, success_data);
        },
        error: function () {
            error_callback(error_data);
        }
    })
}

function success_callback(data, success_data) {
    var value;
    if(data.amount == null || data.amount == undefined) {
        value = data.times;
    } else {
        value = data.amount;
    }
    switch (success_data) {
        case global.url_1:// 累计出借额（平台自成立以来出借的金额总和，仅统计本金）
            console.log(global.url_1 + ': ' + value);
            $("#lend_total").text(MoneyUtil.formatMoney(value,2,",","."));
            pageDta.url_num2 = parseFloat(value);
            IsOK2=true;
            break;
        case global.url_2:// 自成立以来累计借贷金额（平台成立以来出借的金额总和，仅统计本金）
            console.log(global.url_2 + ': ' + MoneyUtil.formatMoney(value,2,",","."));
            $("#lend_amount").text(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_3:// 自成立以来累计借贷笔数（平台成立以来出借的总笔数）
            console.log(global.url_3 + ': ' + value);
            $("#lend_Num").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_4: // 当前待还借贷金额（借款人未还款的出借金额总和，仅统计本金）
            console.log(global.url_4 + ': ' + value);
            $("#stayPayBack_amount").text(MoneyUtil.formatMoney(value,2,",","."));
            pageDta.url_num4 = parseFloat(value);
            IsOK4=true;
            break;
        case global.url_5:// 当前待还借贷笔数（借款人未还款的出借总笔数）
            console.log(global.url_5 + ': ' + value);
            $("#stayPayBack_Num").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_6:// 累计出借人数（平台成立以来，累计的出借总人数，排除VIP理财人）
            console.log(global.url_6 + ': ' + value);
            $("#lend_person_past").text(MoneyUtil.formatMoney(value,0,",",""));
            pageDta.url_num6 = parseFloat(value);
            IsOK6=true;
            break;
        case global.url_7: // 当期出借人数（目前有待回款的出借人数，排除VIP理财人）
            console.log(global.url_7 + ': ' + value);
            $("#lend_person_now").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_8:// 前十大出借人出借余额占比（按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人）
            console.log(global.url_8 + ': ' + value);
            $("#lend_tenth_total").val(value);
            pageDta.url_num8 = parseFloat(value);
            IsOK8=true;
            break;
        case global.url_9:// 最大单一出借人出借余额占比（按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人）
            console.log(global.url_9 + ': ' + value);
            $("#lend_largest_total").val(value);
            pageDta.url_num9 = parseFloat(value);
            IsOK9=true;
            break;
        case global.url_10: // 累计借款人数（平台成立以来，累计的借款人数）
            console.log(global.url_10 + ': ' + value);
            $("#loan_person_past").text(MoneyUtil.formatMoney(value,0,",",""));
            pageDta.url_num10 = parseFloat(value);
            IsOK10=true;
            break;
        case global.url_11:// 当期借款人数（目前处于借款状态的借款人数）
            console.log(global.url_11 + ': ' + value);
            $("#loan_person_now").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_12: // 前十大借款人待还金额占比（按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金）
            console.log(global.url_12 + ': ' + value);
            $("#loan_tenth_total").val(value);
            pageDta.url_num12 = parseFloat(value);
            IsOK12=true;
            break;
        case global.url_13:// 最大单一借款人待还金额占比（按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金）
            console.log(global.url_13 + ': ' + value);
            $("#loan_largest_total").val(value);
            pageDta.url_num13 = parseFloat(value);
            IsOK13=true;
            break;
        case global.url_14:// 借款人逾期金额（当前对投资人已经处于逾期状态的所有借款的金额总和，仅统计本金）
            console.log(global.url_14 + ': ' + value);
            $("#overdue_amount").text(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_15:// 借款人逾期笔数（当前对投资人处于逾期状态的所有借款的笔数之和）
            console.log(global.url_15 + ': ' + value);
            $("#overdue_Num").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_16:// 借款人逾期90天以上金额（当前对投资人逾期＞90天的借款金额总和，仅统计本金）
            console.log(global.url_16 + ': ' + value);
            $("#overdue_amount_90").text(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_17:// 借款人逾期90天以上笔数（当前对投资人逾期＞90天的借款笔数之和）
            console.log(global.url_17 + ': ' + value);
            $("#overdue_Num_90").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_18:// 累计代偿金额（平台自成立以来，累计的代偿金额，包括本金和利息）
            console.log(global.url_18 + ': ' + value);
            $("#compensatory_total").text(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_19:// 累计代偿笔数（平台自成立以来，累计代偿的笔数之和）
            console.log(global.url_19 + ': ' + value);
            $("#compensatory_Num").text(MoneyUtil.formatMoney(value,0,",",""));
            break;
    }
    de();
}

function error_callback (error_data) {
    switch (error_data) {
        case global.url_1:
            console.log(global.url_1 + ': error.' );
            break;
        case global.url_2:
            console.log(global.url_2 + ': error.');
            break;
        case global.url_3:
            console.log(global.url_3 + ': error.');
            break;
        case global.url_4:
            console.log(global.url_4 + ': error.');
            break;
        case global.url_5:
            console.log(global.url_5 + ': error.');
            break;
        case global.url_6:
            console.log(global.url_6 + ': error.');
            break;
        case global.url_7:
            console.log(global.url_7 + ': error.');
            break;
        case global.url_8:
            console.log(global.url_8 + ': error.');
            break;
        case global.url_9:
            console.log(global.url_9 + ': error.');
            break;
        case global.url_10:
            console.log(global.url_10 + ': error.');
            break;
        case global.url_11:
            console.log(global.url_11 + ': error.');
            break;
        case global.url_12:
            console.log(global.url_12 + ': error.');
            break;
        case global.url_13:
            console.log(global.url_13 + ': error.');
            break;
        case global.url_14:
            console.log(global.url_14 + ': error.');
            break;
        case global.url_15:
            console.log(global.url_15 + ': error.');
            break;
        case global.url_16:
            console.log(global.url_16 + ': error.');
            break;
        case global.url_17:
            console.log(global.url_17 + ': error.');
            break;
        case global.url_18:
            console.log(global.url_18 + ': error.');
            break;
        case global.url_19:
            console.log(global.url_19 + ': error.');
            break;
    }
}

$(function() {
    httpPost(global.base_url + global.url_1, null, success_callback, global.url_1, error_callback, global.url_1);
    httpPost(global.base_url + global.url_2, null, success_callback, global.url_2, error_callback, global.url_2);
    httpPost(global.base_url + global.url_3, null, success_callback, global.url_3, error_callback, global.url_3);
    httpPost(global.base_url + global.url_4, null, success_callback, global.url_4, error_callback, global.url_4);
    httpPost(global.base_url + global.url_5, null, success_callback, global.url_5, error_callback, global.url_5);
    httpPost(global.base_url + global.url_6, null, success_callback, global.url_6, error_callback, global.url_6);
    httpPost(global.base_url + global.url_7, null, success_callback, global.url_7, error_callback, global.url_7);
    httpPost(global.base_url + global.url_8, null, success_callback, global.url_8, error_callback, global.url_8);
    httpPost(global.base_url + global.url_9, null, success_callback, global.url_9, error_callback, global.url_9);
    httpPost(global.base_url + global.url_10, null, success_callback, global.url_10, error_callback, global.url_10);
    httpPost(global.base_url + global.url_11, null, success_callback, global.url_11, error_callback, global.url_11);
    httpPost(global.base_url + global.url_12, null, success_callback, global.url_12, error_callback, global.url_12);
    httpPost(global.base_url + global.url_13, null, success_callback, global.url_13, error_callback, global.url_13);
    httpPost(global.base_url + global.url_14, null, success_callback, global.url_14, error_callback, global.url_14);
    httpPost(global.base_url + global.url_15, null, success_callback, global.url_15, error_callback, global.url_15);
    httpPost(global.base_url + global.url_16, null, success_callback, global.url_16, error_callback, global.url_16);
    httpPost(global.base_url + global.url_17, null, success_callback, global.url_17, error_callback, global.url_17);
    httpPost(global.base_url + global.url_18, null, success_callback, global.url_18, error_callback, global.url_18);
    httpPost(global.base_url + global.url_19, null, success_callback, global.url_19, error_callback, global.url_19);
});




function de(){
    if(IsOK2==true&&IsOK6==true){
        //人均累计出借金额=累计借贷金额/累计出借人数，仅统计本金
        var num1=parseFloat(pageDta.url_num2/pageDta.url_num6).toFixed(2);
        if(parseFloat(num1) == 0) {
            $("#lend_per_amount").html("0.01");
        } else {
            $("#lend_per_amount").html(MoneyUtil.formatMoney(num1,2,",","."));
        }
    }
    if(IsOK8==true&&IsOK4==true){
        //前十大出借人出借余额占比=按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金
        var num2=parseFloat(pageDta.url_num8/pageDta.url_num4*100).toFixed(2);
        if(parseFloat(num2) == 0) {
            $("#lend_tenth_proportion").html("0.01%");
        } else {
            $("#lend_tenth_proportion").html(num2 +"%");
        }
    }
    if(IsOK9==true&&IsOK4==true){
        //最大单一出借人出借余额占比=按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金
        var num3=parseFloat(pageDta.url_num9/pageDta.url_num4*100).toFixed(2);
        if(parseFloat(num3) == 0) {
            $("#lend_largest_proportion").html("0.01%")
        } else {
            $("#lend_largest_proportion").html(num3+"%")
        }
    }
    if(IsOK2==true&&IsOK10==true){
        //人均累计借款金额=累计借贷金额/累计借款人数，仅统计本金
        var num4=parseFloat(pageDta.url_num2/pageDta.url_num10).toFixed(2);
        if(parseFloat(num4) == 0) {
            $("#loan_per_amount").html("0.01");
        } else {
            $("#loan_per_amount").html(MoneyUtil.formatMoney(num4,2,",","."));
        }
    }
    if(IsOK12==true&&IsOK4==true){
        //前十大借款人待还金额占比=按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金
        var num5=parseFloat(pageDta.url_num12/pageDta.url_num4*100).toFixed(2);
        if(parseFloat(num5) == 0) {
            $("#loan_tenth_proportion").html("0.01%")
        } else {
            $("#loan_tenth_proportion").html(num5+"%")
        }
    }
    if(IsOK13==true&&IsOK4==true){
        //最大单一借款人待还金额占比=按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金
        var num6=parseFloat(pageDta.url_num13/pageDta.url_num4*100).toFixed(2);
        if(parseFloat(num6) == 0) {
            $("#loan_largest_proportion").html("0.01%")
        } else {
            $("#loan_largest_proportion").html(num6+"%")
        }
    }
}

*/



