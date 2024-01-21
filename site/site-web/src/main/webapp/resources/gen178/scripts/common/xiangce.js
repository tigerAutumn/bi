function getStyle(obj, attr)
{
	return obj.currentStyle?obj.currentStyle[attr]:getComputedStyle(obj, false)[attr];
}
var global = {
		img_src: '',
		img_id: '',
		index: 0,
		img_height:0
}
 function pic(obj1,obj2,obj3,obj4,obj5,obj6,obj7){
	var oBox = document.getElementById(obj1);
	var oBg = document.getElementById(obj2);
	var oBot = document.getElementById(obj3);
	var aBli = oBot.getElementsByTagName(obj4);
	var oFrame = document.getElementById(obj5);
	var aLi = oBox.getElementsByTagName(obj6);
	var aImg = oBox.getElementsByTagName(obj7);
	var body = document.getElementById(body);
	global.img_height = parseInt(document.documentElement.clientHeight * 0.9);
	var i = iNow =  0;
	for(i=0;i<aLi.length;i++){
		aLi[i].index = i;
		aLi[i].onclick = function(){
			document.getElementById('body').style.overflow="hidden"
				with(oFrame.style){display = 'block',width =document.documentElement.clientWidth+'px',height = global.img_height +'px';}
			
			global.img_src = $(this).children('img').attr('src');
			global.img_id = $(this).children('img').attr('id');
			
			oFrame.innerHTML = '<img src="'+$(this).children('img').attr('src')+'" />'; 
			var oImg = oFrame.getElementsByTagName('img')[0];
			
			var iWidth = parseInt(oImg.width*global.img_height/oImg.height);
			var iHeight =global.img_height;
			var iLeft = parseInt((document.documentElement.clientWidth / 2) - (iWidth /2));
			var iTop = parseInt((document.documentElement.clientHeight / 2) - (iHeight /2) - 50);
			with(oImg.style){height = global.img_height+'px',width = 'auto';}
			startMove(oFrame, {opacity:100, width:document.documentElement.clientWidth+'px', height:iHeight});
			oBg.style.display = 'block';
			oBot.style.display = 'block';
			iNow = this.index + 1;
		};
	}
	oFrame.onmousedown = function(){
		return false
	};
	
	aBli[1].onclick = function(){
		oFrame.style.cursor = 'move';
		oFrame.onmousedown = function(e){
			var oEvent = e || event;
			var X = oEvent.clientX - oFrame.offsetLeft;
			var Y = oEvent.clientY - oFrame.offsetTop;
			document.onmousemove = function(e){
				var oEvent = e || event;
				var L = oEvent.clientX - X;
				var T = oEvent.clientY - Y;
				if(L < 0){
					L = 0;
				}else if(L > document.documentElement.clientWidth - oFrame.offsetWidth){
					L = document.documentElement.clientWidth - oFrame.offsetWidth
				}
				if(T < 0){
					T = 0;
				}else if(T > document.documentElement.clientHeight - oFrame.offsetHeight){
					T = document.documentElement.clientHeight - oFrame.offsetHeight;
				}
				
				oFrame.style.margin = 0;
				return false;
			}
			
			document.onmouseup = function(){
				document.onmouseup = null;
				document.onmousemove = null;
			};
			return false;
		};
	};
	aBli[2].onclick = function(){
		startMove(oFrame, { opacity:0, width:150, height:100}, function(){
			oFrame.style.display = 'none';
			oBg.style.display = 'none';
			oBot.style.display = 'none';
			oFrame.onmousedown = null;
			oFrame.style.cursor = 'auto';
			document.getElementById('body').style.overflow='auto'
		});
	};
	
};

//上一页 合作协议
function prev(obj){
	var imgList = $('.coop_mobiles').find('img');
	var pre_src = '';
	var pre_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index-1 < 0) {
				drawToast("已经是第一张了");
				break;
			}
			pre_src = $(imgList[index-1]).attr('src');
			pre_id = $(imgList[index-1]).attr('id');
			global.index = index-1;
			global.img_src = pre_src;
			global.img_id = pre_id;
			
			
			
			$("#frame").html('<img src="'+pre_src+'" width="auto" height='+global.img_height+' "px" />');
			
			break;
		}
	}
}
//下一页 合作协议
function next(obj){
	var imgList = $('.coop_mobiles').find('img');
	var next_src = '';
	var next_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index+1 == imgList.length) {
				drawToast("已经是最后一张了");
				break;
			}
			next_src = $(imgList[index+1]).attr('src');
			next_id = $(imgList[index+1]).attr('id');
			global.img_src = next_src;
			global.img_id = next_id;
			$("#frame").html('<img src="'+next_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}



