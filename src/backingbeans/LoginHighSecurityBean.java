package backingbeans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import displayEntities.HighSecurityDisplayEntity;
import ejb.interfaces.LocalHighLoginEJB;

@Named(value="loginHigh")
@SessionScoped
public class LoginHighSecurityBean implements Serializable {

	private static final long serialVersionUID = 4990844464975214810L;

	private String username;
	private String password;
	private String otp;
	
	
	
	
	private HighSecurityDisplayEntity highSecurityDisplayEntity;
	
	@EJB
	private LocalHighLoginEJB highLoginEJB;

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

	public String login() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, YubicoVerificationException, YubicoValidationFailure {
		HighSecurityDisplayEntity returnedEntity = highLoginEJB.login(username, password); 
		
		String returnPage;
		if (returnedEntity != null) {
			returnPage = highLoginEJB.yubicoHandler(returnedEntity, otp);
			if(returnPage.equals("loggedOnHighSecurity")) {
				this.highSecurityDisplayEntity = returnedEntity;
				this.username = "";
				this.password = "";
				this.otp = "";
				System.out.println("HighLogin success.");
				return "loggedOnHighSecurity";
			} else {
				System.out.println(returnPage);
				return null;
			}
		} else {
			System.out.println("returnedEntity is null");
			return "";
		}
	}

	

	

	
	
	
}
