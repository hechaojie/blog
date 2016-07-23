package test.blog.front.util;

import com.hecj.common.utils.HttpRequest;

public class HttpUtil {

	public static void main(String[] args) throws Exception {
//		String p = "phoneNumber=15811372713&loginPassword=111111";
//		String res = HttpRequest.sendPost("http://www.duomeidai.com/android_api/login", p);
//		System.out.println(res);
//		String p = "phoneNumber=18256931381&loginPassword=111111";
//		String res = HttpRequest.sendPost("http://www.duomeidai.com/android_api/login", p);
//		System.out.println(res);
		
		/**
		 "android_api";
    arrayOfString[2] = "user";
    arrayOfString[3] = "red_envelope";
    arrayOfString[4] = "open";
		 */
		
		 
		 
		while(true){
			
			String url = "http://www.duomeidai.com/android_api/user/red_envelope/open";
			
			String params = "token=0AF91FA9B4FF4856A6DBE16249FFBA2248668EB8F2357867";
			String rest = HttpRequest.sendPost(url, params);
			System.out.println("帐户一："+rest);
			
			String params2 = "token=59E58305F2093333654AB666BA9DF55F159D0EC0E4878DB7";
			String rest2 = HttpRequest.sendPost(url, params2);
			System.out.println("帐户二："+rest2);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
