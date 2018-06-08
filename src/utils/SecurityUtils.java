package utils;

import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtils {
	
	private static final String salt = "R08eac03b80a@dc33dc*7d8%43fbe44b7c^7b05d3a6bdb43fcb710b03ba*";
	
	public static String createMd5Hash(String password){
		return DigestUtils.md5Hex(password + salt).toUpperCase();
	}
}
