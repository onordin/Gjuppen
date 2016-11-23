package ejb.passwordencryption;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2 {

	public static String generateHashedPassword(String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		int iterations = 5000;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();
		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64*8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();		
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}
	
	private static byte[] fromHex(String hex) {
		byte [] bytes = new byte[hex.length() / 2];
		for(int i=0; i<bytes.length; i++) {
			bytes[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i +2), 16);
		}
		return bytes;
	}
	
	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length *2) - hex.length();
		if(paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
	
	public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] salt = new byte [16];
		sr.nextBytes(salt);
		return salt;
	}
	
	// skicka in lösenordet man skrivit in. Jämför med det som är lagrat i db. sant eller falsk att de stämmer överens.
		public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
			String[] parts = storedPassword.split(":");
			int iterations = Integer.parseInt(parts[0]);
			byte[] salt = fromHex(parts[1]);
			byte[] hash = fromHex(parts[2]);
			
			PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length*8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] testHash = skf.generateSecret(spec).getEncoded();
			int diff = hash.length ^ testHash.length; // ^ betyder eller. Om diff blir noll så är längden rätt på lösenordet.
			for(int i=0; i<hash.length && i<testHash.length; i++) {
				diff |= hash[i] ^testHash[i]; //jämför bytes steg för steg |= ökar eller minskar. Om någon plats skiljer sig får diff ett nytt värde.
			}
			// om diff fortfarande är noll så är även innehållet korrekt.
			// målet är att diff ska bli noll. Då stämmer det inmatade lösenordet med det sparade.
			return diff == 0;
			
		}
	
}
