


var safeStep = $(".process_step").children("li");
var safeDes = $(".process_des").children("li");
var scrollProcess = $("#safe_process").offset().top - 200;
var scrollBody = $("body").scrollTop();
var time = null;
$(document).ready(function () {
	$(safeStep[0]).addClass("step_focuse");
	$(safeDes[0]).show();
	if (scrollBody >= scrollProcess){
		time = setInterval(nextProcess,5000);
	}else {}
});

$(".process_step li").on('hover',function () {
	var stepNum = $(this).index();
	//console.log(stepNum);
	$($(this).siblings("li")).removeClass("step_focuse");
	$(this).addClass("step_focuse");
	$(safeDes).hide();
	$(safeDes[stepNum]).show();
	clearInterval(time);
}).mouseleave(function () {
	time = setInterval(nextProcess,5000);
});



function nextProcess() {
	var showStep = $($(".process_step").children(".step_focuse")).index();
	if (showStep == 9) {
		showStep = 0;
		$(safeStep).removeClass("step_focuse");
		$(safeDes).hide();
		$(safeStep[showStep]).addClass("step_focuse");
		$(safeDes[showStep]).show();
	} else {
		showStep += 1;
		$(safeStep).removeClass("step_focuse");
		$(safeDes).hide();
		$(safeStep[showStep]).addClass("step_focuse");
		$(safeDes[showStep]).show();
	}
}