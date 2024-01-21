$(function(){
	var Jtab={
		Tcla:{
			header_li:$(".header_li"),
			main:$(".main"),
			line:$(".main_nav_border1")
		},
		Jfun:function(ev){
			var that=ev;
			var show_Tcla=Jtab.Tcla.main.eq($(that).index()).is(":hidden");
			if(show_Tcla){
				Jtab.Tcla.header_li.removeClass('header_active');
				Jtab.Tcla.header_li.find('span').css('display','none');
				$(that).addClass('header_active');
				$(that).find('span').css('display','block');
				Jtab.Tcla.main.hide();
				$(window).scrollTop(0);
				Jtab.Tcla.main.eq($(that).index()).fadeIn().scrollTop(100);
			}
			
		}
	}
	Jtab.Tcla.header_li.on("touchend",function(){
		Jtab.Jfun(this);
	})	
	/*
	var time = 0; //定时器
	function touchStart(){
		time = setTimeout("press()",1000);
	}
	function touchMove(){
		clearTimeout(time);
	}
	function touchEnd(){
		clearTimeout(time);
	}
	function press(){
		var client = document.getElementById("client").value;
		var json = "{'press':'save'}";
		if(client == "ios") {
			toiOSPage(json);
		}
		if(client == "android") {
			toAndroidPage(json);
		}
	}
	function toiOSPage(json) {
		
	}
	function toAndroidPage(json) {
		javascript:coinharbour.toAndroidPage(json);
	}
	
	function selectTab(level,obj) {
		var tabs = document.getElementsByClassName("header_li");
		for(var i=0;i<tabs.length;i++) {
			tabs[i].setAttribute("class", "header_li");
		}
		obj.setAttribute("class", "header_li header_active");
		
		var contents = document.getElementsByClassName("main");
		for(var j=0;j<contents.length;j++) {
			contents[j].style.cssText = "display:none";
		}
		document.getElementsByClassName("main"+level+" main")[0].style.cssText = "display:block";
	}*/
})