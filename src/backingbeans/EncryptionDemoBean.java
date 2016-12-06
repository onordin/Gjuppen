package backingbeans;

import java.io.Serializable;

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
	private String privateKey;
	private String publicKey;
	
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
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public void setupForNewDemo() {
		System.out.println("inside setup demo");
		liveEncryption = new LiveEncryption();
		liveEncryption.generateKey();
		this.privateKey = liveEncryption.getPrivateKeyAsString();
		this.publicKey = liveEncryption.getPublicKeyAsString();
	}
	
	public String encryptMessage() {
		textEncrypted = liveEncryption.encryptAndReturnString(textEntered);
		System.out.println("encrypted: " + textEncrypted);
		System.out.println(liveEncryption.getPrivateKeyAsString());
		System.out.println(liveEncryption.getPublicKeyAsString());
		return "";
	}
	
	public String decryptMessage() {
		textDecrypted = liveEncryption.decryptAndReturnString(textEncrypted);
		return "";
	}
	
	public void resetBean() {
		privateKey = "";
		publicKey = "";
		textEntered = "";
		textEncrypted = "";
		textDecrypted = "";
	}
	
}
