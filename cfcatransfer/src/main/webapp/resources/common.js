
	//toast 提示框
	function drawToast(message)
	{   
		if($('#toast').length>0){
			return;
		}

		var toastHTML = '<div id="toast"><p style="width:60px;height:60px;margin-bottom: 15px;margin-left: 70px;margin-top: 20px;background-color:#666;border-radius:50px;color:#fff;font-size:50px;line-height:70px;font-weight:bold;font-family: 汉仪菱心体简;">!</p><span style="font-size: 16px;line-height: 25px;padding-left: 10px;padding-right: 10px;">' + message + '</span></div>';   
		document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
	    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
	    $("#toast").css({"top":divTop, "left":divLeft});
		$('#toast').show(300).delay(2000).hide(300,function(){
			$(this).remove();
		});
	}
	//toast 提示框 
	function drawToasts(message)
	{   
		if($('#toast').length>0){
			return;
		}
		var toastHTML = '<div id="toast"><p style="width:60px;height:60px;margin-bottom: 15px;margin-left: 70px;margin-top: 20px;background-color:#666;border-radius:50px;color:#fff;font-size:50px;line-height:70px;font-weight:bold;">&radic;</p><span style="font-size: 16px;line-height: 25px;padding-left: 10px;padding-right: 10px;">' + message + '</span></div>';   
		document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
	    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
	    $("#toast").css({"top":divTop, "left":divLeft});
		$('#toast').show(300).delay(2000).hide(300,function(){
			$(this).remove();
		});
	}

	// 模态框
	/**
	 * title:标题 backMesage:提示内容 backMessage:左边按钮名称 backUrl:加入左边链接，不想跳转，直接双引号
	 * commitMessage:右边按钮名称 commitUrl:加入右边链接名称，不想跳转，直接双引号
	 */
	function drawAlert(title,message,backMesage,backUrl,commitMessage,commitUrl){
		var url=$('#APP_ROOT_PATH').val() +'/alert';
		$.ajax({
			type:"GET",
			url:url,
			data:"",
			success:function(data){
				$("body").append(data);
				var success=new myAlert({id:"J_popup",title:title,content:message,goback:[backMesage,backUrl],commit:[commitMessage,commitUrl],eventTarget:"join"});
				success.init();	
				success.show();
				}
		});	
	}
	
	var isfrist = 0;
	function getMatrixPic(param,fileName){
		var url=$('#APP_ROOT_PATH').val() +'/getMatrix';
		
		if(isfrist == 1){
			invokeToSubscribe();
			return ;
		}
		
		$.ajax({
			type:"POST",
			url:url,
			data:"param=" + param + "&fileName=" + fileName,
			success:function(data){
				$("body").append(data);
				invokeToSubscribe();
			}
		});	
		
		isfrist = 1;
	}
	
	
	// 二维码弹出层
	function hideSubscribe(a) {
	    if (a.target == $('#imgcode') [0]) {
	        return
	    }
	    $('body').unbind('touchmove');
	    if ($('#subscribe_mask').length > 0) {
	        $('#subscribe_mask').css('display', 'none')
	    }
	}
	function invokeToSubscribe() {
	    if ($('#subscribe_mask').length > 0) {
	        var a = document.documentElement.scrollTop || document.body.scrollTop;
	        $('#subscribe_mask').css('top', a);
	        $('body').bind('touchmove', function (b) {
	            b.preventDefault()
	        });
	        $('#subscribe_mask').css('display', 'block')
	    }
	}
	

	function invokeToFriend() {
	   // if (window.is_in_weixin) {
	        popupNavigation('发送给指定好友/群')
	   // } else {
	   // alert('请在微信中打开该链接，发送给指定好友/群。')
	   // }
	}
	function invokeToMoment() {
	   // if (window.is_in_weixin) {
	        popupNavigation('分享到朋友圈')
	   // } else {
	   // alert('请在微信中打开该链接，分享到朋友圈。')
	   // }
	}
	
	function popupNavigation(b, a) {
	    if (b) {
	        $('#navi_content1').text(b)
	    }
	    if (a) {
	        $('#navi_content2').text(a)
	    } else {
	        $('#navi_content2').text('')
	    }
	    if ($('#navi_mask').length > 0) {
	        $('#navi_mask').css({
	            display: 'block'
	        })
	    }
	}
	
	var audio;
	function playOrPaused(obj){
		var type = $("#audio" + obj).attr("date-type");
		
		switch(type){
		case "play":
			audio = document.getElementById('audio'+obj);
			audio.play();
			$("#audio" + obj).attr("date-type","end");
			audio.addEventListener('ended', function () {  
				$("#audio" + obj).attr("date-type","play");
			}, false);
			break;
		case "end":
			audio.pause();
			audio.currentTime = 0;
			$("#audio" + obj).attr("date-type","play");
			break;
		}
   } 
	
	/**
	 * 替换字符串，保留空格，
	 * @param message
	 * @returns
	 */
   function replaceMessage(message) {
	   message=message.replace(/\r\n/g,'<br>');
	   message=message.replace(/\r/g,'<br>');
	   message=message.replace(/\n/g,'</br>');
	   return message;
   }
	