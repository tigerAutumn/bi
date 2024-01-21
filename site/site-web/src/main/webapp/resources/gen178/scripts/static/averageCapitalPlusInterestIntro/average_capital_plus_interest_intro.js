window.onload = function() {
	var oUl = document.getElementById('main_com');
	var aLi = oUl.getElementsByTagName('div');
	var oDiv = document.getElementById('mth_ul');
	var aImg = oDiv.getElementsByTagName('li');
	var aImg1 = getclassName(oDiv,'b_img');

	var aA1 = document.getElementById('mth_btnleft');
	var aA2 = document.getElementById('mth_btnright');
	oUl.innerHTML += oUl.innerHTML
	var i = 0
	var iZindex = 1;
	//除了第一张图，透明度其余全变成0
	for (j = 0; j < aLi.length; j++) {
		if (j == i) {
			continue;
		}

		aLi[j].style.display = 'none'

	}

	for (j = 0; j < aImg.length; j++) {
		aImg[j].index = j
		aImg[j].onmouseover = function() {
			i = this.index - 1
			move()
		}
	}

	function move() {
		var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
		i++
		for (j = 0; j < aImg.length; j++) {
			aImg[j].style.background = 'url('+APP_ROOT_PATH+'/resources/gen178/images/static/averageCapitalPlusInterestIntro/mth3.png) no-repeat center'
			aImg1[j].style.display = 'none'
		}
		if (i == aImg.length) {

			aImg[0].style.background = 'url('+APP_ROOT_PATH+'/resources/gen178/images/static/averageCapitalPlusInterestIntro/mth2.png) no-repeat center'
			aImg1[0].style.display = 'block'
		} else {

			aImg[i].style.background = 'url('+APP_ROOT_PATH+'/resources/gen178/images/static/averageCapitalPlusInterestIntro/mth2.png) no-repeat center'
			aImg1[i].style.display = 'block'
		}
		if (i >= aLi.length / 2) {
			i = 0
		}
		iZindex++ //9999  
		aLi[i].style.zIndex = iZindex;
		aLi[i].style.display = 'block'
		clear()

	}

	function clear() {

		for (j = 0; j < aLi.length; j++) {
			if (j == i) {
				continue;
			}

			aLi[j].style.display = 'none'
		}
	}

	aA1.onclick = function() {
		if (i == 0) {
			i = aLi.length / 2 - 2
		} else {
			i = i - 2
		}
		move()
	}
	aA2.onclick = function() {
		move()
	}
}
function getclassName(obj,attr){
	var aElem = obj.getElementsByTagName('*')
	//获取父元素下面所有子元素
	var arr = []
	//定义一个数组，用来存放子元素
	for(var i=0;i<aElem.length;i++){
		if(aElem[i].className==attr){
			arr.push(aElem[i])
			}
		}
		return arr;
  }


function clickProductList(){
	location.href = $("#APP_ROOT_PATH").val() + "/gen178/regular/list";
}
function clickRegister(){
	var userIsLogin = $("#userIsLogin").val();
	if(userIsLogin == null || userIsLogin == "" || userIsLogin == "no"){
		location.href = $("#APP_ROOT_PATH").val() + "/gen178/user/register_first_new_index";
	}else{
		drawToast("您已经登录币港湾！");
	}
}