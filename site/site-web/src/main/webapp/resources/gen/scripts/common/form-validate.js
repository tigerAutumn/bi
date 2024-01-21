/**
 * 表单验证
 * @author dingpf
 **/
var formValidate = function() {
	return {
		// 表单验证开始
		validateForm:function (currentForm, formType){
			var formValidateFlag = true;
			// 隐藏所有错误信息
		    $('.formValidationError').hide();
			$('.fieldHasError').removeClass('fieldHasError');
			
			//遍历所有表单字段
			var fieldArray = $('.formFieldWrapper input, .formFieldWrapper select, .formFieldWrapper textarea, .formFieldWrapper checkbox, .formFieldWrapper radio');
			//校验开始
			formValidateFlag = formValidate.check(fieldArray,formValidateFlag);
			
	   		return formValidateFlag;
			
		},
		// 表单验证结束
	
		// 表单验证依赖函数 开始
		errorShow:function (tempField){
			$(tempField).focus();
			$(tempField).addClass('fieldHasError');
			//$(tempField + 'FieldError').fadeIn(300);
			var message = $(tempField + 'FieldError').text();
			drawToast(message);
		},
		regValidate:function (tempField, regField){
			var reg = new RegExp($(regField).val());
			if(!reg.test($(tempField).val())) {
				return false;
			}else{
				return true;
			}
		},
		check:function (fieldArray, formValidateFlag){
			for(var i=0; i<fieldArray.length; i++){
				var field = fieldArray[i];
				
				var tempField = '#' + $(field).attr('id');
				var regField = '#' + $(field).attr('id') + 'Reg';
				
				//不为空 校验 开始
				if($(field).hasClass('requiredField')){
					if($(field).val() == '' || $(field).val() == $(field).attr('data-placeholder')){
						$(field).val($(field).attr('data-placeholder'));	
						formValidateFlag = false;
						formValidate.errorShow(tempField);
						return formValidateFlag;
					};
				}
				//不为空 校验 结束
				
				//正则表达式校验开始
				if($(field).hasClass('regExpField')){
					formValidateFlag = formValidate.regValidate(tempField, regField);
					
					if(formValidateFlag == false){
						formValidate.errorShow(tempField);
						return formValidateFlag;
					}
				}
				//正则表达式校验结束
			}
			return true;
		}
		// 表单验证依赖函数 结束
	}
}();