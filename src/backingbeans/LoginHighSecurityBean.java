package backingbeans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import displayEntities.HighSecurityDisplayEntity;
import ejb.interfaces.LocalHighLoginEJB;
import messageservice.MessageService;

/**
 * Presentation layer for the high-login function.
 */

@Named(value="loginHigh")
@SessionScoped
public class LoginHighSecurityBean implements Serializable {

	private static final long serialVersionUID = 4990844464975214810L;

	private String username;
	private String password;
	private String otp;
	private MessageService messageService;
	private HighSecurityDisplayEntity highSecurityDisplayEntity;
	
	@EJB
	private LocalHighLoginEJB highLoginEJB;

	
	public LoginHighSecurityBean() {
		messageService = new MessageService();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	

	public HighSecurityDisplayEntity getHighSecurityDisplayEntity() {
		return highSecurityDisplayEntity;
	}

	public void setHighSecurityDisplayEntity(HighSecurityDisplayEntity highSecurityDisplayEntity) {
		this.highSecurityDisplayEntity = highSecurityDisplayEntity;
	}

	public String login() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		HighSecurityDisplayEntity returnedEntity = highLoginEJB.login(username, password); 

		String returnCode;
		if (returnedEntity != null) {
			returnCode = highLoginEJB.yubicoHandler(returnedEntity, otp);
			if(returnCode.equals("loggedOnHighSecurity")) {
				this.highSecurityDisplayEntity = returnedEntity;
				this.username = "";
				this.password = "";
				this.otp = "";
				return "loggedOnHighSecurity";
			} else {
				messageService.errorMsg("login3", returnCode);
				return null;
			}
		} else {
			messageService.errorMsg("login3", "Wrong username and/or password");
			return "";
		}
	}

	
	
	public String logout() {
		this.highSecurityDisplayEntity = null;
		return "startpage";
	}
	

	
	
	
}
