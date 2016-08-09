package com.blog.service.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.hecj.common.util.date.DateFormatUtil;
/**
 * 文章
 */
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String userId;
	
	public String title;
	
	private String type;
	
	private int commentCount;
	
	private int recommend;
	
	private int permission;
	
	private int isDelete;
	
	private Long createAt;
	
	private Long updateAt;

	public Article() {

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}

	public Long getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Long updateAt) {
		this.updateAt = updateAt;
	}
	
	public String getCreateText(){
		return DateFormatUtil.date2Text(new Date(this.getCreateAt()));
	}
	
}