//上一页 借款协议
function prev1(obj){
	var imgList = $('.loan_mobiles').find('img');
	var pre_src = '';
	var pre_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index-1 < 0) {
				drawToast("已经是第一张了");
				break;
			}
			pre_src = $(imgList[index-1]).attr('src');
			pre_id = $(imgList[index-1]).attr('id');
			global.index = index-1;
			global.img_src = pre_src;
			global.img_id = pre_id;
			$("#frame1").html('<img src="'+pre_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}
//下一页借款协议
function next1(obj){
	var imgList = $('.loan_mobiles').find('img');
	var next_src = '';
	var next_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index+1 == imgList.length) {
				drawToast("已经是最后一张了");
				break;
			}
			next_src = $(imgList[index+1]).attr('src');
			next_id = $(imgList[index+1]).attr('id');
			global.img_src = next_src;
			global.img_id = next_id;
			$("#frame1").html('<img src="'+next_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}



//第三方担保合同-s
//上一页 第三方担保合同
function prev2(obj){
	var imgList = $('.rating_grade_mobiles').find('img');
	var pre_src = '';
	var pre_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index-1 < 0) {
				drawToast("已经是第一张了");
				break;
			}
			pre_src = $(imgList[index-1]).attr('src');
			pre_id = $(imgList[index-1]).attr('id');
			global.index = index-1;
			global.img_src = pre_src;
			global.img_id = pre_id;
			$("#frame2").html('<img src="'+pre_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}
//下一页第三方担保合同
function next2(obj){
	var imgList = $('.rating_grade_mobiles').find('img');
	var next_src = '';
	var next_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index+1 == imgList.length) {
				drawToast("已经是最后一张了");
				break;
			}
			next_src = $(imgList[index+1]).attr('src');
			next_id = $(imgList[index+1]).attr('id');
			global.img_src = next_src;
			global.img_id = next_id;
			$("#frame2").html('<img src="'+next_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}
//第三方担保合同-e


//合作方信息披露-s
//上一页 合作方信息披露
function prev3(obj){
	var imgList = $('.orgnize_check_mobiles').find('img');
	var pre_src = '';
	var pre_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index-1 < 0) {
				drawToast("已经是第一张了");
				break;
			}
			pre_src = $(imgList[index-1]).attr('src');
			pre_id = $(imgList[index-1]).attr('id');
			global.index = index-1;
			global.img_src = pre_src;
			global.img_id = pre_id;
			$("#frame3").html('<img src="'+pre_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}
//下一页合作方信息披露
function next3(obj){
	var imgList = $('.orgnize_check_mobiles').find('img');
	var next_src = '';
	var next_id = '';
	for ( var index in imgList) {
		index = parseInt(index);
		if(global.img_id == $(imgList[index]).attr('id')) {
			if(index+1 == imgList.length) {
				drawToast("已经是最后一张了");
				break;
			}
			next_src = $(imgList[index+1]).attr('src');
			next_id = $(imgList[index+1]).attr('id');
			global.img_src = next_src;
			global.img_id = next_id;
			$("#frame3").html('<img src="'+next_src+'" width="auto" height='+global.img_height+'"px" />');
			break;
		}
	}
}
//合作方信息披露-e



function startMove(obj, json, onEnd){
	clearInterval(obj.timer);
	obj.timer=setInterval(function (){
		doMove(obj, json, onEnd);
	}, 30);
}
function doMove(obj, json, onEnd){
	var attr='';
	var bStop=true;
	for(attr in json){
		var iCur=0;
		if(attr=='opacity'){
			iCur=parseInt(parseFloat(getStyle(obj, attr))*100);
		}else{
			iCur=parseInt(getStyle(obj, attr));
		}
		var iSpeed=(json[attr]-iCur)/5;
		iSpeed=iSpeed>0?Math.ceil(iSpeed):Math.floor(iSpeed);
		
		if(json[attr]!=iCur){
			bStop=false;
		}
		if(attr=='opacity'){
			obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
			obj.style.opacity=(iCur+iSpeed)/100;
		}else{
			obj.style[attr]=iCur+iSpeed+'px';
		}
	}
	if(bStop){
		clearInterval(obj.timer);		
		if(onEnd){
			onEnd();
		}
	}
}