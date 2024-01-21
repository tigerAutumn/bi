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
	$("#data_one").highcharts({
		title:{
			text:'平台成交额',
            floating:false,
            style:{
                fontSize:"24px",
                color:"#666"
            },
		},
		subtitle: {
            text: '累计成交额（万元）',
            style:{
            	fontSize:"20px",
                color:"#999"
            },
   			align:"center",
   			floating: false        },
		legend:{
			enabled:false
		},
		credits: {
		     enabled: false
			},
		chart: {
            backgroundColor:"#ffffff",
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
                style:{
                    fontSize:"16px",
                    color:"#999"
                }
            },
            categories: monthJson
        },
         yAxis: {
        	lineColor: '#ccc',
            lineWidth: 1,
            tickPixelInterval:100,
         	gridLineWidth: 0,
         	tickColor: '#ccc',
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
                    fontSize:"20px",
                    color:"#999"
                }

            }

        },
        tooltip:{
        	animation:true,
        	enabled: true,
        	style:{
                fontSize:18
            },
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
            	pointWidth:25
            },
            areaspline: {
                fillOpacity: 0.0,
                color:"#ff6633"
            }
        },
        series: [
/* {
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
			data:investMonthJson

		}]
	});

	// 理财计划概况
	$("#data_five").highcharts({
		title:{
			text:'各期限计划成交概况',
            floating:false,
            style:{
                fontSize:"24px",
                color:"#666"
            },
		},
		subtitle: {
            text: '总成交额（万元）',
            style:{
            	fontSize:"20px",
                color:"#999"
            },
   			align:"center",
            y:50
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            backgroundColor:"#ffffff",
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
                style:{
                    fontSize:"16px",
                    color:"#999"
                }
            },
            categories: productNameArray
        },
         yAxis: {
        	lineColor: '#ccc',
            lineWidth: 1,
            tickPixelInterval:100,
            gridLineWidth: 0,
            tickColor: '#ccc',
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
                	fontSize:"20px",
                    color:"#999"
                }

            }

        },
        tooltip:{
        	animation:true,
        	enabled: true,
        	style:{
                fontSize:18
            },
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
                pointWidth:50
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
                fontSize:"24px",
                color:"#666"
            },
		},
		legend:{
			enabled:true,
            symbolHeight:20,
            symbolPadding:20,
			layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            itemMarginTop:30,
            x: 0,
            y: 110,
            itemStyle:{
                fontSize:"20px",
                fontWeight:"normal",
                color:"#999"
            },
            floating: false,
            borderWidth: 0,           
            labelFormatter:function(){
            	return this.name + '<br><div style="font-size:24px;color:#666;">¥'+'<span style="font-size:24px;color:#666;">'+Highcharts.numberFormat(this.y,2,'.',',')+'</span>';
            }
		},
		credits: {
	     	enabled: false
		},
		chart: {
            backgroundColor:"#ffffff",
            borderWidth:0,
            borderRadius:8
        },
        tooltip:{
        	animation:true,
        	enabled: true,
        	useHTML: true, //是否使用HTML编辑提示信息
        	headerFormat: '<small style="font-size:18px;">{point.key}</small>',
        	pointFormat: '<span style="font-size:18px;text-align:right">¥{point.y:.2f}</span>'
        },
        plotOptions: {
        	x:100,
            pie: {
            	size:"90%",
            	align: 'left',
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                	distance:-40,//数据显示位置，负数就越靠近圆心
                    enabled: false,
                    style:{
                    	color: '#ffffff',
                    	fontSize:"12px",
                    	textShadow:"none"
                    }
                },
                showInLegend:true
            }
        },
        series: [{
            type: 'pie',
            name:'收益',
            data: [{
	                name: '已赚取收益',
	                color:'#ffc33c',
	                y: totalInterestAmount
	            }, {
	                name: '待赚取收益',
	                color:'#f8e7bf',
	                y: investInterestWill
            	}]
        }]
	});

    // 投资人年龄分布
    $("#data_age").highcharts({
        title:{
            text:'出借人年龄分布',
            floating:false,
            style:{
            	fontSize:"24px",
                color:"#666"
            },
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            type:"column",
            backgroundColor:"#ffffff",
            borderWidth:0
        },
        xAxis: {
            type: 'category',
            gridLineWidth: 0,
            categories:{
                alternateGridColor:"#000000"
            },
            labels:{
                 y:30,
                style:{
                    fontSize:20,
                    color:"#999"
                }
            },
            categories: investorTypeAgeArray
        },
         yAxis: {
            lineColor:'#FF0000',
            LineWidth:1,
            tickPixelInterval:100,
            gridLineWidth: 0,
            max:100,
            title: {
                text: null
            },
            labels:{
                formatter: function () {
                    return this.value ;
                },
                enabled: false,
                style:{
                    fontSize:22
                }

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
                    format: '<div style="font-size:20px;color:#666">{point.y:.2f}%</div>',
                    y:8
                }
            },
            column:{
                pointWidth:50
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
            	fontSize:"24px",
                color:"#666"
            },
            y:30
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            type:"column",
            backgroundColor:"#ffffff",
            borderWidth:0
        },
        xAxis: {
            type: 'category',
            gridLineWidth: 0,
            categories:{
                alternateGridColor:"#000000"
            },
            labels:{
                 y:30,
                style:{
                    fontSize:20,
                    color:"#999"
                },
            },
           
            categories: investorTypeSexArray
        },
         yAxis: {
            lineColor:'#FF0000',
            LineWidth:1,
            tickPixelInterval:100,
            gridLineWidth: 0,
            max:100,
            title: {
                text: null
            },
            labels:{
                formatter: function () {
                    return this.value ;
                },
                enabled: false,
                style:{
                    fontSize:22
                }

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
                    format: '<div style="font-size:20px;color:#666">{point.y:.2f}%</div>',
                    y:10
                }
            },
            column:{
                pointWidth:50
            }
        },
        series: [{
            type: 'column',
            data:[
                {"color":"#ff6633",y:investorTypeSexValArray[0]},
                {"color":"#ffc33c",y:investorTypeSexValArray[1]}
            ]
            
        }]
    });

	//平均收益率对比
	/*$("#data_seven").highcharts({
        title:{
            text:'平均收益率对比',
            floating:false,
            style:{
                fontSize:"30px"
            },
            y:30
        },
        legend:{
            enabled:false
        },
        credits: {
             enabled: false
            },
        chart: {
            backgroundColor:"#ffffff",
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
                y:30,
                style:{
                    fontSize:26,
                    lineHeight:"30px"
                }
            },
            categories: ['银行活期', '银行定期', '银行理财','币港湾<br>平均收益率']
        },
         yAxis: {
            lineColor:'#FF0000',
            LineWidth:1,
            max:12,
            tickPixelInterval:100,
            gridLineWidth: 0,
            title: {
                text: null
            },
            labels:{
                formatter: function () {
                    return this.value ;
                },
                enabled: false,
                style:{
                    fontSize:22
                }

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
                    format: '<div style="font-size:20px;">{point.y:.2f}%</div>',
                    verticalAlign:'top',
                    y:-30
                }
            },
            column:{
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
    });*/
});