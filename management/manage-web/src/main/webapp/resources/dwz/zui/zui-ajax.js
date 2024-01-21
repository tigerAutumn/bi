/**
 * Ajax异步提交工具类 注意：提交的服务器必须与当前使用页面同域名（跨域问题）。
 * 
 * @namespace zui.util
 * @singleton Ajax
 */
zui.util.Ajax = {
		
		/**
		 * 异步GET请求
		 * @param {String} url 服务器地址
		 * @param {Function} fun 回调函数
		 */
		asyncGET:function(url, fun) {
			var http = this._createHTTP();
			http.onreadystatechange = function() {
				if(http.readyState == 4) 
					zui.util.Ajax._callback(fun, http.responseText);
			};
			
			http.open('get',zui.util.Ajax._newURL(url),true);
			http.send(null);
		},
		
		/**
		 * 异步POST提交表单
		 * 
		 * @param {String} url 服务器地址
		 * @param {String|Object} f 表单ID或对象
		 * @param {Function} successFun 成功后回调函数
		 */
		asyncPOSTForm:function(url, f, successFun) {
			zui.util.Ajax._createLoading();
			
			var form = typeof f=='string'?document.getElementById(f):f;
			var id = $UUID('xaj_');
			zui.util.Ajax._iframeCallback[id] = successFun;
			
			if("Microsoft Internet Explorer" == navigator.appName) {
				var _layer = document.createElement('span');
				_layer.style.display = 'none';
				_layer.innerHTML = '<iframe id="'+id+'" name="'+id+'" src="javascript:false;" onload="zui.util.Ajax._iframeOnload(this.id);" style="width:0px;height:0px;"></iframe>';
			    document.body.appendChild(_layer);
			} else {
				var iframe = document.createElement("iframe");
				iframe.id=id;
				iframe.name=id;
				iframe.src="javascript:false;";
				iframe.style.width="0px";
				iframe.style.height="0px";
				iframe.allowtransparency="true";
				iframe.scrolling="no";
				iframe.frameborder="0";
				iframe.style.display = 'none'; 
				document.body.appendChild(iframe);
				iframe.onload = function() {
		    		zui.util.Ajax._iframeOnload(this.id);
		    	}
			}
			
			form.action=url;
			form.target=id;
			form.method='post';
			form.submit();
		},
		
		/**
		 * 同步submit提交表单
		 * 
		 * @param {String} url 服务器地址
		 * @param {String} f 表单ID
		 */
		submit:function(url, f) {
			zui.util.Ajax._createLoading();
			
			var form = typeof f=='string'?document.getElementById(f):f;
			if (url) form.action=url;
			
			form.method='post';
			form.submit();
		},
		
		/**
		 * 去除字符串两边空格
		 */
		_trim:function(str) {		
		    if(str && str.length > 0) {
				return str.replace(/(^\s*)|(\s*$)/g, '');
			}
		    
			return '';		
		}, 
		
		/**
		 * 创建背景层
		 */
		_createLoading:function() {
			var bgObj=document.createElement("div");
			bgObj.setAttribute('id','bgDiv');
			bgObj.style.top="0";
			bgObj.style.left="0";
			bgObj.style.right="0";
			bgObj.style.position="absolute";
			bgObj.style.opacity="0.1";
			bgObj.style.background="#CCCCCC";
			bgObj.style.width="100%";
			bgObj.style.height=(Math.max(document.body.scrollHeight,document.documentElement.scrollHeight)) + "px";
			bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=15";
			document.body.appendChild(bgObj);
		},
		
		_iframeCallback:{},
		
		_iframeOnload:function(id, type) {
			try {
				zui.util.Ajax._callback(zui.util.Ajax._iframeCallback[id], window.frames[id].document.body.innerHTML);
			} catch(e) {};
		},
		
		_createHTTP:function() {
			var http;
			try {
			     http = new XMLHttpRequest();
			} catch (e) {
				try {
			       http = new ActiveXObject("Msxml2.XMLHTTP");
			    } catch (e) {
			    	try {
			        	http = new ActiveXObject("Microsoft.XMLHTTP");
			        } catch (e) {
			        	http = null;
			        }  
			    }
			}
			
			return http;
		},
		
		_newURL:function(url) {
			if(url.indexOf('?')>0) {
				s = url+'&ts='+ new Date().getTime();
			} else {
				s = url+'?ts='+ new Date().getTime();
			}
			
			return s;
		},
		
		_callback:function(oFunction, res) {
		  	if(oFunction) oFunction.call(this,zui.util.Ajax._trim(res));
		  	
		  	try {
		  		document.body.removeChild(document.getElementById("bgDiv"));
		  	} catch(e){}
		}
}