package th.co.bookstore.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

public class ProjectUtils {

	public static String getCurrentUsername(HttpServletRequest request) {
		Principal user = request.getUserPrincipal();
		return user.getName();
	}
	
	public static String encryptMD5Password(String pwdstr) {
		  
		  String pwdStr  = null;
		try {
			byte[] pwd = DigestUtils.md5Digest(pwdstr.getBytes("UTF8"));
			pwdStr = "{MD5}"+ Base64.encodeBase64String(pwd);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		  return pwdStr;
	}
}
