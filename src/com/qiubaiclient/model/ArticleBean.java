package com.qiubaiclient.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 使用fastjson解析
 * 
 * @author xiangxm
 * 
 */
public class ArticleBean {

	private int count;
	private long total;
	private int page;
	/**
	 * items所对应的数组
	 */
	private List<ItemBean> items = new ArrayList<ItemBean>();

	public List<ItemBean> getItems() {
		return items;
	}

	public void setItems(List<ItemBean> items) {
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "ArticleBean [count=" + count + ", total=" + total + ", page="
				+ page + ", items=" + items + "]";
	}

}
