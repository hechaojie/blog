package com.blog.service.core.entity;

import java.io.Serializable;
/**
 * 文章内容
 */
public class ArticleContent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String articleId;
	
	private String content;
	
	private Integer sort;
	
	private Long createAt;

	private Long updateAt;

	public ArticleContent() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

}
