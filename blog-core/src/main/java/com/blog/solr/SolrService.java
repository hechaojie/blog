package com.blog.solr;

import com.hecj.common.util.result.Result;

/**
 * 描述：检索接口
 * @author: hecj
 */
public interface SolrService {

	/**
	 * 描述：检索网站文章（标题、内容、作者）
	 * @author: hecj
	 */
	public Result searchArticle(String search,int page,int size);
}
