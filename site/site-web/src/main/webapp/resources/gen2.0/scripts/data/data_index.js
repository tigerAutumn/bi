$(function(){
	// 数据准备开始
	var monthJson = [];
	var investMonthJson = [];
	var investMonthArray = $("#data_one").attr('investMonthArray'); 
	var investAmountMonthArray = $("#data_one").attr('investAmountMonthArray');
	if(investMonthArray) {
		investMonthArray = $.parseJSON(investMonthArray);
		for ( var index in investMonthArray) {
			monthJson.push(investMonthArray[index]);
		}
	}
	if(investAmountMonthArray) {
		investAmountMonthArray = $.parseJSON(investAmountMonthArray);
		for ( var index in investAmountMonthArray) {
			var obj = {};
			obj.name = investAmountMonthArray[index].investMonth;
			obj.color = "rgb(252, 103, 61)";
			obj.y = parseFloat(investAmountMonthArray[index].totalInvestOverMonthString);
			investMonthJson.push(obj);
		}
	}
	var investInterestWill = parseFloat($("#data_six").attr('investInterestWill'));
	var totalInterestAmount = parseFloat($("#data_six").attr('totalInterestAmount'));
	
	var productNameArray = $.parseJSON($("#data_five").attr('productNameArray'));
	var productAmountArray = $.parseJSON($("#data_five").attr('productAmountArray'));
	for ( var index in productAmountArray) {
		productAmountArray[index] = parseFloat(productAmountArray[index]);
	}
	var averageInvestRate = parseFloat($("#data_seven").attr('averageInvestRate'));
	
	var investorTypeAgeArray = $.parseJSON($("#data_age").attr('investorTypeAgeArray'));
	var investorTypeAgeValArray = $.parseJSON($("#data_age").attr('investorTypeAgeValArray'));
	for ( var index in investorTypeAgeValArray) {
		investorTypeAgeValArray[index] = parseFloat(investorTypeAgeValArray[index]);
	}
	
	var investorTypeSexArray = $.parseJSON($("#data_sex").attr('investorTypeSexArray'));
	var investorTypeSexValArray = $.parseJSON($("#data_sex").attr('investorTypeSexValArray'));
	for ( var index in investorTypeSexValArray) {
		investorTypeSexValArray[index] = parseFloat(investorTypeSexValArray[index]);
	}
	// 数据准备结束
	$("#data_one").highcharts({//平台成交额
		title:{
			text:'平台成交额',
            floating:false,
            style:{
                fontSize:"14px",
                color:"#666"
            },
            y:15
		},
		subtitle: {
            text: '<div style="font-size:12px;padding-left:6px;display:block;color: #999">累计成交额（万元）</div>',
   			color:"#999",
   			align:"left",
            y:45,
            x:55
        },
		legend:{
			enabled:false
		},
		credits: {
		     enabled: false
			},
		chart: {
            backgroundColor:null,
            borderWidth:0
        },
        xAxis: {
            type: 'category',
            lineColor: '#e5e5e5',
            gridLineWidth: 0,
            tickColor: '#e5e5e5',
            categories:{
            	alternateGridColor:"#000000"
            },
            labels:{
                style:{
                    fontSize:"12px",
                    color:"#999"
                }
            },
            categories: monthJson
        },
        yAxis: {
            lineColor: '#e5e5e5',
            lineWidth: 1,
            tickPixelInterval: 100,
            gridLineWidth: 0,
            tickColor: '#e5e5e5',
            tickWidth: 1,
            title: {
                text: null
            },
            labels:{
                formatter: function () {
                    return this.value/10000 ;
                },
            	enabled: true,
                style:{
                    fontSize:"10px",
                    color:"#999"
                }
            }

        },
        tooltip:{
            animation:true,
            enabled: true,
            formatter:function(){
        		return this.x + parseInt(this.y/10000) + '万元';
        	}
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: false,
                    format: '{point.y:.1f}%'
                }
            },
            column:{
            	pointWidth:15
            },
            areaspline: {
                fillOpacity: 0.0,
                color:"#ff6633"
            }
        },
        series: [
            /*{
				type: 'areaspline',                                      
	            data: investMonthJson,                               
	            marker: {                                                     
	            	lineWidth: 2,                                               
	            	lineColor: Highcharts.getOptions().colors[3],               
	            	fillColor: 'white'                                          
	            }
			},*/
			{
				type: 'column',
				color:'#ff6633',
				data:investMonthJson,
                lineWidth:40
			}
		]
	});

	// 理财计划概况
	$("#data_five").highcharts({
		title:{
            text:'各期限计划成交概况',
            floating:false,
            style:{
                fontSize:"14px",
                color:"#666"
            },
            y:15
        },
        subtitle: {
            text: '<div style="font-size:12px;padding-left:6px;display:block;color:#999">总成交额（万元）</div>',
            color:"#999",
            align:"left",
            y:45,
            x:55
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            backgroundColor:null,
            borderWidth:0,
            borderRadius:8
        },
        xAxis: {
            type: 'category',
            lineColor: '#e5e5e5',
            lineWidth: 1,
            tickPixelInterval: 100,
            gridLineWidth: 0,
            tickColor: '#e5e5e5',
            tickWidth: 1,
            categories:{
                alternateGridColor:"#000000"
            },
            labels:{
                style:{
                    fontSize:"12px",
                    color:"#999"
                }
            },
            categories: productNameArray
        },
        yAxis: {
            lineColor: '#e5e5e5',
            lineWidth: 1,
            tickPixelInterval: 100,
            gridLineWidth: 0,
            tickColor: '#e5e5e5',
            tickWidth: 1,
            title: {
                text: null
            },
            labels:{
                formatter: function () {
                    return this.value/10000 ;
                },
                enabled: true
            }

        },
        tooltip: {
        	enabled: true,
            formatter:function(){
        		return this.x + parseInt(this.y/10000) + '万元';
        	}
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: false,
                    format: '{point.y:.1f}%'
                }
            },
            column:{
               pointWidth:40
            },
            areaspline: {
                fillOpacity: 0.0,
                color:"#ff6633"
            }
        },
        series: [{
            type: 'column',
            data:[
                {"color":"#ffc33c",y:productAmountArray[0]},
                {"color":"#ff6633",y:productAmountArray[1]},
                {"color":"#ffc33c",y:productAmountArray[2]}
            ]
        }]
    });
	//收益概况
	$("#data_six").highcharts({
		title:{
			text:"收益概况",
            style:{
                fontSize:"14px",
                color:"#666"
            },
            y:20
		},
		subtitle: {
            text: null,
            color:"#999",
            align:"left",
            y:38
        },
		legend:{
			enabled:true,
            symbolPadding:10,
            layout: 'vertical',
            itemMarginBottom: 20,
            align: 'center',
            verticalAlign: 'bottom',
            squareSymbol: true,
            itemStyle:{
                fontSize:"12px",
                fontWeight:"normal"
            },
            floating: false,
            borderWidth: 0,
           // backgroundColor: '#f7f7f7',
            labelFormatter:function(){
            	return this.name + '<div style="font-size:12px;margin-bottom: 10px;position: relative;top:-5px;color: #999;">：'+"<span style='color:#666;'>¥"+Highcharts.numberFormat(this.y,2,'.',',')+'</span></div>';
            }
		},
		credits: {
	     	enabled: false

		},
		chart: {
            backgroundColor:null,
            borderWidth:0,
            borderRadius:8
        },
        tooltip: {
        	shared: true,
        	useHTML: true, //是否使用HTML编辑提示信息
        	headerFormat: '<span style="font-size:12px">{point.key}</span>',
        	pointFormat: '<span style="text-align: right;font-size:12px">¥{point.y:.2f}</span>'
        },
        plotOptions: {
        	x:100,
            pie: {
            	// center:[100,100],
            	size:"100%",
            	align: 'left',
                allowPointSelect: false,
                cursor: 'pointer',
                dataLabels: {
                	distance:0,//数据显示位置，负数就越靠近圆心
                    enabled: false,
                    style:{
                    	color: '#666',
                    	fontSize:"12px",
                    	textShadow:"none"
                    },
                    format: '{point.percentage:.1f} %'
                },
                showInLegend:true
            }
        },
        series: [{
            type: 'pie',
            name:'收益',
            data: [
                {
	                name: '已赚取收益',
	                color:'#ffc33c',
	                y: totalInterestAmount
	            },
                {
	                name: '待赚取收益',
	                color:'#f8e7bf',
	                y: investInterestWill
            	}
            ]
        }]
	});

	//平均收益率对比
	$("#data_seven").highcharts({
		title:{
			text:"平均收益率对比",
            style:{
                fontSize:"20px"
            },
            y:20
		},
		subtitle: {
            text: null,
            color:"#525252",
            align:"left",
            y:38
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            backgroundColor:null,
            borderWidth:0,
            borderRadius:8
        },
        xAxis: {
            type: 'category',
            gridLineWidth: 0,
            categories:{
                alternateGridColor:"#000000"
            },
            labels:{
                y:20,
                style:{
                    fontSize:12,
                    lineHeight:"20px"
                }
            },
            categories: ['银行活期', '银行定期', '银行理财','币港湾<br>平均收益率']
        },
         yAxis: {
            lineColor:'#FF0000',
            LineWidth:1,
             max:12,
            // tickPositions: [0, 2000, 4000, 6000,8000,10000],
            tickPixelInterval:100,
            gridLineWidth: 0,
            title: {
                text: null
            },
            labels:{
                formatter: function () {
                    return this.value ;
                },
                enabled: false

            }

        },
        tooltip:{
            animation:false,
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '<div style="font-size:12px;">{point.y:.2f}%</div>'
                }
            },
            column:{
               // pointWidth:26
            },
            areaspline: {
                fillOpacity: 0.0,
                color:"#ff6633"
            }
        },
        series: [{
            type: 'column',
            data:[
                {"color":"#bbec6e",y:0.35},
                {"color":"#7fcbef",y:1.5},
                {"color":"#ea4a8a",y:4.8},
                {"color":"#ff6633",y:averageInvestRate}
            ]
            
        }]
    });
    // 投资人年龄分布
    $("#data_age").highcharts({
    	title:{
            text:'出借人年龄分布',
            floating:false,
            style:{
                fontSize:"14px",
                color:"#666"
            },
            y: 20
        },
        subtitle: {
            text: null,
            color: "#525252",
            align: "left",
            y: 38
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            type:"column",
            backgroundColor:null,
            borderWidth:0,
            height:300
            // borderRadius:8,
        },
        xAxis: {
            type: 'category',
            lineColor: '#e5e5e5',
            lineWidth: 1,
            categories:{
                alternateGridColor:"#e5e5e5"
            },
            tickLength: 0,
            labels:{
                style:{
                    fontSize:"12px",
                    color:"#999"
                }
            },
            categories: investorTypeAgeArray
        },
         yAxis: {
             lineColor: '#e5e5e5',
             LineWidth: 1,
             // tickPositions: [0, 2000, 4000, 6000,8000,10000],
             max:50,//纵轴的最大值
             //tickPixelInterval:100,
             tickColor: '#e5e5e5',
             gridLineWidth: 0,
             title: {
                 text: null
            },
            labels:{
                formatter: function () {
                    return this.value ;
                },
                enabled: false
            }
        },
        tooltip:{
            animation:false,
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '<div style="font-size:12px;color:#666;font-weight: lighter">{point.y:.2f}%</div>'
                }
            },
            column:{
                pointWidth:40
            },
            areaspline: {
                fillOpacity: 0.0,
                color:"#ff6633"
            }
        },
        series: [{
            type: 'column',
            data:[
                {"color":"#ffc33c",y:investorTypeAgeValArray[0]},
                {"color":"#ff6633",y:investorTypeAgeValArray[1]},
                {"color":"#ffc33c",y:investorTypeAgeValArray[2]},
                {"color":"#ffc33c",y:investorTypeAgeValArray[3]}
            ]
        }]
    });
    // 投资人性别分布
    $("#data_sex").highcharts({
    	 title:{
             text:'出借人性别分布',
             floating:false,
             style:{
                 fontSize:"14px",
                 color:"#666"
             },
             y:20
         },
         subtitle: {
             text: null,
             color:"#525252",
             align:"left",
             y:38
         },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            type:"column",
            backgroundColor:null,
            borderWidth:0,
            height:300
            // borderRadius:8,
        },
        xAxis: {
            type: 'category',
            lineColor: '#e5e5e5',
            lineWidth: 1,
            tickLength: 0,
            labels:{
                style:{
                    fontSize:"12px",
                    color:"#999"
                }
            },
            categories:{
                alternateGridColor:"#e5e5e5"
            },
           
            categories: investorTypeSexArray
        },
         yAxis: {
             lineColor: '#e5e5e5',
             LineWidth: 1,
             max: 80,
             // tickPositions: [0, 2000, 4000, 6000,8000,10000],
             gridLineWidth: 0,
             title: {
                 text: null
             },
             labels: {
                 formatter: function () {
                     return this.value;
                 },
                 enabled: false
             }

        },
        tooltip:{
            animation:false,
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    allowOverlap:false,
                    enabled: true,
                    format: '<div style="font-size:12px;color:#666;font-weight: lighter">{point.y:.2f}%</div>'
                }
            },
            column:{
                pointWidth:40
            }
            // areaspline: {
            //     fillOpacity: 0.0,
            //     color:"#ff6633",
            // },
        },
        series: [{
            type: 'column',
            data:[
                {"color":"#ff6633",y:investorTypeSexValArray[0]},
                {"color":"#ffc33c",y:investorTypeSexValArray[1]}
            ]
            
        }]
    });
});