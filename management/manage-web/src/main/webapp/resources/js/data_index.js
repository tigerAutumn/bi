$(function(){
	/**
	 * 图表HighCharts
	 */
	var categories = [];
	var dailyNewRegistUser = [];
	var dailyNewUserInvestAmount = [];
	var dailyOldUserInvestAmount = [];
	if($("#chartDatas").val()) {
		var chartDatas = JSON.parse($("#chartDatas").val());
		for ( var index in chartDatas) {
			categories.push(chartDatas[index].time);
			dailyNewRegistUser.push(chartDatas[index].dailyNewRegistUser);
			dailyNewUserInvestAmount.push(chartDatas[index].dailyNewUserInvestAmount);
			dailyOldUserInvestAmount.push(chartDatas[index].dailyOldUserInvestAmount);
		}
	}
	var tickInterval = 1;
	if(dailyNewRegistUser.length >= 25){
		tickInterval = 5;
	}
	
	$('#tubiao').highcharts({
		chart: {
            type: 'spline'
        },
        title: {
            text: '趋势图',
            x: -20
        },
        xAxis: {
        	tickInterval: tickInterval,
            categories: categories
        },
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        credits: {
            enabled: false
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0
        },
        scrollbar: {
            enabled: false
        },
        series: [
        {
            name: '新注册人数',
            data: dailyNewRegistUser,
        }, {
            name: '新用户购买金额',
            data: dailyNewUserInvestAmount,
            visible: false
        }, {
            name: '老用户购买金额',
            data: dailyOldUserInvestAmount,
            visible: false
        }]
    });
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 点击时间类型
	$("#day").click(function(){
		$(this).css("background-color","#5CB85C");
		$("#hour").css("background-color","#ddd");
		$("#span_text").show();
		$("#end_time").show();
		$("#current_days").show();
		
		$("input[name='timeType']").val('day');
	});
	$("#hour").click(function(){
		$(this).css("background-color","#5CB85C");
		$("#day").css("background-color","#ddd");
		$("input[name='endTime']").val('');
		$("#span_text").hide();
		$("#end_time").hide();
		$("#current_days").hide();
		
		$("input[name='timeType']").val('hour');
	});
	// 选择最近天数事件
	$("#current_days").change(function(){
		$("input[name='startTime']").val('');
		$("input[name='endTime']").val('');
		$("input[name='currentDays']").val($(this).val());
	});
	// 渠道变化
	$("#agent_id").change(function(){
		$("input[name='agent']").val($(this).val());
	});
	
	/**
	 * 查询
	 */
	$("#sub_query").click(function(){
		if (checkForm()) {
			$("#query_form").submit();
		}
	});
	
	function checkForm(){
		var time_type = $("#time_type").val();
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		// 按日对比
		if('day' == time_type) {
			if(!start_time && end_time){
				alertMsg.warn('开始日期不能为空');
				return false;
			}
			if(!end_time && start_time){
				alertMsg.warn('截止日期不能为空');
				return false;
			}
			if(end_time < start_time){
				alertMsg.warn('截止日期不能低于开始日期');
				return false;
			}
		}
		// 按时对比
		else {
			if(!start_time){
				alertMsg.warn('请选择日期');
				return false;
			}
		}
		return true;
	}
});









