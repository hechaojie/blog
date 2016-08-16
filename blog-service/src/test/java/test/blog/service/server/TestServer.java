package test.blog.service.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.service.ArticleService;
import com.blog.service.ArticleTypeService;
import com.blog.service.core.entity.Article;
import com.blog.service.core.entity.ArticleContent;
import com.blog.service.core.entity.ArticleType;
import com.blog.service.dao.ArticleContentDao;
import com.blog.service.util.Constant;
import com.blog.solr.SolrService;
import com.hecj.common.util.date.DateFormatUtil;
import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;

/**
 * Server
 */
public class TestServer {

	public static final Log log = LogFactory.getLog(TestServer.class);

	ClassPathXmlApplicationContext context = null;
	@Before
	public void before(){
		String[] configs = new String[] { "spring/spring-context.xml" };
		context = new ClassPathXmlApplicationContext(configs);
	}
	
	@Test
	public void test(){
		System.out.println(1);
	}
	
	@Test
	public void test01(){
		try {

			Pagination p = new Pagination();
			p.setPageSize(1000);
			ArticleTypeService articleTypeService = (ArticleTypeService)context.getBean("articleTypeService");
			Result result = articleTypeService.findArticleTypesByCondition(new HashMap<String,Object>(), p);
			System.out.println(((ArticleType)(result.getData().get(0))).getName());
			System.out.println(result.getPagination().getCountSize());
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02(){
		try {
			
			Article article = new Article();
			article.setCommentCount(0);
			article.setRecommend(0);
			article.setTitle("ddd");
			//article.setUserId(1l);
			//article.setType(111);
			
			ArticleService articleService = (ArticleService)context.getBean("articleService");
			articleService.saveArticle(article, null);
			
			System.out.println("insert id :"+article.getId());
			
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03(){
		try {
			
			ArticleContent article = new ArticleContent();
			article.setContent("11111111");
			
			ArticleContentDao articleContentDao = (ArticleContentDao)context.getBean("articleContentDao");
			System.out.println("insert id :"+articleContentDao.save(article));
			
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void test04(){
		try {
			
			ArticleService articleService = (ArticleService)context.getBean("articleService");
			List<String> ids = new ArrayList<String>();
			ids.add("20160730020542695768");
			articleService.findArticleByIds(ids);	
			
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void test05(){
		try {
			Constant.solrServer = "http://solr.hechaojie.com/solr";
			
			SolrService solrService = (SolrService)context.getBean("solrService");
			System.out.println(solrService);

			Result sdl = solrService.searchArticle("*", 1, 20);
			System.out.println(sdl.getPagination().getCountSize());
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void test06(){
		Date d = DateFormatUtil.parse("2016-2-1 15:10:13", "yyyy-MM-dd HH:mm:ss");
		System.out.println(d.getTime());
	}
}
