/**
 * wap 全局 js文件
 * @authors supmain (mtingfeng@gmail.com)
 * @date    2014-12-01 11:53:04
 * @for *.html
 * @version $1.0$
 */
var $window = $(window);
var TBS = {
	
	init: function(){
        //this.lazyload();
    },
    check: {
    	isAndroid:function(){
    		var ua = navigator.userAgent;
    		var flag = ua.indexOf('Android') > -1 || ua.indexOf('Linux') > -1;
            return flag;
    	},
    	isIos:function(){
    		var ua = navigator.userAgent;  
    		var flag = !!ua.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
            return flag;
    	},
    	//判断移动端
        isPhone: function(){
            var ua = navigator.userAgent,
                reg = /iPhone|iPad|Android|ucweb|windows\s+mobile|Windows\s+Phone/i;
                return reg.test(ua);
        },
        //判断是否为中英文，数字，下划线，减号
        isNick: function(str) {
            var nickReg = /^[\u4e00-\u9fa5A-Za-z0-9-_]+$/;
            return nickReg.test(str);
        },
        //判断邮箱
        isEmail: function(str) {
            var emailReg = /^[a-z0-9][\w\.]*@([a-z0-9][a-z0-9-]*\.)+[a-z]{2,5}$/i;
            return emailReg.test(str);
        },
        //判断手机
        isMobile: function(str) {
            var mobileReg = /^1[34578][0-9]{9}$/;
            return mobileReg.test(str);
        },
        //判断URL
        isUrl: function(str) {
            var urlReg = /^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-.\/?%&=]*)?$/;
            return urlReg.test(str);
        },
        //判断数字
        isNum: function(str) {
            var numReg = /^[0-9]\d*$/;
            return numReg.test(str);
        }
    },

    dialog: {

        $confirm: null,

        confirm: function(html,callback,initFn){
            var $confirm = null;
            if(this.$confirm !== null || $('#dg-tip').length>0){
                return;
            }else{
                $confirm = $('<div id="dg-confirm"><div class="dg_body"><div class="dg_box"><i class="iconfont blue">&#xe649;</i>'+html+'</div><div class="btm"><a href="javascript:TBS.dialog.close();" class="graybtn">取消</a><a href="javascript:;" class="btn">确定</a></div></div></div>');
            }
            $confirm.appendTo('body').addClass('pop_in');
            this.$confirm = $confirm;
            TBS.pageScroll.stop();
            $confirm.on('click','a.btn',function(){
                setTimeout(function(){
                    callback && callback();
                }, 50);
                return false;
            });

            initFn && initFn();
        },

        close: function(){
            var that = this,
                $confirm = that.$confirm;
            if($confirm === null){
                return;
            }else{
                $confirm.addClass('pop_out');
                setTimeout(function(){
                    $confirm.off().remove();
                    that.$confirm = null;
                    TBS.pageScroll.allow();
                }, 350);
            }
        },

        tip: function(html){
            var $Tip = null;
            if($('#dg-tip').length>0){
                return;
            }else{
                $Tip = $('<div id="dg-tip">'+html+'</div>');
            }
            $Tip.appendTo('body').fadeIn();
            setTimeout(function(){
                $Tip.fadeOut(function(){
                    $Tip.remove();
                });
            }, 2500);
        }
    },

    // 页面滚动事件
    pageScroll: {
        event: function(e){
            e.preventDefault();
        },

        // 禁止
        stop: function(){
            var fn = this.event;
            document.body.addEventListener('touchmove',fn,false);
        },

        // 允许
        allow: function(){
            var fn = this.event;
            document.body.removeEventListener('touchmove',fn,false);
        }
    },
    
    // 返回时间JSON，参数为秒
    timeJson: function(times) {
        var oTime = {};
        oTime.secs = Math.floor(times % 60);
        oTime.mins = Math.floor(times / 60 % 60);
        oTime.hours = Math.floor(times / 60 / 60);
        oTime.days = 0;

        if(oTime.hours > 23){
            oTime.days = Math.floor(oTime.hours/24);
            oTime.hours = oTime.hours - oTime.days*24;
        }
        if(oTime.secs<10){
            oTime.secs = '0' + oTime.secs;
        }
        if(oTime.mins<10){
            oTime.mins = '0' + oTime.mins;
        }
        if(oTime.hours<10){
            oTime.hours = '0' + oTime.hours;
        }
        if(oTime.days<10){
            oTime.days = '0' + oTime.days;
        }
        return oTime;
    },

    //延迟加载图片
    lazyload: function() {
        var winheight = $window.height(),
            $images = null,
            _timeout = null;

        $images = $("img").filter(function(){
            return $(this).attr("original") !== undefined;
        });

        function showImg() {
            var _winStop = 0;

            if($images.length < 1){
                return;
            }

            _winStop = $window.scrollTop();

            $images.each(function () {
                var _this = $(this),
                    _top = 0,
                    _src = _this.attr("original");
                if(_src === ''){
                    return;
                }

                _top = _this.offset().top;

                if(_top >= _winStop-200 && _top <= (winheight + _winStop + 50)){
                    _this.hide().attr('src',_src).fadeIn();
                    _this.attr("original","");
                    _this.error(function(){
                        this.src = 'http://img.nala.com.cn/images/kong.gif';
                        _this.addClass('img_error');
                    });
                }
            });
            $images = $images.filter(function(){
                return $(this).attr("original") !== '';
            });
        }

        showImg();
        $window.on("scroll", function () {
            if(_timeout !== null){
                clearTimeout(_timeout);
            }
            _timeout = setTimeout(function(){
                showImg();
            }, 50);
        });
    },
    prettyTab: function(){
        $.fn.prettyTab = function(setting){
            var defauset = {
                current: 0,
                currentClass: 'on'
            };
            $.extend(defauset, setting);
            var current = defauset.current,
                curClass = defauset.currentClass;
            this.each(function(index, val) {
                var $this = $(this),
                    $nav = $this.find('.prettyTabNav'),
                    $con = $this.find(".prettyTabCont"),
                    $item = $this.find('.prettyTabItem');
                $nav.eq(current).addClass(curClass).siblings().removeClass(curClass);
                $item.hide().eq(current).show();
                $this.on('touchend','.prettyTabNav', function(event) {
                    var _this = $(this),
                        index = _this.index();
                    _this.addClass(curClass).siblings().removeClass(curClass);
                    $item.hide().eq(index).show();
                });
            });
        };
        
    }
};
var validate = {
        require: function(elem, errmsg,errorcls){
            elem = $(elem);
            elem.on("change", function(){
                var value = this.value;
                alert("value:"+value);
                var tipCon = $(this).parents("td").find("span");
                if(errorcls){
                    tipCon=$(errorcls);
                }                            
                if(value == ""){
                    tipCon.html(errmsg);
                }else{
                    tipCon.html("");
                }
            });
        },
        phone: function(elem, errmsg,errorcls){
            elem = $(elem);
            var tipCon = elem.parent().find("span");
            if(errorcls){
                    tipCon=$(errorcls);
            }
            elem.on("change", function(){
                var value = $.trim(this.value);
                if(!/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[0-9])[0-9]{8}$/.test(value)){
                	$(this).addClass("error");
                	$.dialog({
                        content : errmsg,
                        title: "alert",
                        time : 1500
                    });
                }else{               
                    //tipCon.html("");
                }
            });
        },
        email: function(elem, errmsg,errorcls){
            elem = $(elem);
            var tipCon = elem.parent().find("span");
            if(errorcls){
                    tipCon=$(errorcls);
            }
            elem.on("change", function(){
                var value = $.trim(this.value);
                if(!/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value)){
                    tipCon.html(errmsg);
                }else{
                    tipCon.html("");
                }
            });
        },
        compare: function(elem1, elem2, errmsg,errorcls){
            elem1 = $(elem1);
            elem2 = $(elem2);
            var tipCon = elem2.parent().find("span");
            if(errorcls){
                    tipCon=$(errorcls);
            }
            elem2.on("change", function(){
            	if(!elem1.hasClass("error")&&elem1.val()!=""){
            		var value1 = $.trim(elem1[0].value);
                    var value2 = $.trim(this.value);
                    if(value1 !== value2){
                    	$(this).addClass("error");
                    	$.dialog({
                            content : errmsg,
                            title: "alert",
                            time : 1500
                        });
                    }else{
                    	//elem1.removeClass(errorcls)
                        //tipCon.html("");
                    }
            	}
                
            });
        },
        maxminln:function(elem,minln,maxln,errmsg,errorcls){
        	elem = $(elem);
        	if(elem.length>1){
        		$.each(elem,function(i,e){
        			iftrue(e,minln,maxln,errorcls);
        		})
        	}else{
        		iftrue(elem,minln,maxln,errorcls);
        	}
        	function iftrue(elem,minln,maxln,errorcls){
        		$(elem).on("change","",function(){
        			if($(this).val().length>=minln&&$(this).val().length<=maxln){        				
            			//tipCon.html("");
        				$(this).removeClass("error");
            		}else{
            			$(this).addClass("error");
            			$.dialog({
                            content : errmsg,
                            title: "alert",
                            time : 1500
                        });
            			
            		}
        		});
        		
        	}
        }
};

$(function(){
   TBS.init();
   $.fn.validform = function(){
		var objform = $(this);
		TBS.validate = true;
		$.each(objform.find('.tip-valid'),function(i,e){
			var type = $(e).attr("data-rule");
			var range = $(e).attr("data-range");
			var compare = $(e).attr("compare-with");
			if(type=="mobile"){
				var ismobile = TBS.check.isMobile($(e).val());
				if(!ismobile){
					alerttip('手机格式不正确！');				
					return false;
				}
			}else if(type=="require"){
				if($(e).val()==""){
					alerttip($(e).attr("tipmes"));				
					return false;
				}
			}
			if(range){
				var  rangelist = range.split(",");
				var isinln = $(e).val().length >=rangelist[0] && $(e).val().length <= rangelist[1];
				if(!isinln){
					alerttip($(e).attr("tipmes"));	
					return false;
				}
				
			}
			if(compare){
				if ($(e).val()!=$('#'+compare).val()){
					alerttip('两次密码输入不正确！');					
					return false;
				}
			}
		});
		function alerttip(con){
			TBS.validate = false;
			$.dialog({
	            content : con,
	            title: "alert",
	            time:1500
	        });
		}
	}
});

