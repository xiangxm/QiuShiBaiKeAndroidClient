package com.qiubaiclient.model;

/**
 * 点赞与不点赞的数据
 * 
 * @author xiangxm
 * 
 */
public class VotesBean {

	/**
	 * 不点赞
	 */
	private int down;
	/**
	 * 点赞
	 */
	private int up;

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	@Override
	public String toString() {
		return "VotesBean [down=" + down + ", up=" + up + "]";
	}

}
