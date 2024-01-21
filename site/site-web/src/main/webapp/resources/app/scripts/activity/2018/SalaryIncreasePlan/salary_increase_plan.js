var timeScroll01="";
var timeScroll02="";
var timeScroll03="";
var timeScroll04="";
var timeLoad="";
jQuery.fn.extend({
	pic_scroll: function(num) {
		$(this).each(function() {
			var _this = $(this); //存储对象
			var ul = _this.find(".payIncreaseBox"); //获取ul对象
			var li = ul.find(".payIncreaseBoxLine"); //获取所有图片所有的li
			var w = li.size() * li.outerHeight(); //统计图片的长度
			li.clone().prependTo(ul); //克隆图片一份放入ul里
			//ul.width(2*w);//设置ul的长度，使所有图片排成一排
			var i = 1,
				l;
			function autoScroll() {
				l = _this.scrollTop();
				if(l >= w) {
					_this.scrollTop(0);
				} else {
					_this.scrollTop(l + i);
				}
			}
			if(num=="01"){
				timeScroll01= setInterval(autoScroll, 50);
			}
			if(num=="02"){
				timeScroll02= setInterval(autoScroll, 50);
			}
			if(num=="03"){
				timeScroll03= setInterval(autoScroll, 50);
			}
			if(num=="04"){
				timeScroll04= setInterval(autoScroll, 50);
			}
		})
	}
});
function toAndroidPage(json) {
	javascript:coinharbour.toAndroidPage(json);
}
$(function(){	
	$('.go_buy').on('click', function() {
		var json = '{"appActive":{"page":"b"}}';
		var client = document.getElementById("client").value;
		if(client == "ios") {
			toiOSPage(json);
		} else if(client == "android") {
			toAndroidPage(json);
		}
	});
	$(".payIncreaseRuleBtn").click(function(){
		$("#rule_one").addClass("alert_show").removeClass("alert_hide")
	})
	$(".close").click(function(){
		$("#rule_one").removeClass("alert_show").addClass("alert_hide")
	})
	if($("#isStart").val() == "start"){
		getlist();
		timeLoad=setInterval(getlist,60000);
	}else{
		clearInterval(timeLoad);
		getlist();
	}
})
function getlist(){
	$.ajax({
        url: $("#APP_ROOT_PATH").val() + '/app/activity/salaryIncreasePlan/getNewData',
        type: 'get',
        data: '',
        success: function(data) {
        	var html01="";
        	var html02="";
        	var html03="";
        	var html04="";
        	forList("#payIncreaseQuota01",data.moreThan10000Quota,html01,data.moreThan10000List,"#payIncreaseRecordNone01","#payIncreaseBox01","#payIncreaseScroll01",'#payIncreaseBox01 .payIncreaseBoxLine','01')  
        	forList("#payIncreaseQuota02",data.moreThan50000Quota,html02,data.moreThan50000List,"#payIncreaseRecordNone02","#payIncreaseBox02","#payIncreaseScroll02",'#payIncreaseBox02 .payIncreaseBoxLine','02')    	
        	forList("#payIncreaseQuota03",data.moreThan100000Quota,html03,data.moreThan100000List,"#payIncreaseRecordNone03","#payIncreaseBox03","#payIncreaseScroll03",'#payIncreaseBox03 .payIncreaseBoxLine','03')    	
        	forList("#payIncreaseQuota04",data.moreThan500000Quota,html04,data.moreThan500000List,"#payIncreaseRecordNone04","#payIncreaseBox04","#payIncreaseScroll04",'#payIncreaseBox04 .payIncreaseBoxLine','04')    	
        },
        error: function (data) {
        	//drawToastrem_750(data)
        }
    });
}
function forList(Quota,Qdata,html,moreList,node,box,pay,len,num){
	if(moreList != null){	            		
	    for(var i in moreList){
	    	html+='<div class="payIncreaseBoxLine">'+getLocalTime(moreList[i].userDrawTime).slice(5,11)+''+getLocalTime(moreList[i].userDrawTime).slice(11,16)+' '+moreList[i].mobile+'</div>'            		            	    	
	    }	    
	    $(box).html(html)
	    $(node).hide()
		$(pay).show()
		 if($(len).length > 3&&num=="01") {
			 clearInterval(timeScroll01)
			 $(pay).pic_scroll(num);
		 }
	    if($(len).length > 3&&num=="02") {
			 clearInterval(timeScroll02)
			 $(pay).pic_scroll(num);
		 }
	    if($(len).length > 3&&num=="03") {
			 clearInterval(timeScroll03)
			 $(pay).pic_scroll(num);
		 }
	    if($(len).length > 3&&num=="04") {
			 clearInterval(timeScroll04)
			 $(pay).pic_scroll(num);
		 }
	}else{
		 $(box).html("")
		 $(node).show()
		 $(pay).hide()
	} 
	if(Qdata != null){
		$(Quota).text(Qdata+"位")
	}else{
		if(num=="01"){
			$(Quota).text("30位")
		}
		if(num=="02"){
			$(Quota).text("15位")
		}
		if(num=="03"){
			$(Quota).text("10位")
		}
		if(num=="04"){
			$(Quota).text("5位")
		}
	}	
}
function getLocalTime(date){
    var date = new Date(date);//如果date为13位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y+M+D+h+m+s;
}