/**
 * Author:      cyb
 * Date:        2017/3/16
 * Description:
 */

var constants = {
    status: {
        init: 'INIT',
        over: 'OVER',
        used: 'USED',
        initt: 'INITt',
        overt: 'OVERt',
        usedt: 'USEDt'
    }
};
var global = {
    base_url : $('#APP_ROOT_PATH').val(),
    red_packet_list_url: '/micro2.0/redPacket/myRedPacketPage',
    go_buy_url: '/micro2.0/regular/list',
    home_url: '/micro2.0/index',
    qianbao: $('#qianbao').val(),
    agentViewFlag: $('#agentViewFlag').val(),
    current_status: $('#status').val(),
    init_page_num: $('#initPageNum').val() ? parseInt($('#initPageNum').val()) : 1,
    over_page_num: $('#overPageNum').val() ? parseInt($('#overPageNum').val()) : 1,
    used_page_num: $('#usedPageNum').val() ? parseInt($('#usedPageNum').val()) : 1,
    init_total_pages: $('#initTotalPages').val() ? parseInt($('#initTotalPages').val()) : 1,
    over_total_pages: $('#overTotalPages').val() ? parseInt($('#overTotalPages').val()) : 1,
    used_total_pages: $('#usedTotalPages').val() ? parseInt($('#usedTotalPages').val()) : 1,
    init_scroll_flag: true,
    used_scroll_flag: true,
    over_scroll_flag: true,
    go_url:"/micro2.0/redPacket/otherCoupons",//已使用已过期
    red_USER:"?type=RED_PACKET&status=USED",
    red_OVER:"?type=RED_PACKET&status=OVER",
    ticket_USER:"?type=INTEREST_TICKET&status=USED",
    ticket_OVER:"?type=INTEREST_TICKET&status=OVER",
    interes_url:"/micro2.0/static/interes_ticket_h5",
    initt_page_num: $('#inittPageNum').val() ? parseInt($('#inittPageNum').val()) : 1,
    overt_page_num: $('#overtPageNum').val() ? parseInt($('#overtPageNum').val()) : 1,
    usedt_page_num: $('#usedtPageNum').val() ? parseInt($('#usedtPageNum').val()) : 1,
    initt_total_pages: $('#inittTotalPages').val() ? parseInt($('#inittTotalPages').val()) : 1,
    overt_total_pages: $('#overtTotalPages').val() ? parseInt($('#overtTotalPages').val()) : 1,
    usedt_total_pages: $('#usedtTotalPages').val() ? parseInt($('#usedtTotalPages').val()) : 1,
    initt_scroll_flag: true,
    usedt_scroll_flag: true,
    overt_scroll_flag: true,
    
};

