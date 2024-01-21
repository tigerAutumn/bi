
$(document).ready(function(){
 


      //登录显示按钮
      /*var sub_two=$(".sub_two");
      sub_two.on('click',function() {
          event.preventDefault();
          
          $(".login_wp").hide(100);
          $(".window_p_1").animate({
            "bottom":60,
            "opacity":1
            },300);
      });*/
	//微信滑出
        $('.wx').hover(function(){
        	$('.imgs').fadeIn(100);
        },function(){
        	$('.imgs').hide(100);
        });

        try{
            $sL=$('#tuoyuan').position().left;  
        }catch(e){
            // console.log(e.name);
        }
       
        $('.nav_right').find('li').hover(function(){
        	$i=$(this).index();
        	$('#tuoyuan').stop(true).animate({"left":$i*90+"px"},200);
        },function(){
        	$('#tuoyuan').stop(true).animate({"left":$sL+"px"},200);
        })
        
        //微信固定
        $('.f1').hover(function(){
        	$(this).css("background-color","#FF6633")
        	$('.fimg').fadeIn(100);
        	
        },function(){
        	$('.fimg').hide(100);
        	$(this).css("background-color","transparent")
        });
        $('.f3').hover(function(){
            $(this).css("background-color","#FF6633")
            $('.f3cur_ft').fadeIn(100);
            
        },function(){
            $('.f3cur_ft').hide(100);
            $(this).css("background-color","transparent")
        });

          //回到顶部
        $('.f4').click(function(){
			$("html,body").animate({scrollTop:0},200);	
		});
      // 安全保障
      $(document).scroll(function(){
        var body_heiht=$(this).scrollTop();
        try{
            var con_safe=$(".con_safe").offset().top;
        } catch(e){
            // console.log("安全保障滚动监听:"+e.name);
        }
        
        if(body_heiht>=con_safe){
           $(".gd").css({"position":"fixed"});
        }else{
            $(".gd").css({"position":"absolute"});
        }
       //显示回到头部
        if(body_heiht>=50){
        	$(".f4").show(200);
        }else{
        	$(".f4").hide(200);
        }
      })

      // 老用户网银支付tab切换
      $('#z2con_bspanm').click(function(){
            $(this).removeClass("z2con_bspanm").addClass('z2con_bspanm1');
            $('#z2con_bspanl').removeClass("z2con_bspanl").addClass('z2con_bspanl1');
            $('.z2con_bspanll').css('display','none');
            $('.z2con_bspanlr').css('display','block');
            var root = $("#APP_ROOT_PATH_GEN").val();
            //校验是否有可用红包
            $.ajax({
            	url: root + "/gen2.0/regular/redPacketCheck",
				data:{
	    			productId:$("#productId").val(),
	    			money:$("#buyAmount").val()
	    		},
	    		type: "post",
				dataType: "json",
				async: false,
				success: function(data) {
					if(data.redPacketId && data.redPacketId !='' ){
						drawAlertNew('直接使用网银支付无法使用红包，如需使用红包，请先用网银充值到余额后再购买！');
					}
				}
            });
        })
        $('#z2con_bspanl').click(function(){
            $(this).removeClass("z2con_bspanl1").addClass('z2con_bspanl');
            $('#z2con_bspanm').removeClass("z2con_bspanm1").addClass('z2con_bspanm');
            $('.z2con_bspanll').css('display','block');
            $('.z2con_bspanlr').css('display','none');
        })
    // 老用户网银支付-选中状态
    $(".circle").on("click",function(){
        $(".cul_z2").find(".circle").removeClass("active_circle");
        $(this).addClass("active_circle");
    })

    // 个人中心-我要充值
    /*侧导航-s*/
        $('.con_left_nav').children('dt').click(function(){
            $i=$(this).index();      //dl里dt下标;li里div下标
            $j=$(this).parent('dl').index(); //dl下标;li下标
            $('.con_left_nav').children('dt').css({"background":"#fff","color":"#656565"});
            $(this).css({"background":"#FF6634","color":"#fff"}).siblings('dt');
            
            $('.con_right_nav').children('li').eq($j).children('div').eq($i).css("display","block").siblings('div').css("display","none");
            $('.con_right_nav').children('li').eq($j).siblings('li').children('div').css("display","none");
        })
        
        // 回款路径下银行卡点击选中
        $('.yuan').click(function(){
                $(this).css('background','url('+$("#APP_ROOT_PATH_GEN").val()+'/resources/gen178/images/duih.png)');
                $(this).css('background-repeat','no-repeat');
                $(this).css('border-color','#fff');
            })

        // 账户有余额显示银行卡说明
        $(".itemts_right").hover(function(){
            $(this).find(".bg_op").stop().show(200);
        },function(){
            $(this).find(".bg_op").stop().hide(200);
        });

        // var a = '1034';
        // var b = a.split("");
        // console.log(b);
        var rootPath = $("#APP_ROOT_PATH_GEN").val();
        //首页显示数字
        shownnumber($("#investNum").val(),"#ty_ul1");
        shownnumber($("#currTotalAmount").val(),"#ty_ul2");
        shownnumber($("#totalIncome").val(),"#ty_ul3");
        function shownnumber(data,id){
            var all='0123456789'.split('');
            var one_ko=String(data).split("");
            var ry_li='';
            var ty_ul=$(id);
            for(var i=0;i<one_ko.length;i++){
                for(var j=0;j<all.length;j++){
                    if(one_ko[i]==all[j]){
                        var img_url = rootPath+"/resources/gen178/images/"+all[j]+".png";
                    	ry_li+="<li class='ry_list'><img src='"+img_url+"'></li>"
                        break;
                    }
                }
            }
          ty_ul.append(ry_li);
        }
        
        
        
        
        // 首页轮播
        var imgLength = $('#slide').find('img').length;
        var slide=$("#slide");
         if(imgLength == 1){
           $('.bannerarea').find('.button').hide();
               return false;
             }else{
               //版头轮播
               $(function() {
   	        	if(slide.size()==0){
   	                return false;
   	            }
                 $('#slide').switchable({
                   triggers: '1',
                   autoplay:true,
                   putTriggers: 'insertBefore',
                   effect: 'fade',
                   easing: 'ease-in-out',
                   loop: true,
                   prev: '#prev',
                   next: '#next',
                   onSwitch: function(event, currentIndex) {
                     var api = this;
                     api.prevBtn.toggleClass('disabled', currentIndex === 0);
                     api.nextBtn.toggleClass('disabled', currentIndex === api.length - 1);
                   }
                 });
               });
         }

})

