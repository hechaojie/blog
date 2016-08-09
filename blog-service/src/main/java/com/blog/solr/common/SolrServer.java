package com.blog.solr.common;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrServer {

	private static SolrServer solrServer = null;
	private static HttpSolrServer server = null;

	private SolrServer() {
	}

	public static synchronized SolrServer getInstance() {
		if (solrServer == null) {
			solrServer = new SolrServer();
		}
		return solrServer;
	}

	public HttpSolrServer getServer() {
		if (server == null) {
			server = new HttpSolrServer("http://solr.hechaojie.com/solr");
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

	public static void main(String[] args) throws SolrServerException {

		HttpSolrServer solr = SolrServer.getInstance().getServer();
		System.out.println(solr.getHttpClient());
		
		SolrQuery query = new SolrQuery();
	    query.setQuery( "bo_content:*" );
	    query.addSort(new SortClause("id",SolrQuery.ORDER.asc));
	    query.setStart(1);
	    query.setRows(2);
	    
	    QueryResponse rsp = server.query( query );
	    
	    SolrDocumentList docs = rsp.getResults();
	    
	    for (SolrDocument doc : docs) {
	    	System.out.println("---------");
			System.out.println(doc.getFieldValue("id"));
			System.out.println(doc.getFieldValue("bo_title"));
		}
	    
	    System.out.println(docs.getNumFound());
	    System.out.println(docs.getStart());
	    System.out.println(docs.getMaxScore());
	    
		
	}

}
