package com.blog.solr.server;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

import com.blog.service.util.Constant;

/**
 * 描述：solr服务工具类
 * @author: hecj
 */
public class SolrServer {

	private static SolrServer solrServer = null;
	private static HttpSolrServer server = null;
	
	private static Object _lock = new Object();
	
	private SolrServer() {
	}

	public static SolrServer getInstance() {
		if (solrServer == null) {
			synchronized (_lock) {
				if(solrServer == null){
					solrServer = new SolrServer();
				}
			}
		}
		return solrServer;
	}

	public HttpSolrServer getServer() {
		if (server == null) {
			server = new HttpSolrServer(Constant.solrServer);
			server.setSoTimeout(1000);
			server.setConnectionTimeout(1000);
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
			server.setFollowRedirects(false);
			server.setAllowCompression(true);
			server.setMaxRetries(1);
		}
		return server;
	}

}
