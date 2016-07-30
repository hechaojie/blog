package test.blog.product.service.server;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blog.core.entity.Article;
import com.blog.core.entity.EmailSendHistory;
import com.blog.core.service.ArticleService;
import com.blog.service.dao.ArticleDao;
import com.blog.service.dao.EmailSendHistoryDao;
import com.hecj.common.utils.GenerateUtil;
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
			/*
			ArticleService service = (ArticleService)context.getBean("articleService");

			Result result = service.findArticlesByCondition(new HashMap<String,Object>(), new Pagination());
			System.out.println(result.isSuccess());
			
			System.out.println(result.getData().get(0));
			
			ArticleDao dao = context.getBean(ArticleDao.class);
			System.out.println(dao.findArticlesByConditions(new HashMap<String,Object>(), 1, 3).get(0).getTitle());
			System.out.println(dao.findArticlesByConditions(new HashMap<String,Object>(), 1, 3).get(0).getCreateAt());
			
			
			System.out.println("------------");
			
			ArticleDao articleDao = (ArticleDao)context.getBean("articleDao");
			System.out.println(articleDao.findArticlesByConditions(new HashMap(), 0, 10));
			
			Article article = new Article();
			article.setId(GenerateUtil.generateId());
			articleDao.save(article);
			*/
			EmailSendHistoryDao emailSendHistoryDao = (EmailSendHistoryDao)context.getBean("emailSendHistoryDao");
			System.out.println(emailSendHistoryDao);
			
			EmailSendHistory  s = new EmailSendHistory();
			s.setId(GenerateUtil.generateId());
			s.setCreateAt(System.currentTimeMillis());
			s.setTitle("");
			s.setType(1);
			//s.setDelete(0);
			s.setContent("");
			s.setReciver("");
			s.setReciverEmail("");
			s.setSenderEmail("");
			s.setSender("");
			emailSendHistoryDao.save(s);
			
		} catch (Exception e) {
			log.error("server start error : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
