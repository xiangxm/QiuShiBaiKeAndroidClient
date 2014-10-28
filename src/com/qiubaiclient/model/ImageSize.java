package com.qiubaiclient.model;

import java.io.Serializable;

/**
 * 照片尺寸
 * 
 * @author xiangxm
 * 
 */
public class ImageSize implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] s;
	private int[] m;

	public int[] getS() {
		return s;
	}

	public void setS(int[] s) {
		this.s = s;
	}

	public int[] getM() {
		return m;
	}

	public void setM(int[] m) {
		this.m = m;
	}

}
