package com.blog.core.entity;

import java.io.Serializable;
/**
 * 文章评论
 */
public class ArticleComment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;
	
	private long articleId;
	
	private String content;
	
	private Long createAt;

	public ArticleComment() {

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}

}
