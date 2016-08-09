package com.blog.service;

import java.util.List;
import java.util.Map;

import com.blog.service.core.entity.Article;
import com.blog.service.core.entity.ArticleContent;
import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;
/**
 * @功能描述 文章业务处理类
 * @Version		V1.0
 * @Date		2015-12-28 下午3:31:59
 * @author hechaojie
 */
public interface ArticleService {
	
	
	/**
	 * @功能描述 动态查询文章,分页
	 * @param params 动态查询参数
	 * @param pagination 分页器
	 * @return Result 结果集
	 * @Version		V1.0
	 * @date		2015-12-28 下午3:30:57
	 * @author hechaojie
	 * @modify
	 */
	public Result findArticlesByCondition(Map<String, Object> params, Pagination pagination) ;
	/**
	 * @功能描述 根据文章Id查询
	 * @param articleId 文章Id
	 * @return  结果集
	 * @throws Exception Article
	 * @Version		V1.0
	 * @date		2015-12-28 下午3:31:26
	 * @author hechaojie
	 * @modify
	 */
	public Article findArticleById(String articleId) throws Exception;
	
	/**
	 * @功能描述 根据文章Id查询内容
	 * @param articleId 文章Id
	 * @return List<ArticleContent>
	 * @Version		V1.0
	 * @date		2015-12-28 下午3:33:27
	 * @author hechaojie
	 * @modify
	 */
	public List<ArticleContent> findArticleContentByArticleId(String articleId);
	
	/**
	 * @功能描述 保存文章
	 * @param article 文章
	 * @param articleContents 文章内容集合
	 * @return boolean
	 * @Version		V1.0
	 * @date		2016-1-5 上午11:40:58
	 * @author hechaojie
	 */
	public String saveArticle(Article article, List<ArticleContent> articleContents);
	
	/**
	 * @功能描述 编辑文章
	 * @param article 文章
	 * @param articleContents 文章内容集合
	 * @return boolean
	 * @Version		V1.0
	 * @date		2016-1-5 18:09:58
	 * @author hechaojie
	 */
	public boolean editArticle(Article article, List<ArticleContent> articleContents);
	
}
