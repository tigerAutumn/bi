//百度地图API功能
function loadJScript() {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=wwyqHpGRQfODl8e7MtfjgSNx&callback=init";
	document.body.appendChild(script);
}

function initTools(){
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	this.addControl(top_left_control);        
	this.addControl(top_left_navigation);     
	this.addControl(top_right_navigation);  
}

function inputPrompt(){
	var that = this;
	function G(id) {
		return document.getElementById(id);
	}
	
	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : "suggestId"
		,"location" : this
	});

	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
		var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		G("searchResultPanel").innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		setPlace.apply(that);
	});

	function setPlace(){
		var that = this;
		that.clearOverlays();    //清除地图上所有覆盖物
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
			that.centerAndZoom(pp, 18);
			addDragMark(that,pp);
		}
		var local = new BMap.LocalSearch(that, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}
	
}

function addDragMark(map,point){
	var marker = new BMap.Marker(point);
	setLng_Lat(marker);
	marker.addEventListener("dragend",function(e){
		setLng_Lat(marker);
	});
	map.addOverlay(marker);    //添加标注
	marker.enableDragging();
	
	function setLng_Lat(marker){
		var p = marker.getPosition();  //获取marker的位置
		$("#projectLongitude").val(p.lng);
		$("#projectLatitude").val(p.lat);
	}
}

function setLocation(){
	var lg = $("#projectLongitude").val(),la = $("#projectLatitude").val();
	if(lg != "" && la != ""){
		this.clearOverlays(); 
		var new_point = new BMap.Point(lg,la);
		addDragMark(this,new_point);   
		this.panTo(new_point);
	}else{
		var that = this;
		function myFun(result){
			var cityName = result.name;
			that.setCenter(cityName);
		}
		var myCity = new BMap.LocalCity();
		myCity.get(myFun);
	}
}

var functions = [initTools,inputPrompt,setLocation];

function init() {
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	map.enableScrollWheelZoom(true);
	//不加timeOut可能出现根据经纬度定位不到的情况
	setTimeout(function(){
		var fl = functions.length;
		for(var i=0;i<fl;i++){
			functions[i].apply(map);
		}
	},500);
}  
loadJScript();