window.onload = function() {
	mainer()
	

}

function mainer() {
	var omainer = document.getElementById('mainer_center')
	var obtn = omainer.getElementsByTagName('a')
	var ologin = document.getElementById('hike_login_copy')
	var oly = document.getElementById('ly')
	var ologin_btn = document.getElementById('login_btn')
	for (var i = 0; i < obtn.length; i++) {
		obtn[i].onclick = function() {
			oly.style.display = "block";
			ologin.style.display = 'block'
		}
	}
}
