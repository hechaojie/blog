package com.blog.service.core.entity;

import java.io.Serializable;

/**
 * 邮件Token认证表
 */
public class EmailAuthToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * token随机串
	 */
	private String token;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 0 有效  1  无效
	 */
	private int isVerify;
	/**
	 * 失效时间
	 */
	private Long validAt;
	/**
	 * 验证时间
	 */
	private Long verifyAt;
	private Long createAt;
	public EmailAuthToken() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsVerify() {
		return isVerify;
	}
	public void setIsVerify(int isVerify) {
		this.isVerify = isVerify;
	}
	public Long getValidAt() {
		return validAt;
	}
	public void setValidAt(Long validAt) {
		this.validAt = validAt;
	}
	public Long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}
	public Long getVerifyAt() {
		return verifyAt;
	}
	public void setVerifyAt(Long verifyAt) {
		this.verifyAt = verifyAt;
	}

}
