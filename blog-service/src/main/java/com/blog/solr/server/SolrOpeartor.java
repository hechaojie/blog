package com.blog.solr.server;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Service;

/**
 * 描述：操作solr
 * 
 * @author: hecj
 */
@Service
public class SolrOpeartor {

	/**
	 * 描述：查询solr索引库
	 * 
	 * @author: hecj
	 * @throws SolrServerException
	 */
	public QueryResponse search(String field, String search, int page, int size) throws SolrServerException {
		HttpSolrServer solr = SolrServer.getInstance().getServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(field + ":" + search);
		query.setHighlight(true);// 开启高亮功能
		query.addHighlightField("*");// 高亮字段
		query.setHighlightSimplePre("<em style=\"font-style: normal;color: #c00;\">");// 渲染标签
		query.setHighlightSimplePost("</em>");// 渲染标签
		query.addSort(new SortClause("id", SolrQuery.ORDER.asc));
		query.setStart((page - 1) * size);
		query.setRows(size);
		
		return solr.query(query);
	}

}
