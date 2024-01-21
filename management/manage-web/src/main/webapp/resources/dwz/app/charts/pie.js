$(function(){
		$('#charts').highcharts({
		    chart: {
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false
		    },
		    title: {
		        text: '求学客课程销售报表'
		    },
		    tooltip: {
			    pointFormat: '{series.name}: <b>'+ +'{point.percentage}%</b>',
		    	percentageDecimals: 1,
		    	formatter:function(){
                    return'<b>'+this.point.name+'</b>: '+Highcharts.numberFormat(this.percentage, 2)+' %';
                }
		    },
		    plotOptions: {
		        pie: {
		            allowPointSelect: true,
		            cursor: 'pointer',
		            dataLabels: {
		                enabled: true,
		                color: '#000000',
		                connectorColor: '#000000',
		                formatter: function() {
		                    return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +' %';
		                }
		            }
		        }
		    },exporting: { 
                enabled: false 
            },
		    series: [{
		        type: 'pie',
		        name: '在求学客课程中所占比例',
		        data:[]
		    }]
		});
	var url1 = $("#APP_ROOT_PATH").val() + '/charts/ajax.htm';
	 $.get(url1, function (result) {
		 var data = eval(result);
		 var charts = $('#charts').highcharts();
		 charts.series[0].setData(data); 
	 });
	 
	 $("button[name=ok]").click(function(){
		 var month; 
		 $("input[name=month]").each(function(){
			 if($(this).attr("checked")){
                 month = $(this).val();
             }
		 });
		 var url = $("#APP_ROOT_PATH").val() + '/charts/ajax.htm?month='+month+"&status="+$("#status").val();
		 $.get(url, function (result) {
			 var data = eval(result);
			 var charts = $('#charts').highcharts();
			 charts.series[0].setData(data); 
		 });
	 });
});
