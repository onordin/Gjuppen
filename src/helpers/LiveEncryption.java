package helpers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;


public class LiveEncryption {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private final String ALGORITHM = "RSA";

	public void generateKey() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(1024);
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
		String decrypted = "";
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ALGORITHM);

			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);
			decrypted = new String(dectyptedText);

		} catch (Exception ex) {
			System.out.println("failure to decrypt");
			decrypted = "ERROR - Failure during decryption!";
			ex.printStackTrace();
		}
		return decrypted;
	}

	
	public String getPublicKeyAsString() {
		RSAPublicKey pub = (RSAPublicKey) publicKey;
		String fullKey = "";
		fullKey += pub.getPublicExponent();
		fullKey += ":";
		fullKey += pub.getModulus();
		return fullKey;
	
	}

	public String getPrivateKeyAsString() {
		RSAPrivateKey priv = (RSAPrivateKey) privateKey;
		String fullKey = "";
		fullKey += priv.getPrivateExponent();
		fullKey += ":";
		fullKey += priv.getModulus();
		return fullKey;
	}


	public String encryptAndReturnString(String text) {
		String encrypted = "";
		byte[] cipher = encrypt(text, publicKey);
		
		encrypted = Base64.getEncoder().encodeToString(cipher);
		//encrypted = Arrays.toString(cipher);
		return encrypted;
	}
	
	
	public String decryptAndReturnString(String encryptedValue) {
		String decrypted = "";
		
		byte[] bytes = Base64.getDecoder().decode(encryptedValue);

		/*
		String[] byteValues = encryptedValue.substring(1, encryptedValue.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];

		for (int i=0, len=bytes.length; i<len; i++) {
		   bytes[i] = Byte.parseByte(byteValues[i].trim());     
		}		
		*/
		
		decrypted = decrypt(bytes, privateKey);
		return decrypted;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	
}