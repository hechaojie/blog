package com.blog.front.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.blog.service.ArticleService;
import com.blog.service.core.vo.ArticleVo;
import com.blog.solr.SolrService;
import com.hecj.common.util.html.HTMLSpirit;
import com.hecj.common.util.result.Result;

@Controller
@RequestMapping(value="/search")
public class SearchController extends BaseController{
	private static final Log log = LogFactory.getLog(SearchController.class);
	@Resource
	private SolrService solrService;
	@Resource
	private ArticleService articleService;
	
	/**
	 * 描述：检索网站文章（标题、内容、作者）
	 * @author: hecj
	 */
	@RequestMapping(value="")
	public String index(Integer page,String sq,HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		page = page == null ? 1 : page;
		try {
			Result result = solrService.searchArticle(sq, page, 10);
			
			List<ArticleVo> articleList = new ArrayList<ArticleVo>();
			if(result.isSuccess()){
				// 检索结果
				QueryResponse resp = (QueryResponse) result.getModel().get("response");
				
				for (SolrDocument doc : resp.getResults()) {
					ArticleVo v = new ArticleVo();
					v.setId((String) doc.getFieldValue("id"));
					// 高亮字段
					Map<String,List<String>> hlDoc = resp.getHighlighting().get(v.getId());
					List<String> titles = hlDoc.get("boTitle");
					if(titles != null && titles.size() > 0){
						v.setTitle(titles.get(0));
					} else{
						v.setTitle((String)doc.getFirstValue("boTitle"));
					}
					String desc = articleService.findArticleContentByArticleId(v.getId()).get(0).getContent();
					v.setContent(dealDesc(sq,desc));
					articleList.add(v);
				}
			}
			model.addAttribute("articleList", articleList);
			model.addAttribute("result", result);
			model.addAttribute("sq", sq);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return "search/index";
	}
	
	/**
	 * 描述：处理文章描述
	 * @author: hecj
	 */
	private static String dealDesc(String sq,String desc){
		desc = HTMLSpirit.delHTMLTag(desc);
		desc = desc.replaceAll("(&nbsp;)+"," ");
		desc = desc.replaceAll("[\\t\\n\\r]","");
		desc = desc.replaceAll(";","; ");
		
		if(desc.length()>=150){
			int startOffest = desc.indexOf(sq);
			if(startOffest != -1){
				if(desc.length()-startOffest< 150){
					desc = desc.substring(desc.length()-150,desc.length());
				} else{
					if(startOffest> 5){
						desc = desc.substring(startOffest-5,startOffest+145);
					} else{
						desc = desc.substring(0,150);
					}
				}
			}
		}
		if(desc.length() >= 150){
			desc = desc.substring(0,150);
		}
		desc = desc.replaceAll("(?i)"+sq, "<em style=\"font-style: normal;color: #c00;\">"+sq+"</em>");
		
		if(desc.endsWith("<")){
			desc = desc.substring(0,desc.length()-1);
		}
		return desc;
	}
}
