/**
 * String具类
 * @namespace zui.util
 */

/**
 * 
 * 
 * @namespace zui.util
 * @singleton StringUtil
 */
zui.util.StringUtil = {
	
	/**
	 * 去掉字符串左右空格
	 */
	trim:function(str) {
		
		return (!str)?'':str.replace(/(^\s*)|(\s*$)/g,'');
	},
	
	/**
	 * 去掉左边空格
	 */
	leftTrim:function(str) {
		
		return (!str)?'':str.replace(/(^\s*)/g,''); 
	},
	
	/**
	 * 去掉右边空格
	 */
	rightTrim:function(str) {
		
		return (!str)?'':str.replace(/(\s*$)/g,'');
	},
	
	/**
	 * 获取字符串长度
	 */
	length:function(str) {
		
		return str.replace(/[^\x00-\xff]/g,'aaa').length; 
	},
	
	/**
	 * 检查邮箱格式
	 */
	isEmail:function(email) {
		var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|com|gov|mil|org|edu|int|name|asia)$";
		
		return (email.search(new RegExp(regu)) == -1)?false:true;
	}, 
	
	/**
	 * 检查字段是否为空
	 */
	isNull:function(str) {
		
		return (this.trim(str) == '')?true:false;
	},
	
	/**
	 * 检查手机号码
	 */
	isPhone:function(mobile) {
		var reg0 = /^13\d{5,9}$/;
		var reg1 = /^14\d{5,9}$/;
		var reg2 = /^15\d{5,9}$/;
		var reg3 = /^18\d{5,9}$/;
		
		var mo = false;
		if (reg0.test(mobile)) mo = true;
		if (reg1.test(mobile)) mo = true;
		if (reg2.test(mobile)) mo = true;
		if (reg3.test(mobile)) mo = true;
		
		return mo;
	},
	
	/**
	 * URL检查
	 */
	isURL:function(url) {
		var ex = /([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
		
		return (new RegExp(ex).test(url)==true)?true:false;
	},
	
	/**
	 * 获取字符串长度
	 */
	len:function(str) {
		var i;
		var sum = 0;
		for(i = 0; i < str.length; i++) {
			if ((str.charCodeAt(i) >= 0) && (str.charCodeAt(i) <= 255))
				sum = sum + 1;
			else
				sum = sum + 2;
		}
		
		return sum;
	},
	
	/**
	 * 检查身份证是否合法
	 */
	isIdCard:function(idCard) {
		var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
		var intStrLen = idCard.length;
		
		/* 检查身份证长度 */
		if ((intStrLen != 15) && (intStrLen != 18)) return false;
		
		/* 检查身份证格式 */
		var varArray = new Array();
		for(var i=0; i < intStrLen; i++) {
			varArray[i] = idCard.charAt(i);
			if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
				return false;
			} else if (i < 17) {
				varArray[i] = varArray[i]*factorArr[i];
			}
		}
		
		/* 检查身份证中日期 */
		if (intStrLen == 18) {
			var lngProduct = 0;
			for(var i=0; i < 17; i++) {
				lngProduct = lngProduct + varArray[i];
			}
			
			var intCheckDigit = 12 - lngProduct % 11;
			switch (intCheckDigit) {
				case 10:intCheckDigit = 'X';
					break;
				case 12:intCheckDigit = 1;
					break;
				case 11:intCheckDigit = 0;
					break;
				case 12:intCheckDigit = 1;
					break;
			}
			
			if (varArray[17].toUpperCase() != intCheckDigit) return false;
			
		} else {
			var date6 = idCard.substring(6,12);
			if (checkDate(date6) == false) return false;
		}
		
		return true;
	}
}