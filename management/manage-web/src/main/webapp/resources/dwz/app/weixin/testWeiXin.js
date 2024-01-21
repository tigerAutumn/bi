// JavaScript Document
var menu1 = 2;
var menu2 = 2;
var menu3 = 2;
var key = 1;
var menuName = "";
function addRow(row) {
	// 先拿到当前页面上的table
	if (key == 1) {
		menu1 = document.getElementById("menuSize1").value;
		menu2 = document.getElementById("menuSize2").value;
		menu3 = document.getElementById("menuSize3").value;
		key = 0;
	}

	switch (row) {

	case 'addMenu1':
		menu1++;
		var i = menu1;

		menuName = "subMenu1";
		if (menu1 > 6) {
			break;
		}

		break
	case 'addMenu2':
		menu2++;
		var i = menu2;
		menuName = "subMenu2";
		if (menu2 > 6) {
			break;
		}

		break;
	case 'addMenu3':
		menu3++;
		var i = menu3;
		menuName = "subMenu3"
		if (menu3 > 6) {
			break;
		}

		break;
	}

	if (i > 6) {
		alert("该菜单已经有五个子菜单无法添加！");
		return;
	}

	var index = document.getElementById(row).rowIndex;
	var table = document.getElementById("weixin");

	var rowLength = table.rows.length;

	var newRow = table.insertRow(index);

	newRow.id = menuName + i.toString();
	var c1 = newRow.insertCell(0);
	c1.innerHTML = "<input class='delete' type='button'  onclick=deleteRow('"
			+ row + "'," + i + ") value='删除'/>";

	var c2 = newRow.insertCell(1);
	c2.innerHTML = "<div class='subnode'><input class='name' name=" + menuName
			+ "Name" + i + " type='text' value=''></div>";

	var c1 = newRow.insertCell(2);
	c1.innerHTML = "<input class='url' name=" + menuName + "Url" + i
			+ " type='text' value=''>";

	i++;

}

function deleteRow(row, num) {

	switch (row) {

	case 'addMenu1':
		var i = menu1;
		menu1--;
		menuName = "subMenu1"
		break
	case 'addMenu2':
		var i = menu2;
		menuName = "subMenu2"
		menu2--;
		break;
	case 'addMenu3':
		var i = menu3;
		menuName = "subMenu3"
		menu3--;
		break;
	}

	var row = menuName + num.toString();

	// 注意点
	var index = document.getElementById(row).rowIndex;

	document.getElementById("weixin").deleteRow(index);

}
