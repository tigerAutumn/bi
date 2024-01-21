/**
 * 平台数据由于数据量过多，采取异步加载形式查询数据
 * Created by cyb on 2018/1/24.
 */
var global = {
    base_url: $('#APP_ROOT_PATH').val() + '/micro2.0/platform/',
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
    operate_start_time: new Date(Date.parse('2015-03-17'.replace(/-/g, "/"))),
	now: new Date()
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
        case global.url_1:
            //console.log(global.url_1 + '累计出借额: ' + value);
            $("#url_1").html(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_2:
            //console.log(global.url_2 + '自成立以来累计借贷金额: ' + value);
            $("#url_2").html(MoneyUtil.formatMoney(value,2,",","."));
            pageDta.url_num2 = parseFloat(value);
            IsOK2=true;
            break;
        case global.url_3:
            //console.log(global.url_3 + '自成立以来累计借贷笔数: ' + value);
            $("#url_3").html(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_4:
            //console.log(global.url_4 + '当前待还借贷金额: ' + value);
            $("#url_4").html(MoneyUtil.formatMoney(value,2,",","."));
            pageDta.url_num4 = parseFloat(value);
            IsOK4=true;
            break;
        case global.url_5:
            //console.log(global.url_5 + '当前待还借贷笔数: ' + value);
            $("#url_5").html(MoneyUtil.formatMoney(value,0,",",""));           
            break;
        case global.url_6:
            //console.log(global.url_6 + '累计出借人数: ' + value);
            $("#url_6").html(MoneyUtil.formatMoney(value,0,",",""));
            pageDta.url_num6 = parseFloat(value);
            IsOK6=true;
            break;
        case global.url_7:
            //console.log(global.url_7 + '当期出借人数: ' + value);
            $("#url_7").html(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_8:
            //console.log(global.url_8 + '前十大出借人出借余额总和: ' + value);
            $("#url_8").val(value);
            pageDta.url_num8 = parseFloat(value);
            IsOK8=true;
            break;
        case global.url_9:
            //console.log(global.url_9 + '最大单一出借人出借余额总和: ' + value);
            $("#url_9").val(value);
            pageDta.url_num9 = parseFloat(value);
            IsOK9=true;
            break;
        case global.url_10:
            //console.log(global.url_10 + '累计借款人数: ' + value);
            $("#url_10").html(MoneyUtil.formatMoney(value,0,",",""));
            pageDta.url_num10 = parseFloat(value);
            IsOK10=true;
            break;
        case global.url_11:
            //console.log(global.url_11 + '当期借款人数: ' + value);
            $("#url_11").html(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_12:
            //console.log(global.url_12 + '前十大借款人待还金额总和: ' + value);
            $("#url_12").val(value);
            pageDta.url_num12 = parseFloat(value);
            IsOK12=true;
            break;
        case global.url_13:
            //console.log(global.url_13 + '最大单一借款人待还金额总和: ' + value);
            $("#url_13").val(value);
            pageDta.url_num13 = parseFloat(value);
            IsOK13=true;
            break;
        case global.url_14:
            //console.log(global.url_14 + '借款人逾期金额: ' + value);
            $("#url_14").html(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_15:
            //console.log(global.url_15 + '借款人逾期笔数: ' + value);
            $("#url_15").html(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_16:
            //console.log(global.url_16 + '借款人逾期90天以上金额: ' + value);
            $("#url_16").html(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_17:
            //console.log(global.url_17 + '借款人逾期90天以上笔数: ' + value);
            $("#url_17").html(MoneyUtil.formatMoney(value,0,",",""));
            break;
        case global.url_18:
            //console.log(global.url_18 + '累计代偿金额: ' + value);
            $("#url_18").html(MoneyUtil.formatMoney(value,2,",","."));
            break;
        case global.url_19:
            //console.log(global.url_19 + '累计代偿笔数: ' + value);
            $("#url_19").html(MoneyUtil.formatMoney(value,0,",",""));
            break;
    }
    de()
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
    operate_date();
});

//运营天数
function operate_date() {
	var ts = global.now - global.operate_start_time;//计算剩余的毫秒数  
	var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	$("#operate_days").text(dd);
}
function de(){
	//var url_num2=parseFloat($("#url_2").html());//累计借贷金额
	//var url_num6=parseFloat($("#url_6").html());//累计出借人数
	//var url_num8=parseFloat($("#url_8").val());//前十大出借人出借余额总和
   //var url_num4=parseFloat($("#url_4").html());//当前待还借贷金额
    //var url_num9=parseFloat($("#url_9").val());//最大单一出借人出借余额总和
    //var url_num10=parseFloat($("#url_10").html());//累计借款人数
    //var url_num12=parseFloat($("#url_12").val());//前十大借款人待还金额总和
    //var url_num13=parseFloat($("#url_13").val());//最大单一借款人待还金额总和
	if(IsOK2==true&&IsOK6==true){
		//人均累计出借金额=累计借贷金额/累计出借人数，仅统计本金    	
    	var num1=parseFloat(pageDta.url_num2/pageDta.url_num6).toFixed(2);
    	if(parseFloat(num1) == 0) {
            $("#lend_per_amount").html("0.01");
        } else{
        	$("#url_num1").html(MoneyUtil.formatMoney(num1,2,",","."))
        }	
	}
	if(IsOK8==true&&IsOK4==true){
		//前十大出借人出借余额占比=按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金    	
    	var num2=parseFloat(pageDta.url_num8/pageDta.url_num4*100).toFixed(2);
    	if(parseFloat(num2) == 0) {
            $("#lend_tenth_proportion").html("0.01%");
        } else{
        	$("#url_num2").html(num2+"%");
        }
    	
	}
	if(IsOK9==true&&IsOK4==true){
		//最大单一出借人出借余额占比=按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金    	
    	var num3=parseFloat(pageDta.url_num9/pageDta.url_num4*100).toFixed(2);
    	if(parseFloat(num3) == 0) {
            $("#lend_largest_proportion").html("0.01%")
        } else{
        	$("#url_num3").html(num3+"%")
        }
    	
	}
	if(IsOK2==true&&IsOK10==true){
		//人均累计借款金额=累计借贷金额/累计借款人数，仅统计本金    	
    	var num4=parseFloat(pageDta.url_num2/pageDta.url_num10).toFixed(2);
    	if(parseFloat(num4) == 0) {
            $("#loan_per_amount").html("0.01");
        } else {
        	$("#url_num4").html(MoneyUtil.formatMoney(num4,2,",","."))
        }
    	
	}
	if(IsOK12==true&&IsOK4==true){
		//前十大借款人待还金额占比=按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金   	
    	var num5=parseFloat(pageDta.url_num12/pageDta.url_num4*100).toFixed(2);
    	if(parseFloat(num5) == 0) {
            $("#loan_tenth_proportion").html("0.01%")
        } else{
        	$("#url_num5").html(num5+"%")
        }
    	
	}
	if(IsOK13==true&&IsOK4==true){
		//最大单一借款人待还金额占比=按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金    	
    	var num6=parseFloat(pageDta.url_num13/pageDta.url_num4*100).toFixed(2);
    	if(parseFloat(num6) == 0) {
            $("#loan_largest_proportion").html("0.01%")
        } else{
        	$("#url_num6").html(num6+"%")
        }
    	
	}
}





