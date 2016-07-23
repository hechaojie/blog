package test.blog.front.util;

import com.hecj.common.utils.StringUtil;
import com.hecj.common.utils.encryp.Encrypt;

public class TokenUtil {
	
	static String PASS_KEY = "4B103DF3D3505AF871EFED63A78E3B75";
	public static String createToken(Long userId) {
//		return Encrypt.encrypt3DES(userId + "," + System.currentTimeMillis(),
//				PASS_KEY);
		
		return Encrypt.strEncode(userId + "," + System.currentTimeMillis(), PASS_KEY,"","");
		
	}

/*	public static String createToken2(Long userId) {
		return Encrypt.encrypt3DES(userId + "," + System.currentTimeMillis(),
				"4B103DF3D3505AF871EFED63A78E3B75");
	}
*/
	public static Long getUserIdFromToken(String token) {
//		String decryptedString = Encrypt
//				.decrypt3DES(token, PASS_KEY);
		
		String decryptedString = Encrypt.strDecode(token, PASS_KEY, "", "");
		System.out.println("decryptedString:"+decryptedString);
		try {
			if (!StringUtil.isStrEmpty(decryptedString)) {
				String userId = decryptedString.substring(0,
						decryptedString.indexOf(","));
				if (StringUtil.isNumber(userId)) {// 如果是数字
					return Long.valueOf(userId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
//		String token = createToken(18890615L);
//		System.out.println(token);
//		System.out.println(getUserIdFromToken(token));
//		System.out.println(getUserIdFromToken("ACF69EB11102C76EACDF913AF3252DB78A1597DE9D5C2FA20B728"));
//		System.out.println(createToken(19012405l));
		System.out.println(Encrypt.strEncode(19012405+"",PASS_KEY,"",""));
	}
}
