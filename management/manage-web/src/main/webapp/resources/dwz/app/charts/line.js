$(function(){
	$('#charts').highcharts({
        chart: {
            type: 'line',
            marginRight: 130,
            marginBottom: 25
        },
        title: {
            text: '每月订单数量',
            x: -20 //center
        },
        subtitle: {
            text: '来源:www.qiuxueke.com',
            x: -20
        },
        xAxis: {
            categories: ['一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
            title: {
                text: '订单数量'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '个'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -10,
            y: 100,
            borderWidth: 0
        },
        series:[]
    });
	var url = $("#APP_ROOT_PATH").val() + '/charts/line_ajax.htm';
	$.get(url, function (result) {
		 var data = eval(result);
		 var charts = $('#charts').highcharts();
		 for(var i=0;i<data.length;i++){
			 charts.addSeries({name: data[i].name, data: data[i].data});
		 }
	 });
});
