package helpers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.Cipher;


public class LiveEncryption {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private final String ALGORITHM = "RSA";

	public void generateKey() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(2048);
			final KeyPair key = keyGen.generateKeyPair();

			publicKey = key.getPublic();
			privateKey = key.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	private String decrypt(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ALGORITHM);

			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}

	public String getPublicKeyAsString() {
		return Integer.toString(publicKey.hashCode());
	}


	public String getPrivateKeyAsString() {
		return Integer.toString(privateKey.hashCode());
	}


	public String encryptAndReturnString(String text) {
		String encrypted = "";
		byte[] cipher = encrypt(text, publicKey);
		encrypted = Arrays.toString(cipher);
		return encrypted;
	}
	
	
	public String decryptAndReturnString(String encryptedValue) {
		String decrypted = "";
		
		String[] byteValues = encryptedValue.substring(1, encryptedValue.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];

		for (int i=0, len=bytes.length; i<len; i++) {
		   bytes[i] = Byte.parseByte(byteValues[i].trim());     
		}		
		
		decrypted = decrypt(bytes, privateKey);
		return decrypted;
	}
	
	
}