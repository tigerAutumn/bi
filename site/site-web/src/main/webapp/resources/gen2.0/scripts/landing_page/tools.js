/**
 * @description [ 工具类 ]
 * 
 * @authors 台拉曼却 (1643157898@qq.com)
 * @date 2015-08-27 09:35:26
 * @version $1.1$
 */
var MoneyUtil = MoneyUtil || {}; // 金钱工具类
var FormCheckUtil = FormCheckUtil || {}; // 表单校验工具类
var DateUtil = DateUtil || {}; // 日期工具类
var ObjectUtil = ObjectUtil || {}; // 对象工具类
var QQUtil = QQUtil || {}; // QQ工具类
var WindowUtil = WindowUtil || {} // 窗口工具类

/*
 * ========================================== MoneyUtil
 * =============================================
 */
MoneyUtil.formatMoney = function(money, places, thousand, decimal) {
	places = !isNaN(places = Math.abs(places)) ? places : 2;
	thousand = thousand || ",";
	decimal = decimal || ".";
	var number = money, negative = number < 0 ? "-" : "", i = parseInt(
			number = Math.abs(+number || 0).toFixed(places), 10)
			+ "", j = (j = i.length) > 3 ? j % 3 : 0;
	return negative
			+ (j ? i.substr(0, j) + thousand : "")
			+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand)
			+ (places ? decimal + Math.abs(number - i).toFixed(places).slice(2)
					: "");
}
/*
 * ========================================== MoneyUtil
 * =============================================
 */

/*
 * ========================================== WindowUtil
 * =============================================
 */
WindowUtil.open = function(url, name, height, width) {
	var top = (window.screen.availHeight - 30 - height) / 2; // 获得窗口的垂直位置;
	var left = (window.screen.availWidth - 10 - width) / 2; // 获得窗口的水平位置;
	var toolbar = 'no';
	var menubar = 'no';
	var scrollbars = 'no';
	var resizable = 'no';
	var location = 'no';
	var status = 'no';
	window.open(url, // 弹出窗口的文件URL
	name, // 窗口名，可为空
	"height=" + height + ", width=" + width + ", top=" + top + ", left=" + left
			+ ", toolbar =" + toolbar + ", menubar=" + menubar
			+ ", scrollbars=" + scrollbars + ", resizable=" + resizable
			+ ", location=" + location + ", status=" + status // parameters
	);
}

/*
 * ========================================== QQUtil
 * =============================================
 */
QQUtil.getGTK = function(str) {
	var hash = 5381;
	for ( var i = 0, len = str.length; i < len; ++i) {
		hash += (hash << 5) + str.charAt(i).charCodeAt();
	}
	return hash & 0x7fffffff;
}

/*
 * ========================================== ObjectUtil
 * =============================================
 */

/*
 * ========================================== FormCheckUtil
 * =============================================
 */

FormCheckUtil.onlyNumber2Point = function(input_obj) {
	input_obj.value = input_obj.value.replace(/^\d{3}\.\d{2}$/, '');
}

/**
 * 【文本框限制】只能输入正整数（D的大小写是有区别的，不要写成d）
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非正整数
 */
FormCheckUtil.onlyInteger = function(input_obj) {
	input_obj.value = input_obj.value.replace(/\D/g, '');
}

/**
 * 【文本框限制】只能输入正数字（正整数、正浮点数）
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非正整数、正浮点数
 */
FormCheckUtil.onlyPositiveNum = function(input_obj) {
	input_obj.value = input_obj.value.match(/\d{1,}\.{0,1}\d{0,}/);
}

/**
 * 【文本框限制】只能输入汉字
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非汉字
 */
FormCheckUtil.onlyChinese = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\u4e00-\u9fa5]+/, '');
};

/**
 * 【文本框限制】只能输入英文字母
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非英文字母
 */
FormCheckUtil.onlyEnglish = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z]/g, '');
}

/**
 * 【文本框限制】只能输入大写英文字母
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非大写英文字母
 */
FormCheckUtil.onlyUpperEnglish = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\A-\Z]/g, '');
}

/**
 * 【文本框限制】只能输入小写英文字母
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非小写英文字母
 */
FormCheckUtil.onlyLowerEnglish = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\a-\z]/g, '');
}

/**
 * 【文本框限制】只能输入英文字母和数字
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非英文字母和数字
 */
FormCheckUtil.onlyEnglishAndNum = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9]/g, '');
}

/**
 * 【文本框限制】只能输入大写英文字母和数字
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非大写英文字母和数字
 */
FormCheckUtil.onlyUpperEnglishAndNum = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\A-\Z0-9]/g, '');
}

/**
 * 【文本框限制】只能输入中文、英文字母和正整数
 * 
 * @param {[type]}
 *            input_obj 文本框对象
 * @return {[type]} 删除文本框中非大写英文字母和正整数
 */
FormCheckUtil.onlyChineseEnglishAndInteger = function(input_obj) {
	input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,
			'');
}

/*
 * ========================================== DateUtil
 * =============================================
 */

/**
 * 是否是闰年
 * 
 * @returns {Boolean}
 */
DateUtil.isLeapYear = function(date) {
	var flag = false;
	if ((date.getYear() % 4 == 0 && date.getYear() % 100 != 0)
			|| (date.getYear() % 400 == 0)) {
		flag = true;
	}
	return flag;
}