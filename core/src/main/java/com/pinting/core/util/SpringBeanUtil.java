package com.pinting.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringBeanUtil implements BeanFactoryAware {

	private static BeanFactory bf;

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		bf = beanFactory;
	}

	public static Object getBean(String beanName) {
		return bf.getBean(beanName);
	}

	public static Object getBean(String beanName, Class<?> clazz) {
		return bf.getBean(beanName, clazz);
	}

}
