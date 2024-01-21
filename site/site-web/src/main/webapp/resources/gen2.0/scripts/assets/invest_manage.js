// ============================================================ 数据 ========================================================================
var constants = {
	plan_type: {
		regular: 'FINISH_RETURN_ALL', //港湾计划（定期产品）
		stages: 'AVERAGE_CAPITAL_PLUS_INTEREST' //赞分期计划（分期产品）
	},
	//计划状态
	status: {
		holding: 'HOLDING',  //持有中
		entrusting: 'ENTRUSTING',  //委托中
		finish: 'FINISH' //已完成
	}
};
var global = {
	base_url : $('#APP_ROOT_PATH').val(),
	invest_manage_url: '/gen2.0/assets/invest_manage_page',	  //加载投资管理分页列表URL
	invest_manage_init_url: '/gen2.0/assets/invest_manage',   //加载投资管理页面URL
	//页码
	regular_holding_page_num: $('#bgwPageNum').val() ? parseInt($('#bgwPageNum').val()) : 1,  // 定期计划持有中页码
	regular_finish_page_num: $('#bgwPageNum').val() ? parseInt($('#bgwPageNum').val()) : 1,  // 定期计划已完成页码
	stages_holding_page_num: $('#zanPageNum').val() ? parseInt($('#zanPageNum').val()) : 1,  // 分期计划持有中页码
	stages_entrusting_page_num: $('#zanPageNum').val() ? parseInt($('#zanPageNum').val()) : 1,  // 分期计划委托中页码
	stages_finish_page_num: $('#zanPageNum').val() ? parseInt($('#zanPageNum').val()) : 1,  // 分期计划已完成页码
	//定期计划总页数
	regular_total_pages: $('#bgwTotalPages').val() ? parseInt($('#bgwTotalPages').val()) : 0, // 当前定期计划总页数
	regular_hold_total_pages: $('#bgwHoldTotalPages').val() ? parseInt($('#bgwHoldTotalPages').val()) : 0,	// 当前定期计划持有中总页数
	regular_finish_total_pages: $('#bgwFinishTotalPages').val() ? parseInt($('#bgwFinishTotalPages').val()) : 0, // 当前定期计划已完成总页数
	//委托计划计划总页数
	stages_total_pages: $('#zanTotalPages').val() ? parseInt($('#zanTotalPages').val()) : 0,  // 当前分期计划总页数
	stages_hold_total_pages: $('#zanHoldTotalPages').val() ? parseInt($('#zanHoldTotalPages').val()) : 0,	// 当前分期计划持有中总页数
	stages_finish_total_pages: $('#zanFinishTotalPages').val() ? parseInt($('#zanFinishTotalPages').val()) : 0,	// 当前分期计划已完成总页数
	stages_entrust_total_pages: $('#zanEntrustTotalPages').val() ? parseInt($('#zanEntrustTotalPages').val()) : 0,	// 当前分期计划委托中总页数
};

// ============================================================ 函数 ========================================================================

/**
 * 获得总页数
 * @param planType		计划类型
 * @param status		计划状态
 * @returns {number}	总页码
 */
function getTotalPages(planType, status) {
	var totalPages = 0;
	if(constants.plan_type.regular == planType) {
		if(constants.status.holding == status) {
			totalPages = global.regular_hold_total_pages;
		} else if(constants.status.finish == status) {
			totalPages = global.regular_finish_total_pages;
		}
	} else if(constants.plan_type.stages == planType) {
		if(constants.status.holding == status) {
			totalPages = global.stages_hold_total_pages;
		} else if(constants.status.entrusting == status) {
			totalPages = global.stages_entrust_total_pages;
		} else if(constants.status.finish == status) {
			totalPages = global.stages_finish_total_pages;
		}
	}
	return totalPages;
}

/**
 * 获得当前页码
 * @param planType		计划类型
 * @param status		计划状态
 * @returns {number}	当前页码
 */
function getPage(planType, status) {
	var page = 0;
	if(constants.plan_type.regular == planType) {
		if(constants.status.holding == status) {
			page = global.regular_holding_page_num;
		} else if(constants.status.finish == status) {
			page = global.regular_finish_page_num;
		}
	} else if(constants.plan_type.stages == planType) {
		if(constants.status.holding == status) {
			page = global.stages_holding_page_num;
		} else if(constants.status.entrusting == status) {
			page = global.stages_entrusting_page_num;
		} else if(constants.status.finish == status) {
			page = global.stages_finish_page_num;
		}
	}
	return page;
}

/**
 * 设置页码
 * @param planType	计划类型
 * @param status	计划状态
 * @param space		对页码添加几页或者减少几页
 */
