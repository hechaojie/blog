package com.blog.service.core.entity;

import java.io.Serializable;

/**
 * 描述：文章评论
 * @author: hecj
 */
public class ArticleComment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String userId;
	
	private String articleId;

	private String content;
	
	private int isDelete;
	
	private Long createAt;

	public ArticleComment() {

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

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
