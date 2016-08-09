package com.blog.service.core.entity;

import java.io.Serializable;

/**
 * 关于我
 */
public class AboutUS implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 是否删除（0：有效，1：删除）
	 */
	private int isDelete;

	private Long createAt;
	
	private Long updateAt;

	public AboutUS() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

}
