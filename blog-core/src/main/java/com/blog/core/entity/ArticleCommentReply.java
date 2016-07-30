package com.blog.core.entity;

import java.io.Serializable;
/**
 * 描述：评论再回复
 * @author: hecj
 */
public class ArticleCommentReply implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String commentId;
	
	private String receiveUserId;
	
	private String replyUserId;
	
	private String content;
	
	private int isDelete;
	
	private Long createAt;

	public ArticleCommentReply() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
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
