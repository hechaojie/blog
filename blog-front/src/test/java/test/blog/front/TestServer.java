package test.blog.front;


import java.util.Random;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import test.blog.front.qiniu.res.QiniuResponse;
import test.blog.front.qiniu.service.QiniuService;

import com.hecj.common.utils.HttpRequest;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

public class TestServer {

/*	public static void main(String[] args) {
		String ACCESS_KEY = \"hY6f2mtDxA0GGqgdrIcHUIUstYS5GAHnZR_uH9ZQ\";
		String SECRET_KEY = \"wyVV23bzxpxhjq-0BDwQHHvsXXtqHj7oFuiXuoY9\";
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	}
*/
	    
	    public static void main(String[] args) throws Exception {
//	    	
//	    	<constructor-arg index=\"0\"><value>asX6brT0iuSuxx8nvB62F7rdGSXGXCi32-PWxYEl</value></constructor-arg>
//	    	<constructor-arg index=\"1\"><value>sOqJ87ezrhX-8-iKM9rmplWbwg1odPgkayRetXyB</value></constructor-arg>
//	    	String ACCESS_KEY = \"asX6brT0iuSuxx8nvB62F7rdGSXGXCi32-PWxYEl\";
//			String SECRET_KEY = \"sOqJ87ezrhX-8-iKM9rmplWbwg1odPgkayRetXyB\";
//			
//			QiniuService service = new QiniuService(ACCESS_KEY,SECRET_KEY);
//			
//			QiniuResponse re = service.upload(\"hecjtop\", \"1dsfd.txt\", \"d:/1.txt\");
//			
//			System.out.println(re.OK);
//			
			/*Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
	        // 请确保该bucket已经存在
	        String bucketName = \"protocols\";
	        PutPolicy putPolicy = new PutPolicy(bucketName);
	        String uptoken = putPolicy.token(mac);
	        PutExtra extra = new PutExtra();
	        String key = \"123.txt\";
	        String localFile = \"d:/1.txt\";
	        PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
	        System.out.println(ret.getStatusCode());
	        System.out.println(ret.getResponse());*/
	    	
//	    	

//	    	System.out.print(HtmlUtils.htmlEscape(\"<a>执行类没</a>\"));
	    	
//	    	String content = HttpRequest.sendPost(\"http://192.168.0.34/friend/uploadcontacts?token=MjA0N2UxZWU0MzI2NGU4YzljNzllNjQ2MGUwNzkzM2ImZjYzMTFlNmFjMDhlNDYxOGIwYTg1MWE5ODlhYzQzOGE%3D\", \"\");
//	    	System.out.println(content);
	    	
	    	
//	    	String content = "[{\"name\":\"杰哥\",\"phone\":\"189-1108-8819\"},{\"name\":\"陈静\",\"phone\":\"137-3380-3097\"},{\"name\":\"陈红\",\"phone\":\"158-3811-7173\"},{\"name\":\"李政荣老师\",\"phone\":\"136-0862-8949\"},{\"name\":\"李伟伟\",\"phone\":\"137-8380-5442\"},{\"name\":\"路盼盼\",\"phone\":\"135-2612-0195\"},{\"name\":\"科得\",\"phone\":\"136-7304-0353\"},{\"name\":\"罗莹\",\"phone\":\"182-0231-6636\"},{\"name\":\"阿妈\",\"phone\":\"136-3961-4067\"},{\"name\":\"艳丽\",\"phone\":\"135-2612-1064\"},{\"name\":\"国军叔\",\"phone\":\"151-3798-3721\"},{\"name\":\"霍雪平\",\"phone\":\"152-3723-4165\"},{\"name\":\"李俊\",\"phone\":\"150-3712-0262\"},{\"name\":\"晓娅\",\"phone\":\"136-0398-6015\"},{\"name\":\"李庆\",\"phone\":\"186-2571-2909\"},{\"name\":\"阿爸\",\"phone\":\"136-0862-5877\"},{\"name\":\"娇儿\",\"phone\":\"131-9351-8807\"},{\"name\":\"李旭\",\"phone\":\"137-8111-1460\"},{\"name\":\"震妈\",\"phone\":\"15836345776\"},{\"name\":\"丁婷婷\",\"phone\":\"137-0348-3779\"},{\"name\":\"段玉明\",\"phone\":\"138-0386-7221\"},{\"name\":\"张小苗\",\"phone\":\"135-5204-0431\"},{\"name\":\"四舅\",\"phone\":\"158-3635-0478\"},{\"name\":\"侯侯\",\"phone\":\"182-1168-3131\"},{\"name\":\"菲\",\"phone\":\"133-4382-2007\"},{\"name\":\"董鑫\",\"phone\":\"156-5216-2663\"},{\"name\":\"方家村\",\"phone\":\"010-52884868\"},{\"name\":\"赵房东\",\"phone\":\"137-0137-4881\"},{\"name\":\"顾茂\",\"phone\":\"186-1129-6060\"},{\"name\":\"国燕\",\"phone\":\"158-3710-5081\"},{\"name\":\"霍雪平\",\"phone\":\"185-3876-9795\"},{\"name\":\"孟凯\",\"phone\":\"132-8507-8131\"},{\"name\":\"李叔\",\"phone\":\"139-0105-8939\"},{\"name\":\"利莎\",\"phone\":\"134-0110-2516\"},{\"name\":\"刘毅\",\"phone\":\"186-1181-0559\"},{\"name\":\"律姐\",\"phone\":\"138-1083-8121\"},{\"name\":\"马娟\",\"phone\":\"158-0111-7461\"},{\"name\":\"宋玉蝉\",\"phone\":\"150-3112-3099\"},{\"name\":\"小贝\",\"phone\":\"186-2583-1483\"},{\"name\":\"小吕\",\"phone\":\"135-9805-8997\"},{\"name\":\"小敏\",\"phone\":\"158-9015-6239\"},{\"name\":\"小姨夫\",\"phone\":\"151-3626-2336\"},{\"name\":\"徐鑫妍\",\"phone\":\"151-1015-7314\"},{\"name\":\"艳丽\",\"phone\":\"186-3728-8247\"},{\"name\":\"爷爷\",\"phone\":\"187-3970-7991\"},{\"name\":\"英姐\",\"phone\":\"189-0119-0800\"},{\"name\":\"由召岭\",\"phone\":\"186-0089-8002\"},{\"name\":\"于小娇\",\"phone\":\"185-1099-4126\"},{\"name\":\"震爸\",\"phone\":\"158-3722-4468\"},{\"name\":\"朱银华\",\"phone\":\"158-1158-4075\"},{\"name\":\"三舅妈\",\"phone\":\"158-9683-7853\"},{\"name\":\"屁莹儿\",\"phone\":\"189-1032-9003\"},{\"name\":\"易药软件\",\"phone\":\"158-0123-2685\"},{\"name\":\"张伟明\",\"phone\":\"177-3160-5290\"},{\"name\":\"李文强\",\"phone\":\"1-312-278-5292\"},{\"name\":\"振鹏\",\"phone\":\"18611139292\"},{\"name\":\"李\",\"phone\":\"13161177831\"},{\"name\":\"百顺家园宾馆公寓\",\"phone\":\"13717924439\"},{\"name\":\"租房曹\",\"phone\":\"13521935379\"},{\"name\":\"侯震\",\"phone\":\" \"},{\"name\":\"做少花\",\"phone\":\"17091086503\"},{\"name\":\"张三\",\"phone\":\"34456789\"},{\"name\":\"张三\",\"phone\":\"34456789\"},{\"name\":\"邵帅\",\"phone\":\"13552877566\"},{\"name\":\"何志坚\",\"phone\":\"13901104554\"},{\"name\":\"姨夫\",\"phone\":\"18567741125\"},{\"name\":\"张衡\",\"phone\":\"15333806429\"},{\"name\":\"韩丽国\",\"phone\":\"15903692027\"},{\"name\":\"常海领\",\"phone\":\"15138912798\"},{\"name\":\"陈志衡\",\"phone\":\"13949412233\"},{\"name\":\"何峰\",\"phone\":\"18739749410\"},{\"name\":\"何海涛\",\"phone\":\"18837802750\"},{\"name\":\"露露\",\"phone\":\"18530115156\"},{\"name\":\"周怀果\",\"phone\":\"18625591915\"},{\"name\":\"张永川\",\"phone\":\"15393769939\"},{\"name\":\"王云鹏\",\"phone\":\"18637267447\"},{\"name\":\"侯浪\",\"phone\":\"13673341954\"},{\"name\":\"向阳\",\"phone\":\"18437190086\"},{\"name\":\"王文科\",\"phone\":\"18911483092\"},{\"name\":\"王超\",\"phone\":\"+86 136-0135-5427\"},{\"name\":\"姨夫\",\"phone\":\"18439242245\"},{\"name\":\"侯丽平\",\"phone\":\"155-1406-0011\"},{\"name\":\"妈许昌号\",\"phone\":\"13323996032\"},{\"name\":\"苹果官方\",\"phone\":\"4006701855\"}]";
//	    	
//	    	String url = "http://m:8092/friend/uploadcontacts?token=MjA0N2UxZWU0MzI2NGU4YzljNzllNjQ2MGUwNzkzM2ImZjYzMTFlNmFjMDhlNDYxOGIwYTg1MWE5ODlhYzQzOGE%3D";
//	    	
//	    	String res = HttpRequest.sendPost(url,"contacts="+content);
//	    	System.out.println(res);
	    	
	    	
	    	String url = "http://baidu.com";
	    	
	    	System.out.println(url.replace(url, "<a href=\""+url+"\">原文链接<a>"));
	    	
	    }
}
