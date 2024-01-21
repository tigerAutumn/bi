zui = function() {};

/* util */
zui.util = function() {};
zui.util.ajax = function() {};

zui.widgets = function() {};
zui.widgets.Dialog = function() {};
zui.widgets.Modal = function() {};

/**
 * 产生一个uuid
 * 
 * @function $UUID 
 * @param {String} prefix 前缀字符串
 * @return {String} 返回UUID
 */
var $UUID = function(prefix) {
	var t = new Date().getTime();
	return (!prefix?'':prefix)+t+''+Math.floor(Math.random()*t);
};

/**
 * 监听用户强制关闭窗口事件
 * 
 * @function $OnBeforeClose 
 * @param {Function} obv
 */
var $OnBeforeClose = function(obv){
	if(!obv) return;
	xui.util.Event.on(window,'beforeunload',function(){
		if(event.clientY<0||event.altKey){   
        	obv.call(window);
    	}
	},null,true);	
}

var __OBSERVE={};

/**
 * 事件函数监听
 * 
 * @function $Listen 
 * @param {String} e 事件名
 * @param {Object} callback 监听函数
 */
var $Listen = function(e,callback){
	__OBSERVE[e]=callback;
};

/**
 * 事件函数触发
 * 
 * @function $Fire 
 * @param {String} e 事件名
 */
var $Fire = function(e){
	var callback = __OBSERVE[e];
	if(callback) callback.call(window, arguments);
};

/**
 * 事件函数注销监听
 * 
 * @function $Unlisten 
 * @param {Object} e
 */
var $Unlisten = function(e){
	__OBSERVE[e]=null;
};