/**
 * Copyright 2005-2015 Flouny.Caesar.com
 * All rights reserved.
 * 
 * @project
 * @author Flouny.Caesar
 * @version 2.0
 * @data 2013-01-01
 */
package com.pinting.manage.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传 VO
 * 
 * @author Flouny.Caesar
 * 
 */
public class Upload {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MultipartFile image;

	private String imgPath;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
}