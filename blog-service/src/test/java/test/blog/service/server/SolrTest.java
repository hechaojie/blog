package test.blog.service.server;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.blog.service.util.Constant;
import com.blog.solr.server.SolrOpeartor;


public class SolrTest {
	
	static SolrOpeartor so = new SolrOpeartor();

	public static void main(String[] args) throws SolrServerException {
		
//		Constant.solrServer = "http://localhost:9001/solr";
		Constant.solrServer = "http://solr.hechaojie.com/solr";
		
		QueryResponse res = so.search("boSearch", "配置", 1, 20);
		System.out.println(res.getResults().getNumFound());
		for (SolrDocument doc : res.getResults()) {
			//System.out.println(doc.toString());
			System.out.println(doc.getFirstValue("boTitle"));
			
		}
		System.out.println(res.getHighlighting());
	}

}
