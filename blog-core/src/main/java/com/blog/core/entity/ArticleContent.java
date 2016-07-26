package com.blog.core.entity;

import java.io.Serializable;
/**
 * 文章内容
 */
public class ArticleContent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long articleId;
	
	private String content;
	
	private Integer sort;
	
	private Long createAt;

	private Long updateAt;

	public ArticleContent() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
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
