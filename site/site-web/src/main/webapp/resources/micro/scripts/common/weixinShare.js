
$(function () {
    var title = $('#title').val();
    var desc = $('#desc').val();
    var link = $('#link').val();
    var imgUrl = $('#imgUrl').val();
    
    if(link == ""){
    	link = location.href.split('#')[0];
    }
    
    wx.ready(function () {
    	wx.hideOptionMenu();
        shareFriend(title, desc, link, imgUrl);
        shareTimeline(title, desc, link, imgUrl);
        shareWeibo(title, desc, link, imgUrl);
        shareQQ(title, desc, link, imgUrl);
        $(function(){
        	wx.showOptionMenu();
        });
        
    });
    
    $("#timeline").click(function(){
    	shareTimeline(title, desc, link, imgUrl);
    })
    
    $("#friend").click(function(){
    	shareFriend(title, desc, link, imgUrl);
    })
    
    $("#copy").click(function(){
    	 var e=document.getElementById("link");//对象是content 
         e.select(); //选择对象 
         document.execCommand("Copy"); //执行浏览器复制命令
		drawToast("复制成功！");
	})
});

// 分享给朋友
function shareFriend(title, desc, link, imgUrl) {
    wx.onMenuShareAppMessage({
        title: title,
        desc: desc,
        link: link,
        imgUrl: imgUrl,
        success: function (data) {
        	var root = $("#app_root_path_share").val();
        	$.ajax({
        		url: root + "/micro2.0/more/newUserAddProductLimit",
        		type: 'post',
        		success: function(data){
        		},
        		error: function(data){
        		}
        	});
            thanksgiving(root);
        },
        cancel: function (data) {
        }
    })
}
// 分享到朋友圈
function shareTimeline(title, desc, link, imgUrl) {
    wx.onMenuShareTimeline({
        title: title,
        link: link,
        imgUrl: imgUrl,
        success: function () {
        	var root = $("#app_root_path_share").val();
        	$.ajax({
        		url: root + "/micro2.0/more/newUserAddProductLimit",
        		type: 'post',
        		success: function(data){
        		}
        	});
            thanksgiving(root);
        },
        cancel: function () {
        }
    })
}
function shareWeibo(title, desc, link, imgUrl) {
    wx.onMenuShareWeibo({
        title: title,
        desc: desc,
        link: link,
        imgUrl: imgUrl,
        success: function () {
        	var root = $("#app_root_path_share").val();
        	$.ajax({
        		url: root + "/micro2.0/more/newUserAddProductLimit",
        		type: 'post',
        		success: function(data){
        		}
        	});
        },
        cancel: function () {
        }
    })
}
function shareQQ(title, desc, link, imgUrl) {
    wx.onMenuShareQQ({
        title: title,
        desc: desc,
        link: link,
        imgUrl: imgUrl,
        success: function () {
        	var root = $("#app_root_path_share").val();
        	$.ajax({
        		url: root + "/micro2.0/more/newUserAddProductLimit",
        		type: 'post',
        		success: function(data){
        		}
        	});
        },
        cancel: function () {
        }
    })
}

function invokeToFriend() {
   // if (window.is_in_weixin) {
        popupNavigation('发送给指定好友/群')
   // } else {
   //     alert('请在微信中打开该链接，发送给指定好友/群。')
   // }
}
function invokeToMoment() {
   // if (window.is_in_weixin) {
        popupNavigation('分享到朋友圈')
   // } else {
   //     alert('请在微信中打开该链接，分享到朋友圈。')
   // }
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

function hideSubscribe(a) {
    if (a.target == $('#imgcode') [0]) {
        return
    }
    $('body').unbind('touchmove');
    if ($('#subscribe_mask').length > 0) {
        $('#subscribe_mask').css('display', 'none')
    }
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
function hideNavigation() {
    if ($('#navi_mask').length > 0) {
        $('#navi_mask').css({
            display: 'none'
        })
    }
}

/**
 * 选择图片
 */
function userChooseImage(obj) {
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有['original', 'compressed']
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            var target = $(obj).attr('target');
            if(localIds) {
                for (var index in localIds) {
                    userUploadImage(localIds[index], target);
                }
                $('.' + target).attr('src', localIds[0]);
                $('.' + target).parent('span').show();
            }
        },
        cancel: function () {
        }
    });
}

/**
 * 上传图片
 */
function userUploadImage(localId, target) {
    wx.uploadImage({
        localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
            var serverId = res.serverId; // 返回图片的服务器端ID
            $('#' + target).val(serverId);
        }
    });
}


function thanksgiving(root){
    var thanksgivingConstant = $('#thanksgivingConstant').val();
    if('thanksgiving' == thanksgivingConstant) {
        $.ajax({
            url: root + "/micro2.0/activity/thanksgiving/share",
            type: 'post',
            success: function(data){
                if(data.code == "000000") {
                    drawToast('分享成功');
                    setTimeout(function() {
                        location.href = root + '/micro2.0/activity/thanksgiving/first';
                    }, 2000);
                } else {
                    drawToast(data.message);
                }
            },
            error: function(data){
                drawToast('失败');
            }
        });
    }
}