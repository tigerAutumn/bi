/**
 * Cookie工具类
 * @namespace zui.util
 */
zui.util.Cookie = function() {
	return {
		
		/**
		 * 写Cookie键值
		 * @param {String} key 键
		 * @param {String} value 值
		 * @param {Date} oExpires 过期日期
		 * @param {Date} path 路径，缺省为根路径
		 */
		write: function(key, value, oExpires, path) {
			if(!key) return;
			var p = path?path : '/';
			var exp = oExpires?oExpires.toGMTString():'Tus, 2 Sep 2049 11:11:11 GMT';
			document.cookie = key + '=' + escape(''+value) + '; path=' +p+ '; expires=' + exp;
		},
		
		/**
		 * 读取Cookie键值
		 * @param {String} key 键
		 * @return {String} 值
		 */
		read: function(key) {
			var reg = new RegExp("(^| )" + key+"=([^;]*)(;|$)","gi");
			var v = document.cookie.replace(/(^\s*)|(\s*$)/g, '');
			var data = reg.exec(v);	
			return data? (data[2]==''?null:unescape(data[2])):null;
		},
		
		/**
		 * 清空Cookie键值
		 * @param {String} key
		 */
		clear: function(key) {
			this.write(key, '');
		}
	}
}();