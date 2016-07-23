package com.blog.front.util;

import java.io.Serializable;

/**
 * 验证码
 */
public class CheckCode implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String code;

	private String sendObj;
	
	private long sendTime;
	
	private long invalidTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public long getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(long invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getSendObj() {
		return sendObj;
	}

	public void setSendObj(String sendObj) {
		this.sendObj = sendObj;
	}

	@Override
	public String toString() {
		return "CheckCode [code=" + code + ", sendObj=" + sendObj
				+ ", sendTime=" + sendTime + ", invalidTime=" + invalidTime
				+ "]";
	}

}
