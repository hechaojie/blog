package test.blog.product.service.server;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.core.service.ArticleService;
import com.blog.service.dao.ArticleDao;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;

/**
 * Server
 */
public class Server {

	public static final Log log = LogFactory.getLog(Server.class);

	public static void main(String[] args) {
		String[] configs = new String[] { "spring/spring-*.xml" };
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configs);
		try {

			ArticleService service = (ArticleService)context.getBean("articleService");

			Result result = service.findArticlesByCondition(new HashMap<String,Object>(), new Pagination());
			System.out.println(result.isSuccess());
			
			System.out.println(result.getData().get(0));
			
			ArticleDao dao = context.getBean(ArticleDao.class);
			System.out.println(dao.findArticlesByConditions(new HashMap<String,Object>(), 1, 3).get(0).getTitle());
			System.out.println(dao.findArticlesByConditions(new HashMap<String,Object>(), 1, 3).get(0).getCreateAt());
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