function setPage(planType, status, space) {
	if(constants.plan_type.regular == planType) {
		if(constants.status.holding == status) {
			global.regular_holding_page_num = global.regular_holding_page_num + space;
		} else if(constants.status.finish == status) {
			global.regular_finish_page_num = global.regular_finish_page_num + space;
		}
	} else if(constants.plan_type.stages == planType) {
		if(constants.status.holding == status) {
			global.stages_holding_page_num = global.stages_holding_page_num + space;
		} else if(constants.status.entrusting == status) {
			global.stages_entrusting_page_num = global.stages_entrusting_page_num + space;
		} else if(constants.status.finish == status) {
			global.stages_finish_page_num = global.stages_finish_page_num + space;
		}
	}
}

/**
 * 下一页
 * @param planType	计划类型
 * @param status	计划状态
 * @param space		对页码添加几页或者减少几页
 */
function nextPage(planType, status, space) {
	var totalPages = getTotalPages(planType, status);
	var nextPage = getPage(planType, status) + space;
	if(!isNaN(totalPages)) {
		totalPages = parseInt(totalPages);
	}
	if(nextPage > totalPages) {
		drawToast("当前页已经是尾页");
		return;
	}
	loadContents(planType, status, space);
}

/**
 * 上一页
 * @param planType	计划类型
 * @param status	计划状态
 * @param space		对页码添加几页或者减少几页
 */
function prePage(planType, status, space) {
	var prePage = getPage(planType, status) + space;
	if(prePage <= 0) {
		drawToast("当前页已经是首页");
		return;
	}
	loadContents(planType, status, space);
}

/**
 * 首页
 * @param planType	计划类型
 * @param status	计划状态
 */
function homePage(planType, status) {
	var page = getPage(planType, status);
	var space = page - 1;
	loadContents(planType, status, -space);
}

/**
 * 尾页
 * @param planType	计划类型
 * @param status	计划状态
 */
function endPage(planType, status) {
	var page = getPage(planType, status);
	var totalPages = getTotalPages(planType, status);
	var space = totalPages - page;
	loadContents(planType, status, space);
}

/**
 * 加载页面
 * @param planType	计划类型
 * @param status	计划状态
 * @param space		对页码添加几页或者减少几页
 */
function loadContents(planType, status, space) {
	openDrawDiv("正在努力加载数据中，请稍候。");
	setPage(planType, status, space);
	var pageNum = getPage(planType, status);
	$.ajax({
		url: global.base_url + global.invest_manage_url,
		data:{
			investStatus: status,
			pageNum: pageNum,
			returnType : planType
		},
		success: function (data) {
			if(constants.plan_type.regular == planType) {
				$('.regular_plan_list').html(data);
			} else if(constants.plan_type.stages == planType) {
				$('.stages_plan_list').html(data);
			}
			closeDrawDiv();
		},
		error: function () {
			setPage(planType, status, -space);
			closeDrawDiv();
			drawToast("币港湾网络堵塞，请稍后再试");
		}
	});
}

/**
 * 一级tab切换
 * @param index	tab页码
 */
function first_tabs_change(index) {
	switch (index) {
		case 1:
			// 展示定期计划tab页面
			$('.stages_plan').removeClass('invest_show').addClass('invest_hide');
			$('.regular_plan').removeClass('invest_hide').addClass('invest_show');
			$('#stages_plan').removeClass('data_label_focuse');
			$('#regular_plan').addClass('data_label_focuse');
			$('.regular_hold').click();
			break;
		case 2:
			// 展示分期计划tab页面
			$('.regular_plan').removeClass('invest_show').addClass('invest_hide');
			$('.stages_plan').removeClass('invest_hide').addClass('invest_show');
			$('#regular_plan').removeClass('data_label_focuse');
			$('#stages_plan').addClass('data_label_focuse');
			$('.stages_hold').click();
			break;
	}
}

/**
 * 二级tab切换
 * @param plan_type	计划类型
 * @param status	计划状态
 */
function second_tabs_change(plan_type, status, obj) {
	$(obj).parents('ul').children('li').removeClass('product_filter_focuse');
	$(obj).addClass('product_filter_focuse');
	loadContents(plan_type, status, 0);
}



// ============================================================ 事件 ========================================================================
// 一级tab切换 定期分期计划tab切换
$('#regular_plan').on('click', function () {
	first_tabs_change(1);
});
$('#stages_plan').on('click', function () {
	first_tabs_change(2);
});
// 二级tab切换 持有中、委托中、已完成
$('.regular_hold').on('click', function () {
	second_tabs_change(constants.plan_type.regular, constants.status.holding, this);
});
$('.regular_finish').on('click', function () {
	second_tabs_change(constants.plan_type.regular, constants.status.finish, this);
});
$('.stages_hold').on('click', function () {
	second_tabs_change(constants.plan_type.stages, constants.status.holding, this);
});
$('.stages_entrusting').on('click', function () {
	second_tabs_change(constants.plan_type.stages, constants.status.entrusting, this);
});
$('.stages_finish').on('click', function () {
	second_tabs_change(constants.plan_type.stages, constants.status.finish, this);
});
