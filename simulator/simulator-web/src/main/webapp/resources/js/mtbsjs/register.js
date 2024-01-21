var time=60;
var time_id="";
TBS.validate = true;
TBS.regpage = {
		init:function(){
			var that = this;
			$('body').height($(window).height());
			//that.validInit();//初始化验证
			that.sendCode();//发送验证码
			that.formsend();//表单提交
			that.refreshCode();//刷新验证码
		},
		validInit:function(){
			validate.phone("input[name=phonenum]", "手机号码格式不正确","error");
		    validate.compare("input[name=password]", "input[name=repassword]", "两次密码不一致","error");
		    validate.maxminln(".psd",6,16,"密码位数必须6-16位","error");
		},
		//获取验证码
		sendCode:function(){
			$('.valid').on('click','.send.undisable',function(){
				$(this).removeClass('undisable');
				var obj = $(this);
					var img_code = $("input[name=img_code]").val(),
						phonenum = $("input[name=phonenum]").val();
					if(img_code==""){
						$.dialog({
				            content : '图片验证码不能为空！',
				            title: "alert",
				            time:1500
				        });
						obj.addClass('undisable');
					}else{
						$.ajax({
							type:'post',
							url:'/mobile/invite/verify_code',
							data:{
								code : img_code
							},
							dataType:'json',
							success:function(data){
								if(data){
									if(phonenum==""){
										$.dialog({
								            content : '请填写手机号！',
								            title: "alert",
								            time:1500
								        });
										obj.addClass('undisable');
									}else if(!TBS.check.isMobile(phonenum)){
										$.dialog({
								            content : '手机格式不正确！',
								            title: "alert",
								            time:1500
								        });
										obj.addClass('undisable');
									}else{
										$.post("/mobile/user/register/send",{"phonenum":phonenum},function(data){
										    if(data.code == 100){
										         time_id=setInterval(countDown,1000);
											}else if(data.code == -400){
												$.dialog({
								                    content : "亲，你已经注册过了！",
								                    title: "alert",
								                    time : 1500
								                });
											}else{
												$.dialog({
								                    content : "发送失败，请重试！",
								                    title: "alert",
								                    time : 1500
								                });
											}
										    setTimeout(function(){
										    	obj.addClass('undisable');
										    },1000)						 
										  },"json");
									}
								}else {
									$.dialog({
							            content : '图片验证码不正确！',
							            title: "alert",
							            time:1500
							        });
									obj.addClass('undisable');
								}
							}
						})
					}
			});
			function countDown(){
				--time;
			 	$(".valid a").removeClass('send').html(time+"秒后重发"); 	
			 	if(time==0){
			   		clearInterval(time_id);
			   		$(".valid a").addClass('send').html("发送验证码");
			        time=60;
			 	}
			}
		},
		formsend:function(){
			$('form').on('click','.sub_ok',function(){
				var verify_code = $("input[name=code]").val();
				if(verify_code == null || verify_code == "") {
					$.dialog({
			            content : '验证码不能为空！',
			            title: "alert",
			            time:1500
			        });
				}else {
					$.ajax({
						type:'post',
						url:'/mobile/user/verify/'+$("input[name=code]").val(),
						dataType:'json',
						success:function(data){
							if(data.code == 100) {
								$('form').validform();
								if(TBS.validate){
									$.ajax({
										url:'/mobile/user/register',
										data:$('form').serialize(),
										type:'post',
										dataType:'json',
										success:function(data){
											if(data.code == 100){
												$.dialog({
										            content : '注册成功',
										            title: "alert",
										            time:1500
										        });
											}else if(data.code == -400){
												$.dialog({
										            content : '亲，你已经注册过了！',
										            title: "alert",
										            time:1500
										        });
											}else{
												$.dialog({
										            content : '注册失败',
										            title: "alert",
										            time:1500
										        });
											}								
										}
									})
								}
							}else {
								$.dialog({
						            content : '验证码不正确，请重新填写！',
						            title: "alert",
						            time:1500
						        });
							}
						}
					})
				}
			});
		},
		//刷新验证码
		refreshCode:function() {
			$('#code_img').on('click',function(){
				$('#code_img').attr('src','/mobile/invite/verify?d='+new Date().getTime());
			})
		}
}
$(function(){	
	TBS.regpage.init();
});