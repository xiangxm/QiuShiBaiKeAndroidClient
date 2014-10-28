package com.qiubaiclient.model;

/**
 * 侧边菜单大种类
 * 
 * @author xiangxm
 * 
 */
public class CategrySlideMenu {

	public CategrySlideMenu(String cateName){
		this.cateName = cateName ;
	}
	private String cateName;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

}
