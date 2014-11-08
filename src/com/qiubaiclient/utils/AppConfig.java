package com.qiubaiclient.utils;

public class AppConfig {

	/**
	 * 纯文
	 */
	public static final String ONLY_TEXT = "http://m2.qiushibaike.com/article/list/text?";// page=1&count=30&rqcnt=39

	/**
	 * 纯图
	 */
	public static final String ONLY_IMG = "http://m2.qiushibaike.com/article/list/imgrank?";// page=1&count=30&rqcnt=48

	/**
	 * 图文
	 */
	public static final String IMAGE_TEXT = "http://m2.qiushibaike.com/article/list/suggest?";// page=1&count=30&rqcnt=223
//http://m2.qiushibaike.com/article/list/suggest?page=1&count=30&rqcnt=223
	/**
	 * 最热
	 */
	// public static final String MOST_HOT =
	// "http://m2.qiushibaike.com/article/list/suggest?";

	/**
	 * 精华
	 */
	// public static final String MOST_ESSONCE =
	// "http://m2.qiushibaike.com/article/list/day?";
	/**
	 * 最新
	 */
	public static final String LATEST = "http://m2.qiushibaike.com/article/list/latest?";
	/**
	 * 真相
	 */
	// public static final String TRUTH =
	// "http://m2.qiushibaike.com/article/list/images?";

	/**
	 * 页数和条数
	 */
	public static final String PAGE_COUNT = "page=%d&count=%d";

	/**
	 * 用户头像
	 */
	public static final String USER_IMG = "http://pic.moumentei.com/system/avtnew/";// 1811/18116245/thumb/20140721131627.jpg

	/**
	 * 获取文章大图
	 */
	public static final String ARTICLE_BIG_IMG = "http://pic.moumentei.com/system/pictures/";// 8511/85110157/medium/app85110157.jpg
	/**
	 * 获取评论内容
	 */
	public static final String DISCUSS_COTENT = "http://m2.qiushibaike.com/article/%d/comments?page=%d&count=%d";// 85115999/comments?page=1&count=30

	/**
	 * 喜欢（点赞）
	 */
	public static final String PRAISE = "http://m2.qiushibaike.com/collect/";// 85107921

	/**
	 * 纯文模块
	 */
	public static final int SECTION_ONLY_TEXT = 0;
	/**
	 * 纯图模块
	 */
	public static final int SECTION_ONLY_IMAGE= 1 ;
	
	/**
	 * 图文模式
	 */
	public static final int TEXT_AND_IMAGE= 2 ;
	
	/**
	 * 最热模块id
	 */
//	public static final int SECTION_MOST_HOT = 1;
	/**
	 * 精华模块id
	 */
//	public static final int SECTION_MOST_ESSONCE = 2;
	/**
	 * 最新模块id
	 */
	public static final int SECTION_LATEST = 3;
	/**
	 * 真相模块id
	 */
//	public static final int SECTION_TRUTH = 4;

}
