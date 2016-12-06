package ejb.passwordencryption;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 * Class PBKDF2 handles password encryptions.
 */
public class PBKDF2 {
	
	private static final int iterations = 5000;

	/**
	 * Method that takes a password, a salt, an iteration value and 
	 * key length and creates a hash and returns it as hex value.
	 * 
	 * @param password = char-array input that will be hashed.
	 * @param salt = byte-array that will be mixed with the hashed char-array.
	 * @return hashed string including salt as hex-value.
	 */
	public static String generateHashedPassword(char[] password, byte[] salt) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, 64*8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] hash = skf.generateSecret(spec).getEncoded();		
		return toHex(hash);
	}
	
	/**
	 * Method to get a byte-array from a hex value.
	 * @param hex = input string as hex.
	 * @return byte-array converted from hex.
	 */
	private static byte[] fromHex(String hex) {
		byte [] bytes = new byte[hex.length() / 2];
		for(int i=0; i<bytes.length; i++) {
			bytes[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i +2), 16);
		}
		return bytes;
	}
	
	/**
	 * Method to get a hex value from a byte-array
	 * @param array = byte-array 
	 * @return string as hex generated from input byte-array.
	 */
	public static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length *2) - hex.length();
		if(paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
	
	/**
	 * Method to create a salt.
	 * @return generate and returns a salt from algorithm SHA1PRNG
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] salt = new byte [16];
		sr.nextBytes(salt);
		return salt;
	}
	
	/**
	 * Method that checks if the password form the user, mixed with salt stored in the database for this 
	 * particular user matches the stored password for the same user. If it's a match, 
	 * the method will return true. Otherwise it will return false.
	 * 
	 * @param inputPassword = string input from front-end field "password". 
	 * @param storedPassword = stored string from database.
	 * @param storedSalt = stored salt from database.
	 * @return true if inputPassword with stored salt is equal to storedPassword, false if not.
	 */
	public static boolean validatePassword(String inputPassword, String storedPassword, String storedSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		byte[] salt = fromHex(storedSalt);
		byte[] originalHash = fromHex(storedPassword);
		
		PBEKeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, iterations, originalHash.length*8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] testHash = skf.generateSecret(spec).getEncoded();
		int diff = originalHash.length ^ testHash.length; // ^ means or. If diff = 0 then the length is correct.
		for(int i=0; i<originalHash.length && i<testHash.length; i++) {
			diff |= originalHash[i] ^testHash[i]; //compare byte for byte if they are equal. If not, counter diff will increase. If diff isn't zero at the end, the method will return false. 
		}
		
		return diff == 0;
		
	}

}
