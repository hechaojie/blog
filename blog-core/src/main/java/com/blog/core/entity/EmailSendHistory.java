package com.blog.core.entity;

import java.io.Serializable;

/**
 * 
 * 描述：邮件发送记录
 * 
 * @author: hecj
 */
public class EmailSendHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String title;

	private String content;

	private String sender;

	private String senderEmail;

	private String reciver;

	private String reciverEmail;

	private int type;

	private int isDelete;

	private long createAt;
	

	public EmailSendHistory() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getReciverEmail() {
		return reciverEmail;
	}

	public void setReciverEmail(String reciverEmail) {
		this.reciverEmail = reciverEmail;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}