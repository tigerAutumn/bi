
	
	//数据
	var age = $("input[name = 'ages']");
	var income = $("input[name = 'income']");
	var investment = $("input[name = 'investment']");
	var exprience = $("input[name = 'exprience']");
	var times = $("input[name = 'times']");
	var attitude = $("input[name = 'attitude']");
	var newprjt = $("input[name = 'newprjt']");
	var term = $("input[name = 'term']");
	var objective = $("input[name = 'objective']");
	var anxious = $("input[name = 'anxious']");
	var score = $("input[name = 'score']");
	var questionDom = $(".question-frame");
	var x = new Array(age, income, investment, exprience, times, attitude, newprjt, term, objective, anxious);
	//计算总分
	function checked() {
		var totalData = 0;
		for(var i = 0; i < 10; i++) {
			for(var j = 0; j < x[i].length; j++) {
				if(x[i][j].checked) {
					totalData += parseInt(x[i][j].value);
				}
			}
		}
		score[0].value = totalData.toString();
	}
	//提交
	function totalScore() {
		var len = $("input:checked").length;
	    if(len == 10){
	    	checked();
	    }else {
	        return false;
	    }		
	}
	//下一题
	$('.Q_child').click(function() {
		var _that = $(this);
		setTimeout(function() {
			_that.parent().parent().hide().next().show();
		}, 100)
	});
	$('.Q_child_end').click(function() {
		$(".Q_btna").css({"background":"#ff6633","color":"#fff"});
	})
	//上一题
	$('.Q_up a').click(function() {
		var _that = $(this);
		setTimeout(function() {
			_that.parent().parent().hide().prev().show();
		}, 100)
	})