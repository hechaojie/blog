package com.blog.service.core.vo;

import java.io.Serializable;
import java.util.Date;

import com.hecj.common.util.date.DateFormatUtil;

/**
 * 描述：文章评论
 * @author: hecj
 */
public class ArticleCommentVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String userId;
	
	private String email;
	
	private String username;
	
	private String articleId;

	private String content;
	
	private int isDelete;
	
	private Long createAt;

	public ArticleCommentVo() {

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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreateText(){
		return DateFormatUtil.date2Text(new Date(this.getCreateAt()));
	}

}
