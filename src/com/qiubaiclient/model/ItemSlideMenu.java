package com.qiubaiclient.model;

/**
 * 
 * 侧边菜单小种类
 * 
 * @author xiangxm
 * 
 */
public class ItemSlideMenu {

	// public static final int INFINITE_ID = -1;
	public static final int SETTING_ID = 999;
	public static final int FEEDBACK_ID = 998;
	public static final int SHARE_ID = 995;
	public static final int ABOUT_ID = 996;

	public ItemSlideMenu(String itemTitle, int id, int icon) {

		this.itemTitle = itemTitle;
		this.id = id;
		this.icon = icon;
	}

	private int id;

	private String itemTitle;

	private int icon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

}
