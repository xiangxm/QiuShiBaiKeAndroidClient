package com.qiubaiclient.model;

import java.io.Serializable;

/**
 * JSON五级数据 用户信息
 * 
 * @author xiangxm
 * 
 */
public class UserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态标志位，暂时不知道什么用
	 */
	private String created_at;
	/**
	 * 用户使用设备发表
	 */
	private String last_device;
	/**
	 * 用户角色
	 */
	private String role;
	/**
	 * 最后访问
	 */
	private String last_visited_at;
	/**
	 * 用户活跃状态
	 */
	private String state;
	/**
	 * 登录用户名
	 */
	private String login;
	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 用户头像icon名称
	 */
	private String icon;
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getLast_device() {
		return last_device;
	}
	public void setLast_device(String last_device) {
		this.last_device = last_device;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLast_visited_at() {
		return last_visited_at;
	}
	public void setLast_visited_at(String last_visited_at) {
		this.last_visited_at = last_visited_at;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "UserBean [created_at=" + created_at + ", last_device="
				+ last_device + ", role=" + role + ", last_visited_at="
				+ last_visited_at + ", state=" + state + ", login=" + login
				+ ", id=" + id + ", icon=" + icon + "]";
	}

	

}
