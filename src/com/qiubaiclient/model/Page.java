package com.qiubaiclient.model;

public class Page {

		/**
		 * 当前第几页
		 */
		public int currentPage = 1;

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

		/**
		 * 获取默认的第一页
		 * 
		 * @return
		 */
		public int getDefaultPage() {

			return this.currentPage = 1;
		}

		/**
		 * 设置默认页数，第一页数据
		 */
		public void setDefaultPage() {

			this.currentPage = 1;
		}

		/**
		 * 下一页第几页
		 */
		public void addPage() {

			this.currentPage++;
		}

		/**
		 * 到前一页
		 */
		public void minusPage() {

			this.currentPage--;
		}

	}
