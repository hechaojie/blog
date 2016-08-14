package com.blog.solr.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Service;

import com.blog.solr.SolrService;
import com.blog.solr.server.SolrOpeartor;
import com.hecj.common.util.result.Result;
import com.hecj.common.util.result.ResultSupport;

@Service("solrService")
public class SolrServiceImpl implements SolrService{
	private static final Log log = LogFactory.getLog(SolrServiceImpl.class);
	@Resource
	private SolrOpeartor solrOpeartor;
	@Override
	public Result searchArticle(String search, int page, int size) {
		
		Result result = new ResultSupport();
		result.getPagination().setPageSize(size);
		result.getPagination().setCurrPage((long)page);
		QueryResponse response = null;
		try {
			response = solrOpeartor.search("boSearch", search, page, size);
			result.getPagination().setCountSize(response.getResults().getNumFound());
			result.getModel().put("response", response);
			result.setResult(true);
		} catch (Exception e) {
			log.error("search excepton : " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
