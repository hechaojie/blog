package com.blog.service.core.email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 单个邮件配置参数
 */
public class EmailVars implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private Map<String, String> vars = new HashMap<String, String>();

	public EmailVars() {

	}

	public EmailVars(String email, Map<String, String> vars) {
		super();
		this.email = email;
		this.vars = vars;
	}

	public String getEmail() {
		return email;
	}

	public EmailVars setEmail(String email) {
		this.email = email;
		return this;
	}

	public Map<String, String> getVars() {
		return vars;
	}

	public void setVars(Map<String, String> vars) {
		this.vars = vars;
	}

	public EmailVars putVar(String key, String value) {
		this.vars.put(key, value);
		return this;
	}

}
