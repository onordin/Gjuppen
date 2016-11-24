package ejb.passwordencryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public static String getMD5(String password){
		String md5String = null;
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes());
			byte[] bytes = messageDigest.digest();
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < bytes.length; i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			md5String = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5String;
	}

}
