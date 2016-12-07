package backingbeans;

import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import helpers.LiveEncryption;

@Named(value="encryptionBean")
@SessionScoped
public class EncryptionDemoBean implements Serializable {

	private static final long serialVersionUID = -5143882865656015359L;
	
	private LiveEncryption liveEncryption;
	private String textEntered;
	private String textEncrypted;
	private String textDecrypted;
	private String privateKeyExp;
	private String privateKeyMod;
	private String publicKeyExp;
	private String publicKeyMod;
	
	public EncryptionDemoBean() {
		liveEncryption = new LiveEncryption();
	}
	

	public String getTextEntered() {
		return textEntered;
	}
	public void setTextEntered(String textEntered) {
		this.textEntered = textEntered;
	}
	public String getTextEncrypted() {
		return textEncrypted;
	}
	public void setTextEncrypted(String textEncrypted) {
		this.textEncrypted = textEncrypted;
	}
	public String getTextDecrypted() {
		return textDecrypted;
	}
	public void setTextDecrypted(String textDecrypted) {
		this.textDecrypted = textDecrypted;
	}
	public String getPrivateKeyExp() {
		return privateKeyExp;
	}

	public void setPrivateKeyExp(String privateKeyExp) {
		this.privateKeyExp = privateKeyExp;
	}

	public String getPrivateKeyMod() {
		return privateKeyMod;
	}

	public void setPrivateKeyMod(String privateKeyMod) {
		this.privateKeyMod = privateKeyMod;
	}

	public String getPublicKeyExp() {
		return publicKeyExp;
	}

	public void setPublicKeyExp(String publicKeyExp) {
		this.publicKeyExp = publicKeyExp;
	}

	public String getPublicKeyMod() {
		return publicKeyMod;
	}

	public void setPublicKeyMod(String publicKeyMod) {
		this.publicKeyMod = publicKeyMod;
	}

	
	public void setupForNewDemo() {
		liveEncryption.generateKey();
		RSAPublicKey publicRSAKey = (RSAPublicKey) liveEncryption.getPublicKey();
		RSAPrivateKey privateRSAKey = (RSAPrivateKey) liveEncryption.getPrivateKey();
		publicKeyExp = publicRSAKey.getPublicExponent().toString();
		publicKeyMod = publicRSAKey.getModulus().toString();
		privateKeyExp = privateRSAKey.getPrivateExponent().toString();
		privateKeyMod = privateRSAKey.getModulus().toString();
	}
	
	public void encryptMessage() {
		textEncrypted = liveEncryption.encryptAndReturnString(textEntered);
		System.out.println("entered: " + textEntered);
		System.out.println("encrypted: " + textEncrypted);
		System.out.println(liveEncryption.getPrivateKeyAsString());
		System.out.println(liveEncryption.getPublicKeyAsString());
	}
	
	public void decryptMessage() {
		textDecrypted = liveEncryption.decryptAndReturnString(textEncrypted);
	}
	
	public void resetBean() {
		publicKeyExp = "";
		publicKeyMod = "";
		privateKeyExp = "";
		privateKeyMod = "";
		textEntered = "";
		textEncrypted = "";
		textDecrypted = "";
	}
	
}
