package com.blog.service.dao;

import com.blog.service.core.entity.EmailAuthToken;

public interface EmailAuthTokenDao {
	
	public long save(EmailAuthToken emailAuthToken);
	
	public EmailAuthToken findByToken(String token);
	
	public boolean update(EmailAuthToken emailAuthToken);
}
