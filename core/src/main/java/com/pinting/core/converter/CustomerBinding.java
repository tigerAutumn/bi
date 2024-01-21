package com.pinting.core.converter;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class CustomerBinding implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		dateBinding(binder);

		intBinding(binder);

		doubleBinding(binder);
	}

	private void doubleBinding(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, new CustomNumberEditor(
				Float.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(
				Double.class, true));
		binder.registerCustomEditor(Float.TYPE, new CustomNumberEditor(
				Float.class, true));
		binder.registerCustomEditor(Double.TYPE, new CustomNumberEditor(
				Double.class, true));
	}

	private void intBinding(WebDataBinder binder) {
		binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(
				BigInteger.class, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(
				Long.class, true));
		binder.registerCustomEditor(Integer.TYPE, new CustomNumberEditor(
				Integer.class, true));
		binder.registerCustomEditor(Long.TYPE, new CustomNumberEditor(
				Long.class, true));
	}

	private void dateBinding(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
