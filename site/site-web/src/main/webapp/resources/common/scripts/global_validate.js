/**
 * 全局验证js
 * @author yanwl
 * @date 2015-11-12
 */
//js方法校验
var BGW = BGW || {};
(function($){
	BGW.check = {
    	//小写字母与数字
    	isnormal:function(str){
    		var reg = /^[a-z0-9]+$/;
    		return reg.test(str);
    	},
        //判断移动端
        isPhone: function(){
            var ua = navigator.userAgent.toLowerCase(),
                reg = /iPhone|iPad|Android|ucweb|windows\s+mobile|Windows\s+Phone/i;
                return reg.test(ua);
        },
        //IE6判定
        isIE6: function(){
        	return !!window.ActiveXObject && !window.XMLHttpRequest;
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
            var mobileReg = /^1[345678][0-9]{9}$/;
            return mobileReg.test(str);
        },
        // 判断固话
        isTelephone: function(str) {
            var phoneReg = /^0\d{2,3}-\d{5,9}$/;
            return phoneReg.test(str);
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
        },
        //判断包含中文
        isZn:function(str){
        	var znReg = /[\u4e00-\u9fa5]/g;
        	return znReg.test(str);
        },
        //判断是否为图片 传入的str为文件后缀
        isPic:function(str){
        	var strFilter=".tbi|.png|.bmp|.jpeg|.jpg|.gif";
        	str = str.toLowerCase();
        	if(strFilter.indexOf(str)>-1){
        		return true;
        	}else{
        		return false;
        	}
        },
        //判断是否是最多保留两位小数的数字
        isDecimal:function(str){
        	var decimalReg = /^-?\d+(\.\d{1,2})?$/;
        	return decimalReg.test(str);
        }
    }
})(jQuery);