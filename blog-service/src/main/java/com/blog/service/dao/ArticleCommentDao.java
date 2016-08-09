package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.service.core.entity.ArticleComment;
import com.blog.service.core.vo.ArticleCommentVo;

public interface ArticleCommentDao {

	public List<ArticleCommentVo> findByConditions(@Param("sqlParams")Map<String,Object> sqlParams,@Param("start")long start,@Param("size")int size);
	
	public long countByConditions(@Param("sqlParams")Map<String,Object> sqlParams);
	
	public long insert(ArticleComment ac);
	
}
