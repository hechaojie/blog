package test.blog.service.server;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.core.entity.Article;
import com.blog.core.entity.ArticleContent;
import com.blog.core.entity.ArticleType;
import com.blog.core.service.ArticleService;
import com.blog.core.service.ArticleTypeService;
import com.blog.service.dao.ArticleContentDao;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;

/**
 * Server
 */
public class TestServer {

	public static final Log log = LogFactory.getLog(TestServer.class);

	ClassPathXmlApplicationContext context = null;
	@Before
	public void before(){
		String[] configs = new String[] { "spring/spring-*.xml" };
		context = new ClassPathXmlApplicationContext(configs);
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
}