function go_buy(obj) {
    if(global.qianbao && global.qianbao == 'qianbao') {
        location.href = global.base_url + global.home_url + '?qianbao=qianbao&agentViewFlag=' + global.agentViewFlag+"&v="+(new Date().getTime());
    } else {
        location.href = global.base_url + global.go_buy_url+"?v="+(new Date().getTime());
    }
    _this=$(obj)
    _this.find(".choose_icon").addClass("active_btn")
    setTimeout(function(){
    	_this.find(".choose_icon").removeClass("active_btn")
    },500)
}
function go_interes(){
	if(global.qianbao && global.qianbao == 'qianbao') {
        location.href = global.base_url + global.interes_url + '?qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
    } else {
        location.href = global.base_url + global.interes_url;
    }
}
$(function() {

    function toggleText(page_num, status) {
        if(constants.status.used == status) {
            if(page_num >= global.used_total_pages) {
                $('.show_text').show();
            } else {
                $('.show_text').hide();
            }
        }
        if(constants.status.over == status) {
            if(page_num >= global.over_total_pages) {
                $('.show_text_over').show();
            } else {
                $('.show_text_over').hide();
            }
        }
    }
    toggleText(global.used_page_num, constants.status.used);
    toggleText(global.over_page_num, constants.status.over);


    function loadPage(status) {
        if(constants.status.init == status) {
            global.init_page_num ++;
            loadContents("RED_PACKET","INIT");
        }
        if(constants.status.over == status) {
            global.over_page_num ++;
            loadContents("RED_PACKET","OVER");
        }
        if(constants.status.used == status) {
            global.used_page_num ++;
            loadContents("RED_PACKET","USED");
        }
        if(constants.status.initt == status) {
            global.initt_page_num ++;
            loadContents("INTEREST_TICKET","INIT");
        }
        if(constants.status.overt == status) {
            global.overt_page_num ++;
            loadContents("INTEREST_TICKET","OVER");
        }
        if(constants.status.usedt == status) {
            global.usedt_page_num ++;
            loadContents("INTEREST_TICKET","USED");
        }
    }
    //下拉分页
    $(window).scroll(function(){
        var totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 10) ;
        if(doc <= totalHeight) {
            if(global.current_status == constants.status.init) {
                if(global.init_scroll_flag) {
                    loadPage(global.current_status);
                }
            } else if(global.current_status == constants.status.used) {
                if(global.used_scroll_flag) {
                    loadPage(global.current_status);
                }
            } else if(global.current_status == constants.status.over){
                if(global.over_scroll_flag) {
                    loadPage(global.current_status);
                }
            }else if(global.current_status == constants.status.initt) {
                if(global.initt_scroll_flag) {
                    loadPage(global.current_status);
                }
            } else if(global.current_status == constants.status.usedt) {
                if(global.usedt_scroll_flag) {
                    loadPage(global.current_status);
                }
            } else {
                if(global.overt_scroll_flag) {
                    loadPage(global.current_status);
                }
            }
        }
    });

    function loadContents(type,sta) {
        var page_num = 1;
        var current_document = '.init_ul';
        if(type=="RED_PACKET"){
        	if(global.current_status == constants.status.init) {
                page_num = global.init_page_num;
                current_document = '.init_ul';
            } else if(global.current_status == constants.status.used) {
                page_num = global.used_page_num;
                current_document = '.used_ul';
            } else {
                page_num = global.over_page_num;
                current_document = '.over_ul';
            }
        }else{
        	if(global.current_status == constants.status.initt) {
                page_num = global.initt_page_num;
                current_document = '.initt_ul';
            } else if(global.current_status == constants.status.usedt) {
                page_num = global.usedt_page_num;
                current_document = '.usedt_ul';
            } else {
                page_num = global.overt_page_num;
                current_document = '.overt_ul';
            }
        }
        $.ajax({
            url: global.base_url + global.red_packet_list_url,
            data:{
                status: sta,
                pageNum: page_num,
                type:type
            },
            success: function(data) {
                $(current_document).append(data);
                if(global.current_status == constants.status.init) {
                    if (global.init_page_num >= global.init_total_pages || global.init_total_pages == 1) {
                        toggleText(global.init_page_num, global.current_status);
                        global.init_scroll_flag = false;
                    }
                } else if(global.current_status == constants.status.used) {
                    if (global.used_page_num >= global.used_total_pages || global.used_total_pages == 1) {
                        toggleText(global.used_page_num, global.current_status);
                        global.used_scroll_flag = false;
                    }
                } else {
                    if (global.over_page_num >= global.over_total_pages || global.over_total_pages == 1) {
                        toggleText(global.over_page_num, global.current_status);
                        global.over_scroll_flag = false;
                    }
                }
            },
            error: function(data) {
                if(global.current_status == constants.status.init) {
                    global.init_scroll_flag = true;
                    global.init_page_num --;
                } else if(global.current_status == constants.status.used) {
                    global.over_page_num --;
                    global.used_scroll_flag = true;
                } else {
                    global.used_page_num --;
                    global.over_scroll_flag = true;
                }
                if(data.resMsg) {
                    drawToast(data.resMsg);
                } else {
                    drawToast("港湾航道堵塞，稍后再试吧~");
                }
            }
        });
    }

    //tab
    $('.classify>.fy_dl').click(function() {
		$(this).addClass('fy_dlclick').find('.fy-line').addClass('fy-line-active').end().siblings().removeClass('fy_dlclick').find('.fy-line').removeClass('fy-line-active');
        $(".null_box > .null_img").stop().hide().eq($('.classify>.fy_dl').index(this)).stop().show();        
        global.current_status = $(this).attr('status');
    });
    //已使用红包
    $(".red_USED").on("click",function(){
    	if(global.qianbao && global.qianbao == 'qianbao') {
            location.href = global.base_url+global.go_url+global.red_USER + '&qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
        } else{
        	location.href=global.base_url+global.go_url+global.red_USER;
        }   	
    })
    //已过期红包
    $(".red_OVER").on("click",function(){
    	if(global.qianbao && global.qianbao == 'qianbao') {
            location.href = global.base_url+global.go_url+global.red_OVER + '&qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
        } else{
        	location.href=global.base_url+global.go_url+global.red_OVER;
        }    	
    })
    //已使用加息券
    $(".ticket_USED").on("click",function(){
    	if(global.qianbao && global.qianbao == 'qianbao') {
            location.href = global.base_url+global.go_url+global.ticket_USER + '&qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
        } else{
        	location.href=global.base_url+global.go_url+global.ticket_USER;
        }    	
    })
    //已过期加息券
    $(".ticket_OVER").on("click",function(){
    	if(global.qianbao && global.qianbao == 'qianbao') {
            location.href = global.base_url+global.go_url+global.ticket_OVER + '&qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
        } else{
        	location.href=global.base_url+global.go_url+global.ticket_OVER;
        }    	
    })
    //加息券弹窗
	  var productLimit=""
	$(".ticket_btn").click(function(){
		$(".dialog_notice").addClass("alert_show").removeClass("alert_hide");
		$(this).next(".alert_info").stop().show();
		productLimit=$(this).find("#productLimit").val();
		var arr=productLimit.split(",");
		var array=[];
		for(var i=0;i<arr.length;i++){
			if(arr[i]=="BIGANGWAN_SERIAL"){
				array.push('港湾系列')
			}
			if(arr[i]=="YONGJIN_SERIAL"){
				array.push('涌金系列')
			}
			if(arr[i]=="KUAHONG_SERIAL"){
				array.push('跨虹系列')
			}
			if(arr[i]=="BAOXIN_SERIAL"){
				array.push('保信系列')
			}
		}
		var str=array.join("， ");
		if(arr.length>=4){
			$(".bgw_name").text("全部系列")
		}else{
			$(".bgw_name").text(str)
		}		
	})
	$(".close_btn").click(function(){
		$(".dialog_notice").addClass("alert_hide").removeClass("alert_show");
		$(".alert_info").stop().hide();
		productLimit="";
	})
});