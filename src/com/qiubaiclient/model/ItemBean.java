package com.qiubaiclient.model;

import java.io.Serializable;

/**
 * ItemBean JSON三级数据
 * 
 * @author xiangxm
 * 
 */
public class ItemBean implements Serializable {

	private int blog_type;

	public int getBlog_type() {
		return blog_type;
	}

	public void setBlog_type(int blog_type) {
		this.blog_type = blog_type;
	}

	/**
	 * 是否点击过
	 */
	private boolean isClicked = false;

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked, int whoClicked) {
		this.isClicked = isClicked;
		if (null != votes) {

			votes.setWhoClicked(whoClicked);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文章附带的图片附件
	 */
	private String image;
	/**
	 * 发布时间
	 */
	private String published_at;
	/**
	 * 标志。暂时不知道是什么作用。
	 */
	private String tag;
	/**
	 * 文章对应的id
	 */
	private String id;
	/**
	 * 文章创建时间。区别于published_at
	 */
	private String created_at;
	/**
	 * 文章的内容
	 */
	private String content;
	/**
	 * 文章状态。糗百里面发布文章需要审核，估计这个是审核用的
	 */
	private String state;
	/**
	 * 评论数量
	 */
	private int comments_count;
	/**
	 * 是否允许评论
	 */
	private boolean allow_comment;
	/**
	 * 用户信息
	 */
	private UserBean user;
	/**
	 * 点赞信息
	 */
	private VotesBean votes;

	private ImageSize image_size;

	public ImageSize getImage_size() {
		return image_size;
	}

	public void setImage_size(ImageSize image_size) {
		this.image_size = image_size;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPublished_at() {
		return published_at;
	}

	public void setPublished_at(String published_at) {
		this.published_at = published_at;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public boolean isAllow_comment() {
		return allow_comment;
	}

	public void setAllow_comment(boolean allow_comment) {
		this.allow_comment = allow_comment;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public VotesBean getVotes() {
		return votes;
	}

	public void setVotes(VotesBean votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "ItemBean [blog_type=" + blog_type + ", isClicked=" + isClicked
				+ ", image=" + image + ", published_at=" + published_at
				+ ", tag=" + tag + ", id=" + id + ", created_at=" + created_at
				+ ", content=" + content + ", state=" + state
				+ ", comments_count=" + comments_count + ", allow_comment="
				+ allow_comment + ", user=" + user + ", votes=" + votes
				+ ", image_size=" + image_size + "]";
	}

}
